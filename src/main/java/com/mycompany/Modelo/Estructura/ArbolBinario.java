


package com.mycompany.Modelo.Estructura;




/**
 * Árbol binario de búsqueda genérico.
 *
 * @param <T> tipo de dato comparable.
 */
public class ArbolBinario<T extends Comparable<T>> {

    private class NodoArbol {
        T dato;
        NodoArbol izquierdo;
        NodoArbol derecho;

        NodoArbol(T dato) {
            this.dato = dato;
            izquierdo = null;
            derecho = null;
        }
    }

    private NodoArbol raiz;

    /**
     * Crea un árbol vacío.
     */
    public ArbolBinario() {
        raiz = null;
    }

    /**
     * Inserta un elemento manteniendo el orden del árbol.
     * @param dato elemento que se insertará.
     * @return true si se insertó; false si ya existía.
     */
    public boolean insertar(T dato) {
        if (buscar(dato)) {
            return false;
        }

        raiz = insertarRecursivo(raiz, dato);
        return true;
    }

    /**
     * Inserta recursivamente un elemento.
     * @param nodo posición actual del árbol.
     * @param dato elemento que se insertará.
     * @return raíz del subárbol actualizado.
     */
    private NodoArbol insertarRecursivo(NodoArbol nodo, T dato) {
        if (nodo == null) {
            return new NodoArbol(dato);
        }

        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        }

        return nodo;
    }

    /**
     * Busca un elemento.
     * @param dato elemento que se desea encontrar.
     * @return true si existe.
     */
    public boolean buscar(T dato) {
        NodoArbol actual = raiz;

        while (actual != null) {
            int comparacion = dato.compareTo(actual.dato);

            if (comparacion == 0) {
                return true;
            } else if (comparacion < 0) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
        }

        return false;
    }

    /**
     * Elimina un elemento del árbol.
     * @param dato elemento que se desea eliminar.
     * @return true si se encontró y eliminó.
     */
    public boolean eliminar(T dato) {
        if (!buscar(dato)) {
            return false;
        }

        raiz = eliminarRecursivo(raiz, dato);
        return true;
    }

    /**
     * Elimina recursivamente un elemento.
     * @param nodo raíz del subárbol actual.
     * @param dato elemento que se desea eliminar.
     * @return raíz del subárbol actualizado.
     */
    private NodoArbol eliminarRecursivo(NodoArbol nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = dato.compareTo(nodo.dato);

        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, dato);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRecursivo(nodo.derecho, dato);
        } else {

            // Caso 1: no tiene hijo izquierdo.
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }

            // Caso 2: no tiene hijo derecho.
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }

            // Caso 3: tiene dos hijos.
            NodoArbol sucesor = encontrarMenor(nodo.derecho);
            nodo.dato = sucesor.dato;
            nodo.derecho = eliminarRecursivo(
                    nodo.derecho, sucesor.dato);
        }

        return nodo;
    }

    /**
     * Encuentra el nodo con el menor valor.
     * @param nodo raíz del subárbol.
     * @return nodo con el menor valor.
     */
    private NodoArbol encontrarMenor(NodoArbol nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }

        return nodo;
    }

    /**
     * Recorrido inorden: izquierda, raíz, derecha.
     * @return elementos ordenados.
     */
    public String inorden() {
        StringBuilder resultado = new StringBuilder();
        inordenRecursivo(raiz, resultado);
        return resultado.toString();
    }

    private void inordenRecursivo(
            NodoArbol nodo, StringBuilder resultado) {

        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo, resultado);
            resultado.append(nodo.dato).append(" ");
            inordenRecursivo(nodo.derecho, resultado);
        }
    }

    /**
     * Recorrido preorden: raíz, izquierda, derecha.
     * @return elementos en preorden.
     */
    public String preorden() {
        StringBuilder resultado = new StringBuilder();
        preordenRecursivo(raiz, resultado);
        return resultado.toString();
    }

    private void preordenRecursivo(
            NodoArbol nodo, StringBuilder resultado) {

        if (nodo != null) {
            resultado.append(nodo.dato).append(" ");
            preordenRecursivo(nodo.izquierdo, resultado);
            preordenRecursivo(nodo.derecho, resultado);
        }
    }

    /**
     * Recorrido posorden: izquierda, derecha, raíz.
     * @return elementos en posorden.
     */
    public String posorden() {
        StringBuilder resultado = new StringBuilder();
        posordenRecursivo(raiz, resultado);
        return resultado.toString();
    }

    private void posordenRecursivo(
            NodoArbol nodo, StringBuilder resultado) {

        if (nodo != null) {
            posordenRecursivo(nodo.izquierdo, resultado);
            posordenRecursivo(nodo.derecho, resultado);
            resultado.append(nodo.dato).append(" ");
        }
    }

    /**
     * Comprueba si el árbol está vacío.
     * @return true si no tiene elementos.
     */
    public boolean estaVacio() {
        return raiz == null;
    }
}
