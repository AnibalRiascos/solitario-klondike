/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Model;





import java.util.Random;
import com.mycompany.Modelo.Estructura.Cola;

/**
 * Crea y mezcla una baraja francesa de 52 cartas.
 */
public class Mazo {

    /**
     * Crea una cola con las 52 cartas mezcladas.
     * @return cola de cartas preparada para repartir.
     */
    public Cola<Carta> crearColaMezclada() {
        Carta[] cartas = new Carta[52];
        int posicion = 0;

        // Crear las 52 cartas.
        for (Palo palo : Palo.values()) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas[posicion] = new Carta(valor, palo);
                posicion++;
            }
        }

        // Mezclar las cartas mediante Fisher-Yates.
        Random aleatorio = new Random();

        for (int i = cartas.length - 1; i > 0; i--) {
            int j = aleatorio.nextInt(i + 1);

            Carta temporal = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temporal;
        }

        // Encolar las cartas mezcladas.
        Cola<Carta> cola = new Cola<>();

        for (Carta carta : cartas) {
            cola.encolar(carta);
        }

        return cola;
    }
}
