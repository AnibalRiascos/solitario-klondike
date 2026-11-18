/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Controlador;




import com.mycompany.Model.Carta;
import com.mycompany.Modelo.Estructura.ListaDoble;
import com.mycompany.Modelo.Estructura.Pila;


/**
 * Contiene las reglas y validaciones del Solitario Klondike.
 */
public class ReglasJuego {

    /**
     * Comprueba si una carta o grupo puede colocarse
     * sobre una columna.
     *
     * @param cartaBase primera carta del grupo que se mueve.
     * @param destino columna que recibirá las cartas.
     * @return true si el movimiento está permitido.
     */
    public boolean puedeColocarEnColumna(
            Carta cartaBase, ListaDoble<Carta> destino) {

        if (cartaBase == null || !cartaBase.isDescubierta()) {
            return false;
        }

        // Una columna vacía solamente acepta un Rey.
        if (destino.estaVacia()) {
            return cartaBase.getValor() == 13;
        }

        Carta cartaDestino = destino.obtener(
                destino.tamanio() - 1);

        if (cartaDestino == null
                || !cartaDestino.isDescubierta()) {
            return false;
        }

        // Deben alternarse los colores.
        boolean coloresAlternos =
                cartaBase.esRoja() != cartaDestino.esRoja();

        // La carta que recibe debe tener un valor superior.
        boolean valorCorrecto =
                cartaDestino.getValor() == cartaBase.getValor() + 1;

        return coloresAlternos && valorCorrecto;
    }

    /**
     * Comprueba que todas las cartas de un grupo
     * formen una secuencia válida.
     *
     * @param columna columna de origen.
     * @param posicion posición desde la que se mueve.
     * @return true si el grupo está correctamente ordenado.
     */
    public boolean esSecuenciaValida(
            ListaDoble<Carta> columna, int posicion) {

        if (posicion < 0 || posicion >= columna.tamanio()) {
            return false;
        }

        for (int i = posicion; i < columna.tamanio(); i++) {
            Carta carta = columna.obtener(i);

            if (carta == null || !carta.isDescubierta()) {
                return false;
            }

            if (i < columna.tamanio() - 1) {
                Carta siguiente = columna.obtener(i + 1);

                boolean coloresAlternos =
                        carta.esRoja() != siguiente.esRoja();

                boolean valorCorrecto =
                        carta.getValor() == siguiente.getValor() + 1;

                if (!coloresAlternos || !valorCorrecto) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Comprueba si una carta puede entrar a una fundación.
     *
     * @param carta carta que se desea colocar.
     * @param fundacion pila de destino.
     * @return true si el movimiento es válido.
     */
    public boolean puedeColocarEnFundacion(
            Carta carta, Pila<Carta> fundacion) {

        if (carta == null || !carta.isDescubierta()) {
            return false;
        }

        if (fundacion.estaVacia()) {
            return carta.getValor() == 1;
        }

        Carta superior = fundacion.verTope();

        return carta.getPalo() == superior.getPalo()
                && carta.getValor() == superior.getValor() + 1;
    }
}
