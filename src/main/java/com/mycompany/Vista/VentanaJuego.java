/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Vista;





import com.mycompany.Controlador.ControladorJuego;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Ventana gráfica del Solitario Klondike.
 */
public class VentanaJuego extends JFrame {

    private ControladorJuego controlador;

    private JTextArea areaTablero;
    private JLabel etiquetaPuntos;
    private JLabel etiquetaEstado;

    private boolean partidaIniciada;

    /**
     * Construye la ventana principal del juego.
     */
    public VentanaJuego() {

        controlador = new ControladorJuego();
        partidaIniciada = false;

        setTitle("Solitario Klondike");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearComponentes();
    }

    /**
     * Crea y organiza los componentes gráficos.
     */
    
  private void crearComponentes() {

    setLayout(new BorderLayout(10, 10));

    // Encabezado.
    JLabel titulo = new JLabel(
            "SOLITARIO KLONDIKE",
            SwingConstants.CENTER
    );

    titulo.setFont(new Font("Arial", Font.BOLD, 25));

    add(titulo, BorderLayout.NORTH);

    // Área donde se representa el tablero.
    areaTablero = new JTextArea();

    areaTablero.setEditable(false);
    areaTablero.setFont(new Font("Monospaced", Font.PLAIN, 16));
    areaTablero.setText(
            "Bienvenido al Solitario Klondike.\n\n"
            + "Pulsa 'Nueva partida' para comenzar."
    );

    JScrollPane desplazamiento =
            new JScrollPane(areaTablero);

    add(desplazamiento, BorderLayout.CENTER);

    // Panel inferior.
    JPanel panelInferior = new JPanel(new BorderLayout(5, 5));

    // Información de la partida.
    JPanel panelInformacion = new JPanel(
            new GridLayout(1, 2)
    );

    etiquetaPuntos = new JLabel("Puntuación: 0");
    etiquetaEstado = new JLabel("Esperando partida");

    panelInformacion.add(etiquetaPuntos);
    panelInformacion.add(etiquetaEstado);

    panelInferior.add(
            panelInformacion,
            BorderLayout.NORTH
    );

    // Botones de acciones.
    JPanel panelBotones = new JPanel(
            new GridLayout(4, 2, 8, 8)
    );

    JButton botonNueva = new JButton("Nueva partida");
    JButton botonRobar = new JButton("Robar cartas");
    JButton botonDescarteColumna =
            new JButton("Descarte a columna");
    JButton botonMoverColumnas =
            new JButton("Mover entre columnas");
    JButton botonColumnaFundacion =
            new JButton("Columna a fundación");
    JButton botonDescarteFundacion =
            new JButton("Descarte a fundación");
    JButton botonFundacionColumna =
            new JButton("Fundación a columna");
    JButton botonReglas = new JButton("Ver reglas");

    panelBotones.add(botonNueva);
    panelBotones.add(botonRobar);
    panelBotones.add(botonDescarteColumna);
    panelBotones.add(botonMoverColumnas);
    panelBotones.add(botonColumnaFundacion);
    panelBotones.add(botonDescarteFundacion);
    panelBotones.add(botonFundacionColumna);
    panelBotones.add(botonReglas);

    panelInferior.add(
            panelBotones,
            BorderLayout.CENTER
    );

    // Ayuda permanente sobre los colores y movimientos.
    JPanel panelAyuda = new JPanel(new GridLayout(2, 1));

    JLabel etiquetaColores = new JLabel(
            "COLORES: ♥ Corazones = ROJO | ♦ Diamantes = ROJO | "
            + "♠ Picas = NEGRO | ♣ Tréboles = NEGRO",
            SwingConstants.CENTER
    );

    JLabel etiquetaMovimientos = new JLabel(
            "MOVIMIENTOS: Orden descendente y colores alternados. "
            + "Ejemplo: 7♥ sobre 8♠ (válido). "
            + "Un Rey puede iniciar una columna vacía.",
            SwingConstants.CENTER
    );

    etiquetaColores.setFont(
            new Font("Arial", Font.BOLD, 12)
    );

    etiquetaMovimientos.setFont(
            new Font("Arial", Font.PLAIN, 12)
    );

    panelAyuda.add(etiquetaColores);
    panelAyuda.add(etiquetaMovimientos);

    panelInferior.add(
            panelAyuda,
            BorderLayout.SOUTH
    );

    // Botón para salir.
    JButton botonSalir = new JButton("Salir");

    panelInferior.add(
            botonSalir,
            BorderLayout.EAST
    );

    add(panelInferior, BorderLayout.SOUTH);

    // Eventos de los botones.
    botonNueva.addActionListener(e -> nuevaPartida());

    botonRobar.addActionListener(e -> robarCartas());

    botonDescarteColumna.addActionListener(
            e -> moverDescarteAColumna()
    );

    botonMoverColumnas.addActionListener(
            e -> moverEntreColumnas()
    );

    botonColumnaFundacion.addActionListener(
            e -> moverColumnaAFundacion()
    );

    botonDescarteFundacion.addActionListener(
            e -> moverDescarteAFundacion()
    );

    botonFundacionColumna.addActionListener(
            e -> moverFundacionAColumna()
    );

    botonReglas.addActionListener(e -> mostrarReglas());

    botonSalir.addActionListener(e -> System.exit(0));
}


