

package com.mycompany.Model;




import com.mycompany.Controlador.ReglasJuego;
import com.mycompany.Modelo.Estructura.Cola;
import com.mycompany.Modelo.Estructura.ListaCircular;
import com.mycompany.Modelo.Estructura.ListaDoble;
import com.mycompany.Modelo.Estructura.Pila;


/**
 * Administra las cartas y las zonas del tablero.
 */
public class Tablero {

    private Cola<Carta> colaReparto;
    private ListaCircular<Carta> mazoRobo;
    private Pila<Carta> descarte;

    private Pila<Carta>[] fundaciones;
    private ListaDoble<Carta>[] columnas;

    private ReglasJuego reglas;

    /**
     * Inicializa las estructuras del tablero.
     */
    @SuppressWarnings("unchecked")
    public Tablero() {
        mazoRobo = new ListaCircular<>();
        descarte = new Pila<>();
        fundaciones = (Pila<Carta>[]) new Pila[4];
        columnas = (ListaDoble<Carta>[]) new ListaDoble[7];
        reglas = new ReglasJuego();

        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Pila<>();
        }

        for (int i = 0; i < 7; i++) {
            columnas[i] = new ListaDoble<>();
        }
    }

    /**
     * Prepara una partida nueva y reparte las cartas.
     */
    public void iniciarPartida() {
        mazoRobo = new ListaCircular<>();
        descarte = new Pila<>();

        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Pila<>();
        }

        for (int i = 0; i < 7; i++) {
            columnas[i] = new ListaDoble<>();
        }

        // Crear las 52 cartas mezcladas.
        Mazo mazo = new Mazo();
        colaReparto = mazo.crearColaMezclada();

        // Repartir las siete columnas.
        for (int columna = 0; columna < 7; columna++) {

            for (int fila = 0; fila <= columna; fila++) {
                Carta carta = colaReparto.desencolar();

                if (fila == columna) {
                    carta.setDescubierta(true);
                }

                columnas[columna].insertarFinal(carta);
            }
        }

        // Las 24 cartas restantes van al mazo circular.
        while (!colaReparto.estaVacia()) {
            mazoRobo.insertarFinal(colaReparto.desencolar());
        }
    }

    /**
     * Obtiene una columna.
     * @param indice índice entre 0 y 6.
     * @return lista de la columna o null si no existe.
     */
    public ListaDoble<Carta> getColumna(int indice) {
        if (indice < 0 || indice >= 7) {
            return null;
        }

        return columnas[indice];
    }

    /**
     * Obtiene una fundación.
     * @param indice índice entre 0 y 3.
     * @return pila correspondiente.
     */
    public Pila<Carta> getFundacion(int indice) {
        if (indice < 0 || indice >= 4) {
            return null;
        }

        return fundaciones[indice];
    }

    /**
     * Obtiene el mazo circular.
     * @return mazo de robo.
     */
    public ListaCircular<Carta> getMazoRobo() {
        return mazoRobo;
    }

    /**
     * Obtiene la pila de descarte.
     * @return pila de descarte.
     */
    public Pila<Carta> getDescarte() {
        return descarte;
    }

    
/**
 * Roba hasta tres cartas del mazo.
 * La cola organiza temporalmente las cartas robadas
 * antes de pasarlas a la pila de descarte.
 *
 * @return última carta robada o null si se recicló el mazo.
 */
