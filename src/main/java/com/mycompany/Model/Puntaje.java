

package com.mycompany.Model;




/**
 * Representa el nombre y la puntuación de una partida.
 */
public class Puntaje implements Comparable<Puntaje> {

    private String nombre;
    private int puntos;

    /**
     * Crea un registro de puntuación.
     * @param nombre nombre del jugador.
     * @param puntos puntuación obtenida.
     */
    public Puntaje(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    /**
     * Obtiene el nombre.
     * @return nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene los puntos.
     * @return puntuación.
     */
    public int getPuntos() {
        return puntos;
    }

    
@Override
public int compareTo(Puntaje otro) {
    if (this.puntos != otro.puntos) {
        return Integer.compare(otro.puntos, this.puntos);
    }

    return this.nombre.compareToIgnoreCase(otro.nombre);
}


    /**
     * Representación textual del puntaje.
     * @return nombre y puntos.
     */
    @Override
    public String toString() {
        return nombre + " - " + puntos + " puntos";
    }
}