    /**
     * Inicia una partida nueva.
     */
    private void nuevaPartida() {

        String nombre = JOptionPane.showInputDialog(
                this,
                "Escribe el nombre del jugador:"
        );

        if (nombre == null) {
            return;
        }

        nombre = nombre.trim();

        if (nombre.isEmpty()) {
            nombre = "Jugador";
        }

        controlador.iniciarPartida(nombre);

        partidaIniciada = true;

        actualizarPantalla(
                "Nueva partida iniciada. ¡Buena suerte!"
        );
    }

    /**
     * Roba hasta tres cartas.
     */
    private void robarCartas() {

        if (!puedeJugar()) {
            return;
        }

        String mensaje = controlador.robarCarta();

        actualizarPantalla(mensaje);
        revisarFinalPartida();
    }

    /**
     * Mueve el descarte a una columna.
     */
    private void moverDescarteAColumna() {

        if (!puedeJugar()) {
            return;
        }

        int destino = pedirColumna(
                "¿A qué columna deseas mover la carta?"
        );

        if (destino == -1) {
            return;
        }

        boolean movimiento =
                controlador.moverDescarteAColumna(destino);

        if (movimiento) {
            actualizarPantalla("Movimiento realizado.");
        } else {
            actualizarPantalla("Movimiento no válido.");
        }

        revisarFinalPartida();
    }

    /**
     * Mueve un grupo de cartas entre columnas.
     */
    private void moverEntreColumnas() {

        if (!puedeJugar()) {
            return;
        }

        int origen = pedirColumna(
                "Selecciona la columna de origen:"
        );

        if (origen == -1) {
            return;
        }

        int posicion = pedirNumero(
                "Posición inicial del grupo.\n"
                + "Cuenta desde arriba: 1 es la primera carta."
        );

        if (posicion < 1) {
            return;
        }

        int destino = pedirColumna(
                "Selecciona la columna de destino:"
        );

        if (destino == -1) {
            return;
        }

        boolean movimiento =
                controlador.moverColumnaAColumna(
                        origen,
                        posicion - 1,
                        destino
                );

        if (movimiento) {
            actualizarPantalla("Grupo de cartas movido.");
        } else {
            actualizarPantalla("No se puede realizar ese movimiento.");
        }

        revisarFinalPartida();
    }

    /**
     * Mueve la carta superior de una columna
     * a su fundación.
     */
    private void moverColumnaAFundacion() {

        if (!puedeJugar()) {
            return;
        }

        int origen = pedirColumna(
                "Selecciona la columna de origen:"
        );

        if (origen == -1) {
            return;
        }

        boolean movimiento =
                controlador.moverColumnaAFundacion(origen);

        if (movimiento) {
            actualizarPantalla("Carta enviada a la fundación.");
        } else {
            actualizarPantalla("Movimiento no válido.");
        }

        revisarFinalPartida();
    }

    /**
     * Mueve la carta del descarte a su fundación.
     */
    private void moverDescarteAFundacion() {

        if (!puedeJugar()) {
            return;
        }

        boolean movimiento =
                controlador.moverDescarteAFundacion();

        if (movimiento) {
            actualizarPantalla("Carta enviada a la fundación.");
        } else {
            actualizarPantalla("Movimiento no válido.");
        }

        revisarFinalPartida();
    }

