
package com.mycompany.Modelo.Estructura;



/**
 * Implementación propia de una pila genérica.
 * Funciona bajo el principio LIFO.
 *
 * @param <T> tipo de dato almacenado.
 */
public class Pila<T> {

    private Nodo<T> tope;
    private int tamanio;

    /**
     * Inicializa una pila vacía.
     */
    public Pila() {
        tope = null;
        tamanio = 0;
    }

    /**
     * Agrega un elemento en la parte superior.
     * @param dato elemento que se desea agregar.
     */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(tope);
        tope = nuevo;
        tamanio++;
    }

    /**
     * Retira y devuelve el elemento superior.
     * @return elemento retirado o null si está vacía.
     */
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }

        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamanio--;

        return dato;
    }

    /**
     * Consulta el elemento superior sin retirarlo.
     * @return elemento superior o null si está vacía.
     */
    public T verTope() {
        if (estaVacia()) {
            return null;
        }

        return tope.getDato();
    }

    /**
     * Comprueba si la pila está vacía.
     * @return true si está vacía.
     */
    public boolean estaVacia() {
        return tope == null;
    }

    /**
     * Consulta el número de elementos.
     * @return cantidad de elementos.
     */
    public int tamanio() {
        return tamanio;
    }

    /**
     * Elimina todos los elementos de la pila.
     */
    public void vaciar() {
        tope = null;
        tamanio = 0;
    }
}
