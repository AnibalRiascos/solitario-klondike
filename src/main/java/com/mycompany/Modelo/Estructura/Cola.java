

package com.mycompany.Modelo.Estructura;




/**
 * Implementación propia de una cola genérica.
 * Funciona bajo el principio FIFO.
 *
 * @param <T> tipo de dato almacenado.
 */
public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> finalCola;
    private int tamanio;

    /**
     * Inicializa una cola vacía.
     */
    public Cola() {
        frente = null;
        finalCola = null;
        tamanio = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param dato elemento que se desea agregar.
     */
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (estaVacia()) {
            frente = nuevo;
            finalCola = nuevo;
        } else {
            finalCola.setSiguiente(nuevo);
            finalCola = nuevo;
        }

        tamanio++;
    }

    /**
     * Retira y devuelve el primer elemento.
     * @return elemento retirado o null si está vacía.
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }

        T dato = frente.getDato();
        frente = frente.getSiguiente();
        tamanio--;

        if (frente == null) {
            finalCola = null;
        }

        return dato;
    }

    /**
     * Consulta el primer elemento sin retirarlo.
     * @return primer elemento o null si está vacía.
     */
    public T verFrente() {
        if (estaVacia()) {
            return null;
        }

        return frente.getDato();
    }

    /**
     * Comprueba si la cola está vacía.
     * @return true si está vacía.
     */
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Consulta la cantidad de elementos.
     * @return número de elementos.
     */
    public int tamanio() {
        return tamanio;
    }
}