    /**
     * Mueve la carta superior de una fundación
     * a una columna.
     */
    private void moverFundacionAColumna() {

        if (!puedeJugar()) {
            return;
        }

        int fundacion = pedirNumero(
                "Selecciona la fundación (1 a 4):"
        );

        if (fundacion < 1 || fundacion > 4) {
            mostrarMensaje("La fundación debe estar entre 1 y 4.");
            return;
        }

        int destino = pedirColumna(
                "Selecciona la columna de destino:"
        );

        if (destino == -1) {
            return;
        }

        boolean movimiento =
                controlador.moverFundacionAColumna(
                        fundacion - 1,
                        destino
                );

        if (movimiento) {
            actualizarPantalla("Carta devuelta a la columna.");
        } else {
            actualizarPantalla("Movimiento no válido.");
        }

        revisarFinalPartida();
    }

    /**
     * Solicita una columna y devuelve su índice.
     * @param mensaje pregunta que se mostrará.
     * @return índice entre 0 y 6, o -1 si se cancela.
     */
    private int pedirColumna(String mensaje) {

        int numero = pedirNumero(
                mensaje + "\nEscribe un número entre 1 y 7."
        );

        if (numero < 1 || numero > 7) {
            mostrarMensaje("Debes seleccionar una columna del 1 al 7.");
            return -1;
        }

        return numero - 1;
    }

    /**
     * Solicita un número entero.
     * @param mensaje pregunta que se mostrará.
     * @return número o -1 si se cancela o es inválido.
     */
    private int pedirNumero(String mensaje) {

        String texto = JOptionPane.showInputDialog(
                this,
                mensaje
        );

        if (texto == null) {
            return -1;
        }

        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException error) {
            mostrarMensaje("Debes escribir un número válido.");
            return -1;
        }
    }

    /**
     * Actualiza el tablero y la información de pantalla.
     * @param mensaje mensaje de la última acción.
     */
    private void actualizarPantalla(String mensaje) {

        if (!partidaIniciada) {
            return;
        }

        areaTablero.setText(
                controlador.getTablero().mostrarTablero()
        );

        etiquetaPuntos.setText(
                "Puntuación: " + controlador.getPuntos()
        );

        etiquetaEstado.setText(mensaje);
    }

    /**
     * Comprueba si el jugador puede realizar acciones.
     * @return true si existe una partida activa.
     */
    private boolean puedeJugar() {

        if (!partidaIniciada) {
            mostrarMensaje("Primero debes comenzar una partida.");
            return false;
        }

        if (controlador.isPartidaTerminada()) {
            mostrarMensaje("La partida ya terminó. Inicia otra.");
            return false;
        }

        return true;
    }

    /**
     * Revisa si la partida terminó.
     */
    private void revisarFinalPartida() {

        if (controlador.isPartidaTerminada()) {

            actualizarPantalla(
                    controlador.getResultado()
            );

            JOptionPane.showMessageDialog(
                    this,
                    controlador.getResultado()
                    + "\nPuntuación final: "
                    + controlador.getPuntos()
            );
        }
    }

    /**
     * Muestra las reglas del juego.
     */
    private void mostrarReglas() {

        String reglas =
                "REGLAS DEL SOLITARIO KLONDIKE\n\n"
                + "1. Ordena las columnas de mayor a menor.\n"
                + "2. Alterna cartas rojas y negras.\n"
                + "3. Las columnas vacías aceptan Reyes.\n"
                + "4. Las fundaciones comienzan con As.\n"
                + "5. Las fundaciones deben tener el mismo palo.\n"
                + "6. Ordena cada fundación hasta llegar al Rey.\n"
                + "7. Ganas cuando completas las cuatro fundaciones.";

        JOptionPane.showMessageDialog(
                this,
                reglas,
                "Reglas del juego",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra un mensaje informativo.
     * @param mensaje texto que se mostrará.
     */
    private void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Solitario Klondike",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    
    
    private JPanel crearPanelAyuda() {

    JPanel panel = new JPanel(new GridLayout(2, 1));

    JLabel colores = new JLabel(
        "COLORES: ♥ Corazones = ROJO | ♦ Diamantes = ROJO"
        + " | ♠ Picas = NEGRO | ♣ Treboles = NEGRO",
        SwingConstants.CENTER
    );

    JLabel movimientos = new JLabel(
        "MOVIMIENTOS: Orden descendente y colores alternados. "
        + "Ejemplo: 7♥ sobre 8♠ (válido). "
        + "Un Rey puede colocarse en una columna vacía.",
        SwingConstants.CENTER
    );

    colores.setFont(new Font("Arial", Font.BOLD, 13));
    movimientos.setFont(new Font("Arial", Font.PLAIN, 12));

    panel.add(colores);
    panel.add(movimientos);

    return panel;
}
    
    
    
    
    
    
    
}