public Carta robarCarta() {

    if (mazoRobo.estaVacia()) {
        reciclarDescarte();
        return null;
    }

    // Preparar hasta tres cartas en la cola.
    int cantidad = 0;

    while (cantidad < 3 && !mazoRobo.estaVacia()) {
        Carta carta = mazoRobo.eliminarActual();
        carta.setDescubierta(true);
        colaReparto.encolar(carta);
        cantidad++;
    }

    // Pasar las cartas de la cola al descarte.
    Carta ultima = null;

    while (!colaReparto.estaVacia()) {
        ultima = colaReparto.desencolar();
        descarte.apilar(ultima);
    }

    return ultima;
}


    /**
     * Devuelve las cartas descartadas al mazo de robo.
     */
    
    private void reciclarDescarte() {
        while (!descarte.estaVacia()) {
            Carta carta = descarte.desapilar();
            carta.setDescubierta(false);
            mazoRobo.insertarInicio(carta);
    }

    mazoRobo.reiniciarActual();
}


    /**
     * Mueve la carta superior del descarte a una columna.
     *
     * @param destino índice de columna.
     * @return true si el movimiento fue válido.
     */
    public boolean moverDescarteAColumna(int destino) {
        if (destino < 0 || destino >= 7) {
            return false;
        }

        Carta carta = descarte.verTope();

        if (!reglas.puedeColocarEnColumna(
                carta, columnas[destino])) {
            return false;
        }

        descarte.desapilar();
        columnas[destino].insertarFinal(carta);

        return true;
    }

    /**
     * Mueve un grupo de cartas entre dos columnas.
     *
     * @param origen columna de origen.
     * @param posicion posición desde la que se mueve.
     * @param destino columna receptora.
     * @return true si se pudo realizar el movimiento.
     */
    public boolean moverColumnaAColumna(
            int origen, int posicion, int destino) {

        if (origen < 0 || origen >= 7
                || destino < 0 || destino >= 7
                || origen == destino) {
            return false;
        }

        ListaDoble<Carta> listaOrigen = columnas[origen];
        ListaDoble<Carta> listaDestino = columnas[destino];

        Carta cartaBase = listaOrigen.obtener(posicion);

        if (cartaBase == null) {
            return false;
        }

        if (!reglas.esSecuenciaValida(listaOrigen, posicion)) {
            return false;
        }

        if (!reglas.puedeColocarEnColumna(
                cartaBase, listaDestino)) {
            return false;
        }

        boolean movido = listaOrigen.moverDesde(
                posicion, listaDestino);

        if (movido) {
            revelarTope(listaOrigen);
        }

        return movido;
    }

    /**
     * Mueve la carta superior de una columna
     * a su fundación correspondiente.
     *
     * @param origen índice de columna.
     * @return true si se pudo mover.
     */
    public boolean moverColumnaAFundacion(int origen) {
        if (origen < 0 || origen >= 7) {
            return false;
        }

        ListaDoble<Carta> columna = columnas[origen];

        if (columna.estaVacia()) {
            return false;
        }

        Carta carta = columna.obtener(columna.tamanio() - 1);
        Pila<Carta> fundacion = fundaciones[carta.getPalo().ordinal()];

        if (!reglas.puedeColocarEnFundacion(carta, fundacion)) {
            return false;
        }

        columna.eliminar(carta);
        fundacion.apilar(carta);

        revelarTope(columna);
        return true;
    }

    /**
     * Mueve la carta superior del descarte
     * a su fundación correspondiente.
     *
     * @return true si el movimiento fue válido.
     */
    public boolean moverDescarteAFundacion() {
        Carta carta = descarte.verTope();

        if (carta == null) {
            return false;
        }

        Pila<Carta> fundacion = fundaciones[carta.getPalo().ordinal()];

        if (!reglas.puedeColocarEnFundacion(carta, fundacion)) {
            return false;
        }

        descarte.desapilar();
        fundacion.apilar(carta);

        return true;
    }

    /**
     * Mueve una carta de una fundación a una columna.
     *
     * @param indiceFundacion fundación de origen.
     * @param destino columna receptora.
     * @return true si el movimiento fue válido.
     */
    public boolean moverFundacionAColumna(
            int indiceFundacion, int destino) {

        if (indiceFundacion < 0 || indiceFundacion >= 4
                || destino < 0 || destino >= 7) {
            return false;
        }

        Pila<Carta> fundacion = fundaciones[indiceFundacion];
        Carta carta = fundacion.verTope();

        if (!reglas.puedeColocarEnColumna(
                carta, columnas[destino])) {
            return false;
        }

        fundacion.desapilar();
        columnas[destino].insertarFinal(carta);

        return true;
    }

    /**
     * Descubre la carta superior de una columna
     * cuando queda al descubierto.
     *
     * @param columna columna que se revisará.
     */
    private void revelarTope(ListaDoble<Carta> columna) {
        if (!columna.estaVacia()) {
            Carta superior = columna.obtener(
                    columna.tamanio() - 1);

            superior.setDescubierta(true);
        }
    }
    
    
    
    
    
