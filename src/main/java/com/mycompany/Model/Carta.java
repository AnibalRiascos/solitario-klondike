


package com.mycompany.Model;




/**
 * Representa una carta de la baraja francesa.
 */
public class Carta {

    private int valor;
    private Palo palo;
    private boolean descubierta;

    /**
     * Crea una carta.
     * @param valor valor entre 1 y 13.
     * @param palo palo de la carta.
     */
    public Carta(int valor, Palo palo) {
        if (valor < 1 || valor > 13 || palo == null) {
            throw new IllegalArgumentException("Carta inválida");
        }

        this.valor = valor;
        this.palo = palo;
        this.descubierta = false;
    }

    /**
     * Obtiene el valor.
     * @return valor de la carta.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Obtiene el palo.
     * @return palo de la carta.
     */
    public Palo getPalo() {
        return palo;
    }

    /**
     * Comprueba si la carta está descubierta.
     * @return true si está descubierta.
     */
    public boolean isDescubierta() {
        return descubierta;
    }

    /**
     * Cambia el estado de visibilidad.
     * @param descubierta nuevo estado.
     */
    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    /**
     * Comprueba si la carta es roja.
     * @return true para corazones y diamantes.
     */
    public boolean esRoja() {
        return palo == Palo.CORAZONES
                || palo == Palo.DIAMANTES;
    }

    /**
     * Devuelve el nombre corto del valor.
     * @return A, J, Q, K o número.
     */
    public String nombreValor() {
        switch (valor) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(valor);
        }
    }

    /**
     * Devuelve el símbolo del palo.
     * @return símbolo correspondiente.
     */
    public String simboloPalo() {
        switch (palo) {
            case CORAZONES:
                return "♥";
            case DIAMANTES:
                return "♦";
            case TREBOLES:
                return "♣";
            case PICAS:
                return "♠";
            default:
                return "?";
        }
    }

    /**
     * Representación textual de la carta.
     * @return carta visible o carta tapada.
     */
    @Override
    public String toString() {
        if (!descubierta) {
            return "[##]";
        }

        return "[" + nombreValor() + simboloPalo() + "]";
    }

    /**
     * Compara dos cartas por valor y palo.
     * @param objeto objeto que se comparará.
     * @return true si representan la misma carta.
     */
    @Override
    public boolean equals(Object objeto) {
        if (!(objeto instanceof Carta)) {
            return false;
        }

        Carta otra = (Carta) objeto;

        return valor == otra.valor && palo == otra.palo;
    }

    /**
     * Genera el código hash de la carta.
     * @return código basado en valor y palo.
     */
    @Override
    public int hashCode() {
        return valor * 31 + palo.hashCode();
    }
}
