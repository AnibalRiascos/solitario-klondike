
package com.mycompany.solitario;




import com.mycompany.Modelo.Estructura.ArbolBinario;
import com.mycompany.Modelo.Estructura.Cola;
import com.mycompany.Modelo.Estructura.ListaCircular;
import com.mycompany.Modelo.Estructura.ListaDoble;
import com.mycompany.Modelo.Estructura.Pila;


/**
 * Prueba las operaciones principales de las cinco estructuras.
 */
public class PruebaEstructuras {

    /**
     * Ejecuta las pruebas.
     * @param args argumentos de ejecución.
     */
    public static void main(String[] args) {

        System.out.println("===== PRUEBA DE PILA =====");

        Pila<Integer> pila = new Pila<>();
        pila.apilar(10);
        pila.apilar(20);
        pila.apilar(30);

        System.out.println("Tope: " + pila.verTope());
        System.out.println("Retirado: " + pila.desapilar());
        System.out.println("Tamaño: " + pila.tamanio());
        System.out.println("¿Vacía?: " + pila.estaVacia());

        System.out.println("\n===== PRUEBA DE COLA =====");

        Cola<Integer> cola = new Cola<>();
        cola.encolar(10);
        cola.encolar(20);
        cola.encolar(30);

        System.out.println("Frente: " + cola.verFrente());
        System.out.println("Retirado: " + cola.desencolar());
        System.out.println("Tamaño: " + cola.tamanio());
        System.out.println("¿Vacía?: " + cola.estaVacia());

        System.out.println("\n===== PRUEBA DE LISTA CIRCULAR =====");

        ListaCircular<Integer> circular = new ListaCircular<>();
        circular.insertarInicio(20);
        circular.insertarFinal(30);
        circular.insertarEnPosicion(1, 25);

        System.out.println("Recorrido: " + circular.recorrer());
        System.out.println("Buscar 25: " + circular.buscar(25));
        System.out.println("Actual: " + circular.verActual());
        System.out.println("Avanzar: " + circular.avanzar());

        circular.eliminar(25);
        System.out.println("Después de eliminar: "
                + circular.recorrer());

        System.out.println("\n===== PRUEBA DE LISTA DOBLE =====");

        ListaDoble<Integer> doble = new ListaDoble<>();
        doble.insertarInicio(20);
        doble.insertarInicio(10);
        doble.insertarFinal(30);
        doble.insertarEnPosicion(2, 25);

        System.out.println("Adelante: " + doble.recorrerAdelante());
        System.out.println("Atrás: " + doble.recorrerAtras());
        System.out.println("Buscar 25: " + doble.buscar(25));

        doble.eliminar(25);
        System.out.println("Después de eliminar: "
                + doble.recorrerAdelante());

        System.out.println("\n===== PRUEBA DE ÁRBOL =====");

        ArbolBinario<Integer> arbol = new ArbolBinario<>();

        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        System.out.println("Buscar 40: " + arbol.buscar(40));
        System.out.println("Inorden: " + arbol.inorden());
        System.out.println("Preorden: " + arbol.preorden());
        System.out.println("Posorden: " + arbol.posorden());

        arbol.eliminar(30);

        System.out.println("Después de eliminar 30: "
                + arbol.inorden());
        
        
        
        System.out.println("\n===== CASOS ADICIONALES DE PILA =====");

        Pila<Integer> pilaVacia = new Pila<>();

        System.out.println("¿Pila vacía?: " + pilaVacia.estaVacia());
        System.out.println("Retirar de pila vacía: " + pilaVacia.desapilar());
        
        
        
        System.out.println("\n===== CASOS ADICIONALES DE COLA =====");

        Cola<Integer> colaVacia = new Cola<>();

        System.out.println("¿Cola vacía?: " + colaVacia.estaVacia());
        System.out.println("Retirar de cola vacía: " + colaVacia.desencolar());
        
        
        
        
        System.out.println("\n===== CASOS ADICIONALES DE ÁRBOL =====");

        System.out.println("Buscar 100: " + arbol.buscar(100));

        arbol.eliminar(20);

        System.out.println("Después de eliminar 20: " + arbol.inorden());



        
        

        System.out.println("\n===== FIN DE LAS PRUEBAS =====");
    }
}