/**
 * Comprueba si todavía existe algún movimiento posible.
 * @return true si se puede continuar jugando.
 */
public boolean hayMovimientosDisponibles() {

    // Si quedan cartas por robar, se puede continuar.
    if (!mazoRobo.estaVacia() || !descarte.estaVacia()) {
        return true;
    }

    // Comprobar movimientos entre columnas y fundaciones.
    for (int i = 0; i < 7; i++) {

        ListaDoble<Carta> origen = columnas[i];

        if (origen.estaVacia()) {
            continue;
        }

        Carta superior = origen.obtener(origen.tamanio() - 1);

        // Comprobar si la carta superior puede ir a su fundación.
        Pila<Carta> fundacion =
                fundaciones[superior.getPalo().ordinal()];

        if (reglas.puedeColocarEnFundacion(superior, fundacion)) {
            return true;
        }

        // Comprobar movimientos hacia otras columnas.
        for (int j = 0; j < 7; j++) {
            if (i != j && reglas.puedeColocarEnColumna(
                    superior, columnas[j])) {
                return true;
            }
        }

        // Comprobar movimientos de grupos.
        for (int posicion = 0;
                posicion < origen.tamanio(); posicion++) {

            Carta base = origen.obtener(posicion);

            if (!reglas.esSecuenciaValida(origen, posicion)) {
                continue;
            }

            for (int j = 0; j < 7; j++) {
                if (i != j && reglas.puedeColocarEnColumna(
                        base, columnas[j])) {
                    return true;
                }
            }
        }
    }

    // Comprobar si alguna fundación puede devolver
    // su carta superior a una columna.
    for (int i = 0; i < 4; i++) {
        Carta carta = fundaciones[i].verTope();

        if (carta == null) {
            continue;
        }

        for (int j = 0; j < 7; j++) {
            if (reglas.puedeColocarEnColumna(carta, columnas[j])) {
                return true;
            }
        }
    }

    return false;
}

    
    
    
    

    /**
     * Comprueba si se completaron las cuatro fundaciones.
     * @return true si se ganaron las 52 cartas.
     */
    public boolean hayVictoria() {
        for (int i = 0; i < 4; i++) {
            if (fundaciones[i].tamanio() != 13) {
                return false;
            }
        }

        return true;
    }

    /**
     * Muestra las zonas principales del tablero.
     * @return texto con el estado de la partida.
     */
    public String mostrarTablero() {
        StringBuilder texto = new StringBuilder();

        texto.append("\n========== SOLITARIO ==========\n");

        texto.append("Mazo de robo: ")
                .append(mazoRobo.tamanio())
                .append(" cartas restantes\n");

        texto.append("Descarte: ")
                .append(descarte.verTope() == null
                        ? "Vacío" : descarte.verTope())
                .append("\n");

        texto.append("Fundaciones:\n");

        for (int i = 0; i < 4; i++) {
            texto.append(i + 1).append(". ");

            if (fundaciones[i].estaVacia()) {
                texto.append("Vacía");
            } else {
                texto.append(fundaciones[i].verTope());
            }

            texto.append("\n");
        }

        texto.append("\nColumnas:\n");

        for (int i = 0; i < 7; i++) {
            texto.append("Columna ")
                    .append(i + 1)
                    .append(": ");

            texto.append(columnas[i].recorrerAdelante());
            texto.append("\n");
        }

        texto.append("===============================\n");

        return texto.toString();
    }
}
