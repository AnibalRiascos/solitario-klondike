

package com.mycompany.Modelo.Estructura;



/**
 * Lista doblemente enlazada genérica.
 *
 * @param <T> tipo de dato almacenado.
 */
public class ListaDoble<T> {

    private class NodoDoble {
        T dato;
        NodoDoble anterior;
        NodoDoble siguiente;

        NodoDoble(T dato) {
            this.dato = dato;
        }
    }

    private NodoDoble cabeza;
    private NodoDoble cola;
    private int tamanio;

    /**
     * Inicializa una lista doble vacía.
     */
    public ListaDoble() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    /**
     * Inserta un elemento al inicio.
     * @param dato elemento que se insertará.
     */
    public void insertarInicio(T dato) {
        NodoDoble nuevo = new NodoDoble(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }

        tamanio++;
    }

    /**
     * Inserta un elemento al final.
     * @param dato elemento que se insertará.
     */
    public void insertarFinal(T dato) {
        NodoDoble nuevo = new NodoDoble(dato);

        if (cola == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }

        tamanio++;
    }

    /**
     * Inserta un elemento en una posición.
     * La primera posición es 0.
     * @param posicion ubicación de inserción.
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

        NodoDoble siguiente = obtenerNodo(posicion);
        NodoDoble anterior = siguiente.anterior;
        NodoDoble nuevo = new NodoDoble(dato);

        nuevo.anterior = anterior;
        nuevo.siguiente = siguiente;
        anterior.siguiente = nuevo;
        siguiente.anterior = nuevo;

        tamanio++;
        return true;
    }

    /**
     * Elimina la primera aparición de un dato.
     * @param dato elemento que se desea eliminar.
     * @return true si se eliminó.
     */
    public boolean eliminar(T dato) {
        NodoDoble actual = cabeza;

        while (actual != null) {
            if (actual.dato.equals(dato)) {
                if (actual.anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    actual.anterior.siguiente = actual.siguiente;
                }

                if (actual.siguiente == null) {
                    cola = actual.anterior;
                } else {
                    actual.siguiente.anterior = actual.anterior;
                }

                tamanio--;
                return true;
            }

            actual = actual.siguiente;
        }

        return false;
    }

    /**
     * Busca un dato y devuelve su posición.
     * @param dato elemento que se desea buscar.
     * @return posición o -1 si no existe.
     */
    public int buscar(T dato) {
        NodoDoble actual = cabeza;
        int posicion = 0;

        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return posicion;
            }

            actual = actual.siguiente;
            posicion++;
        }

        return -1;
    }

    /**
     * Obtiene un dato por su posición.
     * @param posicion posición solicitada.
     * @return dato o null si no existe.
     */
    public T obtener(int posicion) {
        NodoDoble nodo = obtenerNodo(posicion);
        return nodo == null ? null : nodo.dato;
    }

    /**
     * Busca internamente un nodo por posición.
     * @param posicion índice solicitado.
     * @return nodo encontrado o null.
     */
    private NodoDoble obtenerNodo(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            return null;
        }

        if (posicion < tamanio / 2) {
            NodoDoble actual = cabeza;

            for (int i = 0; i < posicion; i++) {
                actual = actual.siguiente;
            }

            return actual;
        } else {
            NodoDoble actual = cola;

            for (int i = tamanio - 1; i > posicion; i--) {
                actual = actual.anterior;
            }

            return actual;
        }
    }

    /**
     * Mueve todos los nodos desde una posición
     * hasta el final de esta lista hacia otra lista.
     * No crea copias de los datos.
     *
     * @param posicion posición desde la que se moverán.
     * @param destino lista que recibirá los nodos.
     * @return true si se realizó el movimiento.
     */
    public boolean moverDesde(int posicion, ListaDoble<T> destino) {
        if (destino == null || destino == this
                || posicion < 0 || posicion >= tamanio) {
            return false;
        }

        NodoDoble primero = obtenerNodo(posicion);
        NodoDoble ultimo = cola;

        int cantidad = tamanio - posicion;

        // Desconectar el grupo de la lista original.
        if (primero.anterior == null) {
            cabeza = null;
            cola = null;
        } else {
            cola = primero.anterior;
            cola.siguiente = null;
        }

        primero.anterior = null;

        // Conectar el grupo al final de la lista destino.
        if (destino.cola == null) {
            destino.cabeza = primero;
        } else {
            destino.cola.siguiente = primero;
            primero.anterior = destino.cola;
        }

        destino.cola = ultimo;

        this.tamanio -= cantidad;
        destino.tamanio += cantidad;

        return true;
    }

    /**
     * Recorre la lista desde el inicio hasta el final.
     * @return elementos en orden.
     */
    public String recorrerAdelante() {
        StringBuilder resultado = new StringBuilder();
        NodoDoble actual = cabeza;

        while (actual != null) {
            resultado.append(actual.dato).append(" ");
            actual = actual.siguiente;
        }

        return resultado.toString();
    }

    /**
     * Recorre la lista desde el final hasta el inicio.
     * @return elementos en orden inverso.
     */
    public String recorrerAtras() {
        StringBuilder resultado = new StringBuilder();
        NodoDoble actual = cola;

        while (actual != null) {
            resultado.append(actual.dato).append(" ");
            actual = actual.anterior;
        }

        return resultado.toString();
    }

    /**
     * Comprueba si la lista está vacía.
     * @return true si está vacía.
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
}
