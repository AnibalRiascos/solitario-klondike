

package com.mycompany.Modelo.Estructura;




/**
 * Lista circular simplemente enlazada.
 *
 * @param <T> tipo de dato almacenado.
 */
public class ListaCircular<T> {

    private class NodoCircular {
        T dato;
        NodoCircular siguiente;

        NodoCircular(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private NodoCircular cabeza;
    private NodoCircular actual;
    private int tamanio;

    /**
     * Inicializa una lista circular vacía.
     */
    public ListaCircular() {
        cabeza = null;
        actual = null;
        tamanio = 0;
    }

    /**
     * Inserta un elemento al inicio.
     * @param dato elemento que se insertará.
     */
    public void insertarInicio(T dato) {
        NodoCircular nuevo = new NodoCircular(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            nuevo.siguiente = nuevo;
            actual = nuevo;
        } else {
            NodoCircular ultimo = cabeza;

            while (ultimo.siguiente != cabeza) {
                ultimo = ultimo.siguiente;
            }

            nuevo.siguiente = cabeza;
            ultimo.siguiente = nuevo;
            cabeza = nuevo;
        }

        tamanio++;
    }

    /**
     * Inserta un elemento al final.
     * @param dato elemento que se insertará.
     */
    public void insertarFinal(T dato) {
        NodoCircular nuevo = new NodoCircular(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            nuevo.siguiente = nuevo;
            actual = nuevo;
        } else {
            NodoCircular ultimo = cabeza;

            while (ultimo.siguiente != cabeza) {
                ultimo = ultimo.siguiente;
            }

            ultimo.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }

        tamanio++;
    }

    /**
     * Inserta un elemento en una posición.
     * La primera posición es 0.
     * @param posicion ubicación donde se insertará.
     * @param dato elemento que se insertará.
     * @return true si se insertó correctamente.
     */
    public boolean insertarEnPosicion(int posicion, T dato) {
        if (posicion < 0 || posicion > tamanio) {
            return false;
        }

        if (posicion == 0) {
            insertarInicio(dato);
            return true;
        }

        if (posicion == tamanio) {
            insertarFinal(dato);
            return true;
        }

        NodoCircular anterior = cabeza;

        for (int i = 0; i < posicion - 1; i++) {
            anterior = anterior.siguiente;
        }

        NodoCircular nuevo = new NodoCircular(dato);
        nuevo.siguiente = anterior.siguiente;
        anterior.siguiente = nuevo;

        tamanio++;
        return true;
    }

    /**
     * Elimina la primera aparición de un dato.
     * @param dato elemento que se desea eliminar.
     * @return true si se encontró y eliminó.
     */
    public boolean eliminar(T dato) {
        if (cabeza == null) {
            return false;
        }

        NodoCircular anterior = cabeza;
        while (anterior.siguiente != cabeza) {
            anterior = anterior.siguiente;
        }

        NodoCircular nodo = cabeza;

        for (int i = 0; i < tamanio; i++) {
            if (nodo.dato.equals(dato)) {
                if (tamanio == 1) {
                    cabeza = null;
                    actual = null;
                } else {
                    anterior.siguiente = nodo.siguiente;

                    if (nodo == cabeza) {
                        cabeza = nodo.siguiente;
                    }

                    if (nodo == actual) {
                        actual = nodo.siguiente;
                    }
                }

                tamanio--;
                return true;
            }

            anterior = nodo;
            nodo = nodo.siguiente;
        }

        return false;
    }

    /**
     * Extrae el elemento señalado por el cursor actual.
     * @return elemento extraído o null si está vacía.
     */
    public T eliminarActual() {
        if (cabeza == null) {
            return null;
        }

        NodoCircular anterior = actual;

        while (anterior.siguiente != actual) {
            anterior = anterior.siguiente;
        }

        T dato = actual.dato;

        if (tamanio == 1) {
            cabeza = null;
            actual = null;
        } else {
            anterior.siguiente = actual.siguiente;

            if (actual == cabeza) {
                cabeza = actual.siguiente;
            }

            actual = actual.siguiente;
        }

        tamanio--;
        return dato;
    }

    /**
     * Busca un elemento y devuelve su posición.
     * @param dato elemento que se desea buscar.
     * @return posición encontrada o -1.
     */
    public int buscar(T dato) {
        NodoCircular nodo = cabeza;

        for (int i = 0; i < tamanio; i++) {
            if (nodo.dato.equals(dato)) {
                return i;
            }

            nodo = nodo.siguiente;
        }

        return -1;
    }

    /**
     * Devuelve el elemento de una posición.
     * @param posicion posición solicitada.
     * @return elemento o null si no existe.
     */
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            return null;
        }

        NodoCircular nodo = cabeza;

        for (int i = 0; i < posicion; i++) {
            nodo = nodo.siguiente;
        }

        return nodo.dato;
    }

    /**
     * Avanza el cursor una posición circular.
     * @return dato de la nueva posición actual.
     */
    public T avanzar() {
        if (actual == null) {
            return null;
        }

        actual = actual.siguiente;
        return actual.dato;
    }

    /**
     * Consulta el dato actual.
     * @return dato actual o null si está vacía.
     */
    public T verActual() {
        return actual == null ? null : actual.dato;
    }

    /**
     * Recorre todos los elementos una sola vez.
     * @return representación de los elementos.
     */
    public String recorrer() {
        if (cabeza == null) {
            return "Lista vacía";
        }

        StringBuilder resultado = new StringBuilder();
        NodoCircular nodo = cabeza;

        for (int i = 0; i < tamanio; i++) {
            resultado.append(nodo.dato).append(" -> ");
            nodo = nodo.siguiente;
        }

        resultado.append("(vuelve al inicio)");
        return resultado.toString();
    }

    /**
     * Comprueba si la lista está vacía.
     * @return true si no contiene elementos.
     */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /**
     * Devuelve la cantidad de elementos.
     * @return tamaño de la lista.
     */
    public int tamanio() {
        return tamanio;
    }
    
    
    /**
    * Coloca el cursor actual nuevamente en el primer nodo.
    */
    public void reiniciarActual() {
        actual = cabeza;
    }

}
