
package com.mycompany.Modelo.Estructura;




/**
 * Nodo genérico utilizado por las estructuras enlazadas.
 * Almacena un dato y una referencia al siguiente nodo.
 *
 * @param <T> tipo de dato almacenado.
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> siguiente;

    /**
     * Crea un nodo con un dato.
     * @param dato información que almacenará el nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    /**
     * Obtiene el dato del nodo.
     * @return dato almacenado.
     */
    public T getDato() {
        return dato;
    }

    /**
     * Modifica el dato del nodo.
     * @param dato nuevo dato.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Obtiene el siguiente nodo.
     * @return referencia al siguiente nodo.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el siguiente nodo.
     * @param siguiente nodo que continúa la cadena.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
