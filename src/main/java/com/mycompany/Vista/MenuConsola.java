/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Vista;




import com.mycompany.Controlador.ControladorJuego;
import com.mycompany.Model.Tablero;
import java.util.Scanner;


/**
 * Interfaz de consola del Solitario Klondike.
 */
public class MenuConsola {

    private Scanner entrada;
    private ControladorJuego controlador;

    /**
     * Inicializa el menú.
     */
    public MenuConsola() {
        entrada = new Scanner(System.in);
        controlador = new ControladorJuego();
    }

    /**
     * Inicia el menú principal.
     */
    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("       SOLITARIO KLONDIKE");
            System.out.println("==============================");
            System.out.println("1. Nueva partida");
            System.out.println("2. Mejores puntuaciones");
            System.out.println("3. Reglas del juego");
            System.out.println("4. Salir");
            System.out.println("==============================");

            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    nuevaPartida();
                    break;

                case 2:
                    mostrarPuntajes();
                    break;

                case 3:
                    mostrarReglas();
                    break;

                case 4:
                    System.out.println("Gracias por jugar.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }

    /**
     * Prepara y ejecuta una nueva partida.
     */
    private void nuevaPartida() {
        System.out.print("Nombre del jugador: ");
        String nombre = entrada.nextLine().trim();

        if (nombre.isEmpty()) {
            nombre = "Jugador";
        }

        controlador.iniciarPartida(nombre);

        jugar();
    }

    /**
     * Ejecuta el ciclo principal de una partida.
     */
    private void jugar() {

        while (!controlador.isPartidaTerminada()) {

            Tablero tablero = controlador.getTablero();

            System.out.println(tablero.mostrarTablero());
            System.out.println("Puntuación: "
                    + controlador.getPuntos());

            System.out.println("\nACCIONES");
            System.out.println("1. Robar hasta tres cartas");
            System.out.println("2. Descarte a columna");
            System.out.println("3. Mover grupo entre columnas");
            System.out.println("4. Columna a fundación");
            System.out.println("5. Descarte a fundación");
            System.out.println("6. Fundación a columna");
            System.out.println("7. Ver reglas");
            System.out.println("8. Abandonar partida");

            int opcion = leerEntero("Selecciona una acción: ");

            switch (opcion) {

                case 1:
                    System.out.println(controlador.robarCarta());
                    break;

                case 2:
                    moverDescarteAColumna();
                    break;

                case 3:
                    moverEntreColumnas();
                    break;

                case 4:
                    int origen = leerColumna(
                            "Columna de origen (1-7): ");

                    if (!controlador.moverColumnaAFundacion(origen)) {
                        System.out.println("Movimiento no válido.");
                    } else {
                        System.out.println("Carta enviada a la fundación.");
                    }
                    break;

                case 5:
                    if (!controlador.moverDescarteAFundacion()) {
                        System.out.println("Movimiento no válido.");
                    } else {
                        System.out.println("Carta enviada a la fundación.");
                    }
                    break;

                case 6:
                    int fundacion = leerEntero(
                            "Fundación (1-4): ") - 1;

                    int destino = leerColumna(
                            "Columna de destino (1-7): ");

                    if (!controlador.moverFundacionAColumna(
                            fundacion, destino)) {
                        System.out.println("Movimiento no válido.");
                    } else {
                        System.out.println("Carta devuelta a la columna.");
                    }
                    break;

                case 7:
                    mostrarReglas();
                    break;

                case 8:
                    System.out.println("Partida abandonada.");
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("\n" + controlador.getResultado());
        System.out.println("Puntuación final: "
                + controlador.getPuntos());
    }

    /**
     * Mueve el descarte a una columna seleccionada.
     */
    private void moverDescarteAColumna() {
        int destino = leerColumna("Columna de destino (1-7): ");

        if (!controlador.moverDescarteAColumna(destino)) {
            System.out.println("Movimiento no válido.");
        } else {
            System.out.println("Movimiento realizado.");
        }
    }

    /**
     * Mueve un grupo de cartas entre columnas.
     */
    private void moverEntreColumnas() {
        int origen = leerColumna("Columna de origen (1-7): ");

        int posicion = leerEntero(
                "Posición inicial del grupo (desde abajo): ");

        int destino = leerColumna("Columna de destino (1-7): ");

        // El usuario cuenta desde 1 y el programa desde 0.
        posicion--;

        if (!controlador.moverColumnaAColumna(
                origen, posicion, destino)) {
            System.out.println("Movimiento no válido.");
        } else {
            System.out.println("Grupo movido correctamente.");
        }
    }

    /**
     * Solicita una columna válida.
     * @param mensaje texto que se mostrará.
     * @return índice de columna entre 0 y 6.
     */
    private int leerColumna(String mensaje) {
        int numero;

        do {
            numero = leerEntero(mensaje);

            if (numero < 1 || numero > 7) {
                System.out.println("Debes elegir entre 1 y 7.");
            }

        } while (numero < 1 || numero > 7);

        return numero - 1;
    }

    /**
     * Lee un número entero y controla entradas inválidas.
     * @param mensaje pregunta que se mostrará.
     * @return número ingresado.
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            String texto = entrada.nextLine();

            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException error) {
                System.out.println(
                        "Entrada inválida. Escribe un número.");
            }
        }
    }

    /**
     * Muestra las reglas del Solitario.
     */
    private void mostrarReglas() {
        System.out.println("\n========== REGLAS ==========");
        System.out.println("1. Debes ordenar las columnas de Rey a As.");
        System.out.println("2. Las cartas deben alternar rojo y negro.");
        System.out.println("3. Las columnas vacías solo aceptan Reyes.");
        System.out.println("4. Las fundaciones comienzan con As.");
        System.out.println("5. Las fundaciones suben hasta Rey.");
        System.out.println("6. Cada fundación debe tener un mismo palo.");
        System.out.println("7. Ganas cuando completas las cuatro fundaciones.");
        System.out.println("============================");
    }

    /**
     * Muestra los mejores resultados.
     */
    private void mostrarPuntajes() {
        System.out.println("\n===== MEJORES PUNTUACIONES =====");
        System.out.println(controlador.mostrarMejoresPuntajes());
    }

    
}
