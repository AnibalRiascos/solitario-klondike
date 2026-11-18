
package com.mycompany.Controlador;





import com.mycompany.Model.Carta;
import com.mycompany.Model.Puntaje;
import com.mycompany.Model.Tablero;
import com.mycompany.Modelo.Estructura.ArbolBinario;


/**
 * Controla el desarrollo de una partida de Solitario.
 */
public class ControladorJuego {

    private Tablero tablero;
    private ArbolBinario<Puntaje> mejoresPuntajes;

    private String nombreJugador;
    private int puntos;
    private boolean partidaTerminada;
    private String resultado;

    /**
     * Inicializa el controlador y el árbol de puntuaciones.
     */
    public ControladorJuego() {
        mejoresPuntajes = new ArbolBinario<>();
        tablero = new Tablero();
        puntos = 0;
        partidaTerminada = false;
        resultado = "";
    }

    /**
     * Comienza una nueva partida.
     * @param nombre nombre del jugador.
     */
    public void iniciarPartida(String nombre) {
        nombreJugador = nombre;
        puntos = 0;
        partidaTerminada = false;
        resultado = "";

        tablero = new Tablero();
        tablero.iniciarPartida();
    }

    /**
     * Roba una carta o recicla el mazo si está vacío.
     * @return mensaje de la operación.
     */
    public String robarCarta() {
        boolean mazoEstabaVacio =
                tablero.getMazoRobo().estaVacia();

        Carta carta = tablero.robarCarta();

        if (carta == null) {
            actualizarEstado();

            if (mazoEstabaVacio) {
                return "El mazo se recicló. Puedes volver a robar.";
            }

            return "No hay cartas disponibles.";
        }

        actualizarEstado();
        return "Carta robada: " + carta;
    }

    /**
     * Mueve el descarte a una columna.
     * @param destino columna de destino.
     * @return true si se realizó el movimiento.
     */
    public boolean moverDescarteAColumna(int destino) {
        boolean resultadoMovimiento =
                tablero.moverDescarteAColumna(destino);

        if (resultadoMovimiento) {
            puntos += 5;
            actualizarEstado();
        }

        return resultadoMovimiento;
    }

    /**
     * Mueve un grupo entre columnas.
     * @param origen columna de origen.
     * @param posicion posición inicial del grupo.
     * @param destino columna de destino.
     * @return true si se realizó el movimiento.
     */
    public boolean moverColumnaAColumna(
            int origen, int posicion, int destino) {

        boolean movimiento = tablero.moverColumnaAColumna(
                origen, posicion, destino);

        if (movimiento) {
            puntos += 5;
            actualizarEstado();
        }

        return movimiento;
    }

    /**
     * Mueve una carta de una columna a una fundación.
     * @param origen columna de origen.
     * @return true si se realizó el movimiento.
     */
    public boolean moverColumnaAFundacion(int origen) {
        boolean movimiento =
                tablero.moverColumnaAFundacion(origen);

        if (movimiento) {
            puntos += 10;
            actualizarEstado();
        }

        return movimiento;
    }

    /**
     * Mueve el descarte a su fundación.
     * @return true si se realizó el movimiento.
     */
    public boolean moverDescarteAFundacion() {
        boolean movimiento = tablero.moverDescarteAFundacion();

        if (movimiento) {
            puntos += 10;
            actualizarEstado();
        }

        return movimiento;
    }

    /**
     * Devuelve una carta de una fundación a una columna.
     * @param fundacion índice de la fundación.
     * @param destino índice de la columna.
     * @return true si se realizó el movimiento.
     */
    public boolean moverFundacionAColumna(
            int fundacion, int destino) {

        boolean movimiento =
                tablero.moverFundacionAColumna(fundacion, destino);

        if (movimiento) {
            puntos = Math.max(0, puntos - 5);
            actualizarEstado();
        }

        return movimiento;
    }

    /**
     * Comprueba si se ganó o se perdió la partida.
     */
    private void actualizarEstado() {

        if (tablero.hayVictoria()) {
            partidaTerminada = true;
            resultado = "¡Felicidades! Has ganado la partida.";

            mejoresPuntajes.insertar(
                    new Puntaje(nombreJugador, puntos));

        } else if (!tablero.hayMovimientosDisponibles()) {
            partidaTerminada = true;
            resultado = "No quedan movimientos posibles. "
                    + "La partida ha terminado.";
        }
    }

    /**
     * Obtiene el tablero actual.
     * @return tablero de la partida.
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Obtiene los puntos actuales.
     * @return puntuación.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Comprueba si la partida terminó.
     * @return true si terminó.
     */
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    /**
     * Obtiene el mensaje del resultado.
     * @return mensaje final.
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Obtiene los mejores puntajes ordenados.
     * @return recorrido inorden del árbol.
     */
    public String mostrarMejoresPuntajes() {
        if (mejoresPuntajes.estaVacio()) {
            return "Todavía no hay puntuaciones registradas.";
        }

        return mejoresPuntajes.inorden();
    }
}
