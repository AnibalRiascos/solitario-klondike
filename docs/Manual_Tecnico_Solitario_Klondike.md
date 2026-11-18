# MANUAL TÉCNICO

## Solitario Klondike

*Arquitectura, estructuras de datos, pruebas y mantenimiento*

| Campo | Detalle |
|---|---|
| Estudiante | John Anibal Riascos |
| Asignatura | Estructuras de Datos |
| Programa / Institución | ______________________________ |
| Fecha | 20 de noviembre de 2026 |
| Versión | 1.0 |

> Documento técnico del proyecto de Estructuras de Datos.

---

## Contenido

- 1. Resumen ejecutivo
- 2. Objetivos técnicos
- 3. Requisitos y entorno
  - 3.1 Requisitos de software
  - 3.2 Verificación del entorno
- 4. Organización del código
  - 4.1 Estructura de paquetes
  - 4.2 Responsabilidades por paquete
  - 4.3 Clases y responsabilidades
  - 4.4 Puntos de entrada
- 5. Diagrama de clases
  - 5.1 Lectura del diagrama
  - 5.2 Relaciones principales
- 6. Estructuras de datos implementadas
  - 6.1 Pila (`Pila<T>`)
  - 6.2 Cola (`Cola<T>`)
  - 6.3 Lista circular (`ListaCircular<T>`)
  - 6.4 Lista doble (`ListaDoble<T>`)
  - 6.5 Árbol binario de búsqueda (`ArbolBinario<T>`)
  - 6.6 Resumen de complejidades
- 7. Flujo de funcionamiento
  - 7.1 Inicio de la aplicación
  - 7.2 Robo de cartas
  - 7.3 Movimiento de cartas
  - 7.4 Fin de la partida
- 8. Reglas y validaciones implementadas
- 9. Pruebas realizadas
  - 9.1 Estrategia
  - 9.2 Casos, resultados esperados y obtenidos
  - 9.3 Cobertura de operaciones
  - 9.4 Limitaciones de las pruebas
- 10. Compilación y ejecución
  - 10.1 Compilación con `javac`
  - 10.2 Compilación con Maven
  - 10.3 Ejecución desde NetBeans
  - 10.4 Ejecución de las pruebas
  - 10.5 Verificación del jar
  - 10.6 Errores frecuentes
- 11. Decisiones de diseño y limitaciones
  - 11.1 Decisiones de diseño
  - 11.2 Limitaciones conocidas
  - 11.3 Mejoras futuras
- 12. Conclusiones
- 13. Referencias
- Anexo A. Salida de `PruebaEstructuras`
- Anexo B. Verificación del reparto y del reciclaje
- Anexo C. Comandos rápidos

---

# 1. Resumen ejecutivo

El proyecto implementa el juego **Solitario Klondike** en Java 21 con interfaz gráfica Swing. La lógica del juego está separada de la interfaz y utiliza cinco estructuras de datos implementadas desde cero: **pila**, **cola**, **lista circular**, **lista doble** y **árbol binario de búsqueda**. El objetivo académico es demostrar el uso correcto de estas estructuras en un problema real, sin apoyarse en las colecciones de Java para la lógica del tablero.

El código se organiza en cinco paquetes: `Model`, `Modelo.Estructura`, `Controlador`, `Vista` y `solitario`. La clase `Solitario` inicia la interfaz; `ControladorJuego` coordina el tablero, las reglas, la puntuación y el ranking; `Tablero` concentra el estado de la partida; y las estructuras resuelven las operaciones de reparto, robo, columnas, fundaciones y ranking.

| Métrica | Valor |
|---|---|
| Clases Java | 16 clases + 1 enumeración (`Palo`) |
| Líneas de código Java | 3.301 líneas en `src/main/java` |
| Paquetes | 5 (`Model`, `Modelo.Estructura`, `Controlador`, `Vista`, `solitario`) |
| Estructuras obligatorias | 5, todas implementadas manualmente |
| Dependencias externas | Ninguna para ejecutar el juego |
| Pruebas | Clase `PruebaEstructuras` con salida por consola |
| Interfaz | Swing, ventana de 950 × 700 píxeles |
| Versión de Java | 21 (probado con Temurin 21.0.13) |
| Repositorio | https://github.com/AnibalRiascos/solitario-klondike |

# 2. Objetivos técnicos

- Implementar una partida funcional de Solitario Klondike con reglas completas de columnas y fundaciones.
- Usar las cinco estructuras solicitadas en operaciones reales del juego, no solo como demostración aislada.
- Separar la lógica del juego, la interfaz y el controlador para facilitar el mantenimiento.
- Validar cada movimiento antes de modificar el estado del tablero.
- Documentar el código con comentarios Javadoc y ofrecer una clase de prueba reproducible.
- Generar un proyecto compilable con `javac`, NetBeans o Maven y un jar ejecutable.

# 3. Requisitos y entorno

## 3.1 Requisitos de software

| Herramienta | Versión | Uso |
|---|---|---|
| JDK | 21 o superior | Compilación y ejecución. Probado con Temurin 21.0.13. |
| NetBeans | Recomendado | Apertura del proyecto y ejecución con F6. |
| Maven | 3.9 o superior (opcional) | Compilación y generación del jar ejecutable. |
| Sistema operativo | Windows, macOS o Linux | Debe tener entorno gráfico para Swing. |
| Memoria | 256 MB disponibles | Suficiente para la baraja y la interfaz. |
| Librerías externas | Ninguna | Solo se usan clases del JDK. |

El proyecto no requiere base de datos, servidor de aplicaciones ni conexión a internet para ejecutarse. Maven puede necesitar internet la primera vez para descargar sus plugins, pero el juego en sí no tiene dependencias externas.

## 3.2 Verificación del entorno

```text
java -version
javac -version
```

Salida esperada en el equipo de desarrollo:

```text
openjdk version "21.0.13" 2026-10-20 LTS
OpenJDK Runtime Environment Temurin-21.0.13+9 (build 21.0.13+9-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.13+9 (build 21.0.13+9-LTS, mixed mode, sharing)
javac 21.0.13
```

> **Nota.** Si `java` o `javac` no están en el `PATH`, usa la ruta completa del JDK. En este equipo: `/home/anibalitho/jdk-21.0.13+9/bin`.

---

# 4. Organización del código

## 4.1 Estructura de paquetes

```text
Solitario/
├── pom.xml
├── README.md
└── src/main/java/com/mycompany/
    ├── Model/
    │   ├── Carta.java
    │   ├── Mazo.java
    │   ├── Palo.java
    │   ├── Puntaje.java
    │   └── Tablero.java
    ├── Modelo/Estructura/
    │   ├── Pila.java
    │   ├── Cola.java
    │   ├── ListaCircular.java
    │   ├── ListaDoble.java
    │   ├── ArbolBinario.java
    │   └── Nodo.java
    ├── Controlador/
    │   ├── ControladorJuego.java
    │   └── ReglasJuego.java
    ├── Vista/
    │   ├── VentanaJuego.java
    │   └── MenuConsola.java
    └── solitario/
        ├── Solitario.java
        └── PruebaEstructuras.java
```

La distribución sigue una separación cercana al patrón **Modelo–Vista–Controlador (MVC)**. El modelo no conoce Swing; la vista no implementa reglas; el controlador actúa como intermediario y actualiza la puntuación y el estado de la partida.

## 4.2 Responsabilidades por paquete

| Paquete | Responsabilidad | Clases principales |
|---|---|---|
| `Model` | Estado y reglas del juego, cartas, mazo y puntuaciones. | `Tablero`, `Carta`, `Mazo`, `Palo`, `Puntaje` |
| `Modelo.Estructura` | Estructuras de datos reutilizables implementadas desde cero. | `Pila`, `Cola`, `ListaCircular`, `ListaDoble`, `ArbolBinario`, `Nodo` |
| `Controlador` | Coordina la partida, valida movimientos y administra puntos y ranking. | `ControladorJuego`, `ReglasJuego` |
| `Vista` | Interfaz gráfica Swing y menú de consola alternativo. | `VentanaJuego`, `MenuConsola` |
| `solitario` | Punto de entrada y prueba de estructuras. | `Solitario`, `PruebaEstructuras` |

## 4.3 Clases y responsabilidades

| Clase | Paquete | Responsabilidad | Métodos clave |
|---|---|---|---|
| `Solitario` | `solitario` | Punto de entrada de la aplicación gráfica. | `main` |
| `VentanaJuego` | `Vista` | Ventana Swing, botones, diálogos y actualización del tablero. | `VentanaJuego`, `crearComponentes`, `nuevaPartida` |
| `MenuConsola` | `Vista` | Menú de consola alternativo; no es el punto de entrada actual. | `iniciar`, `jugar` |
| `ControladorJuego` | `Controlador` | Coordina la partida, puntuación, ranking y fin de juego. | `iniciarPartida`, `robarCarta`, `mover...`, `actualizarEstado` |
| `ReglasJuego` | `Controlador` | Valida movimientos de columnas y fundaciones. | `puedeColocarEnColumna`, `esSecuenciaValida`, `puedeColocarEnFundacion` |
| `Tablero` | `Model` | Estado completo: columnas, fundaciones, mazo, descarte y reparto. | `iniciarPartida`, `robarCarta`, `mover...`, `hayVictoria` |
| `Mazo` | `Model` | Crea y mezcla las 52 cartas con Fisher–Yates. | `crearColaMezclada` |
| `Carta` | `Model` | Valor, palo, visibilidad y representación textual. | `esRoja`, `nombreValor`, `simboloPalo`, `toString` |
| `Palo` | `Model` | Enumeración de los cuatro palos. | `CORAZONES`, `DIAMANTES`, `TREBOLES`, `PICAS` |
| `Puntaje` | `Model` | Nombre y puntos; ordena de mayor a menor. | `compareTo`, `toString` |
| `Pila` | `Modelo.Estructura` | Estructura LIFO para fundaciones y descarte. | `apilar`, `desapilar`, `verTope` |
| `Cola` | `Modelo.Estructura` | Estructura FIFO para reparto y lote de robo. | `encolar`, `desencolar`, `verFrente` |
| `ListaCircular` | `Modelo.Estructura` | Mazo de robo circular con cursor. | `insertarInicio`, `eliminarActual`, `avanzar`, `reiniciarActual` |
| `ListaDoble` | `Modelo.Estructura` | Columnas del tablero y movimiento de grupos. | `insertarFinal`, `moverDesde`, `recorrerAdelante`, `recorrerAtras` |
| `ArbolBinario` | `Modelo.Estructura` | Ranking ordenado de puntajes. | `insertar`, `buscar`, `eliminar`, `inorden` |
| `Nodo` | `Modelo.Estructura` | Nodo enlazado usado por pila y cola. | `getDato`, `getSiguiente` |
| `PruebaEstructuras` | `solitario` | Demostración reproducible de las cinco estructuras. | `main` |

## 4.4 Puntos de entrada

- **Aplicación gráfica:** `com.mycompany.solitario.Solitario` (clase principal del jar).
- **Demostración de estructuras:** `com.mycompany.solitario.PruebaEstructuras`.
- **Menú de consola:** `MenuConsola` existe como alternativa, pero el `main` actual abre la interfaz Swing. Para usarlo habría que crear un punto de entrada que lo invoque.

El código está documentado con comentarios Javadoc en clases y métodos públicos. Los nombres de clases, métodos y variables están en español para mantener coherencia con el enunciado y con el dominio del juego.

# 5. Diagrama de clases

![Figura 4. Diagrama de clases del Solitario Klondike. Las flechas continuas indican dependencia o uso; los rombos indican composición con `Nodo`. Las dependencias hacia las estructuras se agrupan para no saturar el diagrama.](assets/04_diagrama_clases.png)

*Figura 4. Diagrama de clases del Solitario Klondike. Las flechas continuas indican dependencia o uso; los rombos indican composición con `Nodo`. Las dependencias hacia las estructuras se agrupan para no saturar el diagrama.*

## 5.1 Lectura del diagrama

El diagrama resume los paquetes, las clases principales y las dependencias. Los miembros se muestran de forma abreviada; las tablas de las secciones 4 y 6 detallan las responsabilidades y las operaciones.

- **solitario:** contiene el arranque de la aplicación y la prueba de estructuras.
- **Vista:** `VentanaJuego` captura las acciones del usuario; `MenuConsola` es una alternativa por consola.
- **Controlador:** `ControladorJuego` conoce el `Tablero` y el ranking; `ReglasJuego` valida movimientos.
- **Model:** `Tablero` compone las columnas, fundaciones, mazo y descarte; `Carta` y `Palo` representan la baraja.
- **Modelo.Estructura:** implementaciones propias de pila, cola, lista circular, lista doble y árbol binario.

## 5.2 Relaciones principales

| Origen | Destino | Tipo | Motivo |
|---|---|---|---|
| `Solitario` | `VentanaJuego` | Dependencia | La clase principal crea y muestra la ventana. |
| `VentanaJuego` | `ControladorJuego` | Dependencia | La vista delega las acciones del usuario. |
| `MenuConsola` | `ControladorJuego` | Dependencia | El menú alternativo también usa el controlador. |
| `ControladorJuego` | `Tablero` | Dependencia | Coordina el estado de la partida. |
| `ControladorJuego` | `ArbolBinario<Puntaje>` | Dependencia | Mantiene el ranking ordenado. |
| `Tablero` | `Mazo` | Dependencia | Obtiene la cola mezclada para repartir. |
| `Tablero` | `Pila`, `Cola`, `ListaCircular`, `ListaDoble` | Composición | Representan fundaciones, reparto, mazo y columnas. |
| `Pila`, `Cola` | `Nodo` | Composición | Los nodos enlazados almacenan los elementos. |
| `Carta` | `Palo` | Dependencia | Cada carta pertenece a un palo. |
| `Puntaje` | `Comparable<Puntaje>` | Implementación | Permite ordenar el ranking en el árbol. |

---

# 6. Estructuras de datos implementadas

Las cinco estructuras están en el paquete `Modelo.Estructura`. Todas usan nodos enlazados y exponen operaciones básicas sin depender de `java.util.ArrayList`, `LinkedList`, `Stack`, `Queue` ni `TreeMap`.

## 6.1 Pila (`Pila<T>`)

### Uso en el juego

Se usa para las **cuatro fundaciones** y para la **pila de descarte**. En ambos casos solo importa la carta superior: la fundación recibe y devuelve la última carta, y el descarte muestra la carta que se puede jugar.

### Por qué se eligió

Porque el comportamiento es **LIFO** (last in, first out): la última carta apilada es la primera que se consulta o se retira. Una pila enlazada permite apilar y desapilar en tiempo constante.

| Operación | Descripción | Complejidad |
|---|---|---|
| `apilar(dato)` | Inserta un nodo en el tope. | O(1) |
| `desapilar()` | Retira y devuelve el tope; `null` si está vacía. | O(1) |
| `verTope()` | Consulta el tope sin retirarlo. | O(1) |
| `estaVacia()` | Indica si no hay elementos. | O(1) |
| `tamanio()` | Devuelve la cantidad de elementos. | O(1) |
| `vaciar()` | Elimina todos los elementos. | O(n) |

**Espacio:** O(n), donde n es el número de cartas de la pila. **Caso borde:** `desapilar` y `verTope` devuelven `null` cuando la pila está vacía; no lanzan excepción.

```text
public void apilar(T dato) {
    Nodo<T> nuevo = new Nodo<>(dato);
    nuevo.setSiguiente(tope);
    tope = nuevo;
    tamanio++;
}

public T desapilar() {
    if (tope == null) {
        return null;
    }
    T dato = tope.getDato();
    tope = tope.getSiguiente();
    tamanio--;
    return dato;
}
```

## 6.2 Cola (`Cola<T>`)

### Uso en el juego

Se usa en el **reparto inicial** y como **lote temporal de hasta tres cartas** durante el robo. Las cartas se encolan en el orden en que se sacan del mazo y se desencolan para pasarlas al descarte.

### Por qué se eligió

Porque el orden es **FIFO** (first in, first out): la primera carta que entra es la primera que sale. La cola mantiene referencias al frente y al final, por lo que encolar y desencolar son O(1).

| Operación | Descripción | Complejidad |
|---|---|---|
| `encolar(dato)` | Agrega un nodo al final. | O(1) |
| `desencolar()` | Retira y devuelve el frente; `null` si está vacía. | O(1) |
| `verFrente()` | Consulta el frente sin retirarlo. | O(1) |
| `estaVacia()` | Indica si no hay elementos. | O(1) |
| `tamanio()` | Devuelve la cantidad de elementos. | O(1) |

**Espacio:** O(n). **Caso borde:** `desencolar` y `verFrente` devuelven `null` si la cola está vacía.

```text
public void encolar(T dato) {
    Nodo<T> nuevo = new Nodo<>(dato);
    if (frente == null) {
        frente = nuevo;
        finalCola = nuevo;
    } else {
        finalCola.setSiguiente(nuevo);
        finalCola = nuevo;
    }
    tamanio++;
}
```

## 6.3 Lista circular (`ListaCircular<T>`)

### Uso en el juego

Representa el **mazo de robo**. El cursor `actual` señala la próxima carta que se entregará. Al robar, se extrae el nodo actual y el cursor avanza; cuando el mazo se vacía, el descarte se recicla y el cursor se reinicia.

### Por qué se eligió

Porque el mazo se recorre de forma circular: después de la última carta se vuelve a la primera sin crear una estructura nueva. La lista circular modela naturalmente ese recorrido y conserva el tamaño.

| Operación | Descripción | Complejidad |
|---|---|---|
| `insertarInicio(dato)` | Inserta al inicio; recorre hasta el último nodo. | O(n) |
| `insertarFinal(dato)` | Inserta al final; recorre hasta el último nodo. | O(n) |
| `insertarEnPosicion(pos, dato)` | Inserta en una posición 0..n. | O(n) |
| `eliminar(dato)` | Elimina la primera aparición. | O(n) |
| `eliminarActual()` | Extrae el nodo del cursor y avanza. | O(n) |
| `buscar(dato)` | Devuelve la posición o −1. | O(n) |
| `obtener(pos)` | Devuelve el dato en una posición. | O(n) |
| `avanzar()` | Mueve el cursor al siguiente nodo. | O(1) |
| `reiniciarActual()` | Coloca el cursor en la cabeza. | O(1) |
| `recorrer()` | Recorre todos los nodos una vez. | O(n) |

**Espacio:** O(n). **Observación:** `insertarInicio` y `insertarFinal` son O(n) porque solo se guarda la cabeza; para el mazo de 24 cartas el costo es despreciable. El reciclaje inserta las cartas una por una, por lo que su costo es O(n²) en el peor caso; con 24 cartas sigue siendo una operación pequeña.

## 6.4 Lista doble (`ListaDoble<T>`)

### Uso en el juego

Representa las **siete columnas**. Cada columna es una lista doblemente enlazada con referencia a la cabeza y a la cola. Esto permite insertar al final, eliminar una carta, consultar la carta superior y mover un grupo completo de una columna a otra.

### Por qué se eligió

Porque las columnas necesitan recorridos en ambos sentidos y operaciones en los extremos. La referencia a la cola permite insertar al final en O(1), y `moverDesde` reenlaza los nodos del grupo sin copiar los datos. También se usa para mostrar la columna en orden descendente y ascendente en las pruebas.

| Operación | Descripción | Complejidad |
|---|---|---|
| `insertarInicio(dato)` | Inserta al inicio. | O(1) |
| `insertarFinal(dato)` | Inserta al final usando la cola. | O(1) |
| `insertarEnPosicion(pos, dato)` | Inserta en una posición 0..n. | O(n) |
| `eliminar(dato)` | Elimina la primera aparición. | O(n) |
| `buscar(dato)` | Devuelve la posición o −1. | O(n) |
| `obtener(pos)` | Consulta por índice; elige cabeza o cola según la posición. | O(n) |
| `moverDesde(pos, destino)` | Mueve el grupo desde `pos` hasta el final a otra lista. | O(n) |
| `recorrerAdelante()` | Recorre de cabeza a cola. | O(n) |
| `recorrerAtras()` | Recorre de cola a cabeza. | O(n) |

**Espacio:** O(n). **Ventaja:** `moverDesde` no crea copias; desconecta el grupo y lo enlaza al final de la lista destino. Después del movimiento, `Tablero` revela la nueva carta superior de la columna de origen.

```text
public boolean moverDesde(int posicion, ListaDoble<T> destino) {
    NodoDoble primero = obtenerNodo(posicion);
    NodoDoble ultimo = cola;
    if (primero.anterior == null) { cabeza = null; cola = null; }
    else { cola = primero.anterior; cola.siguiente = null; }
    primero.anterior = null;
    // ... enlazar "primero" al final de la lista destino ...
    destino.cola = ultimo;
    destino.tamanio += tamanio - posicion;
    tamanio = posicion;
    return true;
}
```

## 6.5 Árbol binario de búsqueda (`ArbolBinario<T>`)

### Uso en el juego

Se usa para el **ranking de mejores puntajes**. Cada nodo guarda un `Puntaje`, que implementa `Comparable<Puntaje>` y ordena de mayor a menor. El recorrido inorden devuelve el ranking ordenado.

### Por qué se eligió

Porque mantiene los elementos ordenados y permite insertar, buscar y eliminar sin ordenar toda la colección. En un árbol equilibrado las operaciones son O(log n); en el peor caso, si el árbol se degenera, son O(n). Para el volumen de puntuaciones de una sesión, el rendimiento es más que suficiente.

| Operación | Descripción | Complejidad |
|---|---|---|
| `insertar(dato)` | Inserta respetando el orden del árbol. | O(h) promedio O(log n) |
| `buscar(dato)` | Busca un elemento. | O(h) promedio O(log n) |
| `eliminar(dato)` | Elimina un nodo; cubre hoja, un hijo y dos hijos. | O(h) promedio O(log n) |
| `inorden()` | Recorrido izquierda–raíz–derecha; devuelve el ranking. | O(n) |
| `preorden()` | Recorrido raíz–izquierda–derecha. | O(n) |
| `posorden()` | Recorrido izquierda–derecha–raíz. | O(n) |
| `estaVacio()` | Indica si no hay nodos. | O(1) |

**Espacio:** O(n). **Caso borde:** `buscar` y `eliminar` devuelven `false` si el elemento no existe; los recorridos devuelven una cadena vacía si el árbol está vacío.

```text
private NodoArbol insertarRecursivo(NodoArbol nodo, T dato) {
    if (nodo == null) {
        return new NodoArbol(dato);
    }
    int comparacion = dato.compareTo(nodo.dato);
    if (comparacion < 0) {
        nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
    } else if (comparacion > 0) {
        nodo.derecho = insertarRecursivo(nodo.derecho, dato);
    }
    return nodo;
}
```

## 6.6 Resumen de complejidades

| Estructura | Operaciones principales | Mejor / promedio | Peor caso | Espacio |
|---|---|---|---|---|
| Pila | apilar, desapilar, verTope | O(1) | O(1) | O(n) |
| Cola | encolar, desencolar, verFrente | O(1) | O(1) | O(n) |
| Lista circular | insertar, eliminar, buscar, avanzar | O(1) avanzar; O(n) búsquedas | O(n) | O(n) |
| Lista doble | insertar extremos, moverDesde, recorrer | O(1) extremos; O(n) búsquedas | O(n) | O(n) |
| Árbol binario | insertar, buscar, eliminar, recorridos | O(log n) | O(n) | O(n) |

---

# 7. Flujo de funcionamiento

## 7.1 Inicio de la aplicación

1. `Solitario.main` programa la creación de `VentanaJuego` en el hilo de eventos de Swing.
2. `VentanaJuego` crea un `ControladorJuego` y construye los componentes gráficos.
3. El controlador crea un `Tablero` y un `ArbolBinario<Puntaje>` para el ranking.
4. Al pulsar **Nueva partida**, el controlador reinicia el tablero, los puntos y el estado.
5. `Tablero.iniciarPartida` crea un `Mazo`, obtiene una `Cola<Carta>` mezclada y reparte las columnas.

El reparto deja **28 cartas** en las columnas (1+2+3+4+5+6+7) y **24 cartas** en la lista circular del mazo. Solo la última carta de cada columna se marca como descubierta.

## 7.2 Robo de cartas

1. La vista llama a `ControladorJuego.robarCarta()`.
2. Si el mazo está vacío, `Tablero.reciclarDescarte()` pasa las cartas del descarte al mazo y reinicia el cursor.
3. Si hay cartas, se extraen hasta tres con `ListaCircular.eliminarActual()`.
4. Las cartas se encolan temporalmente en una `Cola<Carta>` y luego se apilan en el descarte.
5. El controlador actualiza el estado y revisa si la partida terminó.

```text
public Carta robarCarta() {
    if (mazoRobo.estaVacia()) {
        reciclarDescarte();
        return null;
    }
    int cantidad = 0;
    while (cantidad < 3 && !mazoRobo.estaVacia()) {
        Carta carta = mazoRobo.eliminarActual();
        carta.setDescubierta(true);
        colaReparto.encolar(carta);
        cantidad++;
    }
    Carta ultima = null;
    while (!colaReparto.estaVacia()) {
        ultima = colaReparto.desencolar();
        descarte.apilar(ultima);
    }
    return ultima;
}
```

## 7.3 Movimiento de cartas

Todos los movimientos siguen el mismo flujo: la vista solicita los datos, el controlador delega en el tablero, el tablero consulta `ReglasJuego`, se modifica la estructura correspondiente y el controlador actualiza puntos y estado. Si el movimiento no es válido, no se modifica ninguna estructura.

| Acción | Método del tablero | Estructuras implicadas | Puntos |
|---|---|---|---|
| Descarte a columna | `moverDescarteAColumna` | `Pila` → `ListaDoble` | +5 |
| Columna a columna | `moverColumnaAColumna` | `ListaDoble` → `ListaDoble` | +5 |
| Columna a fundación | `moverColumnaAFundacion` | `ListaDoble` → `Pila` | +10 |
| Descarte a fundación | `moverDescarteAFundacion` | `Pila` → `Pila` | +10 |
| Fundación a columna | `moverFundacionAColumna` | `Pila` → `ListaDoble` | −5 (mínimo 0) |

## 7.4 Fin de la partida

Después de cada acción, `ControladorJuego.actualizarEstado()` comprueba dos condiciones:

- **Victoria:** `Tablero.hayVictoria()` verifica que las cuatro fundaciones tengan 13 cartas.
- **Derrota:** `Tablero.hayMovimientosDisponibles()` devuelve `false`. El método considera que hay movimientos si quedan cartas en el mazo o en el descarte, o si alguna carta puede ir a una fundación, a otra columna o devolverse desde una fundación.

Si hay victoria, el controlador registra el `Puntaje` en el árbol y muestra el mensaje final. Si no hay movimientos, muestra el mensaje de derrota. En ambos casos bloquea las acciones hasta una nueva partida.

# 8. Reglas y validaciones implementadas

| Validación | Método | Condición |
|---|---|---|
| Carta descubierta | `ReglasJuego.puedeColocarEnColumna` | La carta base y la carta superior del destino deben estar descubiertas. |
| Columna vacía | `ReglasJuego.puedeColocarEnColumna` | Solo se acepta un Rey (valor 13). |
| Color alternado | `ReglasJuego.puedeColocarEnColumna` | `cartaBase.esRoja() != cartaDestino.esRoja()`. |
| Orden descendente | `ReglasJuego.puedeColocarEnColumna` | `destino.valor == base.valor + 1`. |
| Secuencia de grupo | `ReglasJuego.esSecuenciaValida` | Todas las cartas desde la posición están descubiertas y ordenadas. |
| Fundación vacía | `ReglasJuego.puedeColocarEnFundacion` | Solo se acepta un As (valor 1). |
| Fundación no vacía | `ReglasJuego.puedeColocarEnFundacion` | Mismo palo y valor siguiente. |
| Victoria | `Tablero.hayVictoria` | Las cuatro fundaciones tienen tamaño 13. |
| Derrota | `Tablero.hayMovimientosDisponibles` | No quedan cartas por robar y no existe ningún movimiento válido. |

Las validaciones se concentran en `ReglasJuego` para evitar duplicar condiciones en la vista o en el controlador. El tablero solo modifica las estructuras después de que la regla correspondiente devuelve `true`.

# 9. Pruebas realizadas

## 9.1 Estrategia

El proyecto incluye la clase `PruebaEstructuras`, que ejecuta operaciones representativas de las cinco estructuras e imprime los resultados en consola. No se usó JUnit ni un framework de aserciones: las pruebas son manuales y reproducibles, y su salida se conserva como evidencia. También se ejecutó una verificación adicional del reparto y del reciclaje del mazo.

```text
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out com.mycompany.solitario.PruebaEstructuras
```

## 9.2 Casos, resultados esperados y obtenidos

| # | Caso | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|
| 1 | Pila: apilar 10, 20, 30; consultar y desapilar. | Tope 30, retirado 30, tamaño 2, no vacía. | Tope 30, retirado 30, tamaño 2, `false`. | Conforme |
| 2 | Pila vacía: consultar y desapilar. | `true` y `null`. | `true` y `null`. | Conforme |
| 3 | Cola: encolar 10, 20, 30; consultar y desencolar. | Frente 10, retirado 10, tamaño 2, no vacía. | Frente 10, retirado 10, tamaño 2, `false`. | Conforme |
| 4 | Cola vacía: consultar y desencolar. | `true` y `null`. | `true` y `null`. | Conforme |
| 5 | Lista circular: insertar 20 al inicio, 30 al final, 25 en posición 1; recorrer, buscar, avanzar y eliminar. | `20 -> 25 -> 30`; buscar 25 = 1; actual 20; avanzar 25; después de eliminar `20 -> 30`. | Exactamente los valores esperados. | Conforme |
| 6 | Lista doble: insertar 20, 10, 30 y 25; recorrer, buscar y eliminar. | Adelante `10 20 25 30`; atrás `30 25 20 10`; buscar 25 = 2; después de eliminar `10 20 30`. | Exactamente los valores esperados. | Conforme |
| 7 | Árbol: insertar 50, 30, 70, 20, 40, 60, 80; buscar 40 y recorrer. | Buscar 40 = `true`; inorden `20 30 40 50 60 70 80`; preorden `50 30 20 40 70 60 80`; posorden `20 40 30 60 80 70 50`. | Exactamente los valores esperados. | Conforme |
| 8 | Árbol: eliminar 30 (dos hijos), buscar 100 y eliminar 20 (hoja). | Inorden tras 30: `20 40 50 60 70 80`; buscar 100 = `false`; inorden tras 20: `40 50 60 70 80`. | Exactamente los valores esperados. | Conforme |
| 9 | Reparto inicial. | Columnas 1..7; 28 cartas en columnas; 24 en mazo; 0 en descarte; victoria `false`; movimientos `true`. | `1 2 3 4 5 6 7`; 28; 24; 0; `false`; `true`. | Conforme |
| 10 | Robar una vez. | Se roban 3 cartas; mazo 21; descarte 3. | Mazo 21; descarte 3; carta superior `[Q♣]`. | Conforme |
| 11 | Agotar el mazo y reciclar. | Tras 8 robos: mazo 0 y descarte 24; el siguiente robo devuelve `null` y recicla; mazo 24 y descarte 0. | Exactamente los valores esperados. | Conforme |

Los 11 escenarios produjeron los resultados esperados. La prueba no usa aserciones automáticas; la comparación se hace leyendo la salida. La salida completa está en los anexos A y B.

## 9.3 Cobertura de operaciones

| Estructura | Operaciones cubiertas | Operaciones no cubiertas en la prueba |
|---|---|---|
| Pila | `apilar`, `desapilar`, `verTope`, `estaVacia`, `tamanio` | `vaciar` |
| Cola | `encolar`, `desencolar`, `verFrente`, `estaVacia`, `tamanio` | — |
| Lista circular | `insertarInicio`, `insertarFinal`, `insertarEnPosicion`, `recorrer`, `buscar`, `verActual`, `avanzar`, `eliminar` | `eliminarActual`, `obtener`, `reiniciarActual` (se usan en el juego) |
| Lista doble | `insertarInicio`, `insertarFinal`, `insertarEnPosicion`, `recorrerAdelante`, `recorrerAtras`, `buscar`, `eliminar` | `obtener`, `moverDesde` (se usan en el juego) |
| Árbol binario | `insertar`, `buscar`, `inorden`, `preorden`, `posorden`, `eliminar` | `estaVacio` (se usa en el ranking) |

## 9.4 Limitaciones de las pruebas

- No hay pruebas automatizadas con JUnit ni aserciones; la evidencia es la salida impresa.
- No se prueba la interfaz gráfica de forma automatizada.
- No se simulan partidas completas ni se mide el rendimiento con muchas iteraciones.
- La verificación del reparto y del reciclaje se hizo con un programa auxiliar, no con una prueba unitaria.

# 10. Compilación y ejecución

## 10.1 Compilación con `javac`

Desde la carpeta que contiene `pom.xml`:

```text
# Linux / macOS
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")

# Ejecutar la interfaz gráfica
java -cp out com.mycompany.solitario.Solitario
```

Si el JDK no está en el `PATH`:

```text
JAVA=/home/anibalitho/jdk-21.0.13+9/bin
$JAVA/javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")
$JAVA/java -cp out com.mycompany.solitario.Solitario
```

> **Nota.** En Windows, `find` no está disponible. Usa NetBeans, Maven o compila indicando manualmente los archivos. También puedes usar PowerShell para generar la lista de fuentes.

## 10.2 Compilación con Maven

```text
mvn clean package
java -jar target/Solitario-1.0-SNAPSHOT.jar
```

El `pom.xml` configura `maven.compiler.release=21`, codificación UTF-8, la clase principal `com.mycompany.solitario.Solitario` y el plugin `maven-jar-plugin` 3.5.0 para generar un jar ejecutable.

## 10.3 Ejecución desde NetBeans

1. Abre NetBeans y selecciona **File → Open Project**.
2. Elige la carpeta `Solitario` (la que contiene `pom.xml`).
3. Pulsa **Run → Run Project** o **F6**.

## 10.4 Ejecución de las pruebas

```text
# Si compilaste con javac en out/
java -cp out com.mycompany.solitario.PruebaEstructuras

# Si generaste el jar con Maven
java -cp target/Solitario-1.0-SNAPSHOT.jar com.mycompany.solitario.PruebaEstructuras
```

## 10.5 Verificación del jar

```text
jar tf target/Solitario-1.0-SNAPSHOT.jar | head
java -jar target/Solitario-1.0-SNAPSHOT.jar
```

## 10.6 Errores frecuentes

| Error | Causa | Solución |
|---|---|---|
| `invalid target release: 21` | JDK anterior a 21. | Instala JDK 21 o superior y recompila. |
| `UnsupportedClassVersionError` | Clases compiladas con otra versión. | Borra `out` y `target/classes`; recompila con JDK 21. |
| `Could not find or load main class` | Classpath o nombre de clase incorrecto. | Ejecuta desde la carpeta del proyecto con `-cp out`. |
| `HeadlessException` | No hay entorno gráfico. | Abre la aplicación en una sesión de escritorio. |
| `mvn: command not found` | Maven no instalado o fuera del `PATH`. | Usa NetBeans o `javac`; Maven es opcional. |
| La ventana no aparece | La aplicación se ejecutó en segundo plano o sin pantalla. | Revisa la terminal y el entorno gráfico; ejecuta de nuevo. |

# 11. Decisiones de diseño y limitaciones

## 11.1 Decisiones de diseño

- **Estructuras propias:** se implementaron pila, cola, lista circular, lista doble y árbol binario para cumplir el requisito académico y demostrar el manejo de referencias.
- **Separación MVC:** la vista no contiene reglas; el modelo no conoce Swing; el controlador coordina.
- **Tablero textual:** se eligió una representación textual para concentrar el esfuerzo en las estructuras y las reglas, no en gráficos de cartas.
- **Lista circular para el mazo:** modela el reciclaje sin crear una nueva colección.
- **Lista doble para columnas:** permite mover grupos reenlazando nodos y recorrer en ambos sentidos.
- **Árbol para el ranking:** el recorrido inorden devuelve los puntajes ordenados de mayor a menor.
- **Validaciones centralizadas:** `ReglasJuego` concentra las condiciones de columnas y fundaciones.

## 11.2 Limitaciones conocidas

- La interfaz no tiene arrastrar y soltar, animaciones ni imágenes de cartas.
- No hay deshacer, rehacer, sugerencias, temporizador ni finalización automática.
- La puntuación y el ranking viven en memoria; no se guardan en disco.
- El ranking se mantiene en el controlador, pero la interfaz gráfica no tiene una pantalla para consultarlo; el menú de consola sí lo hace, aunque no es el punto de entrada actual.
- `hayMovimientosDisponibles` considera que hay juego mientras queden cartas en el mazo o en el descarte, aunque no exista una jugada útil en ese instante. Es una decisión conservadora: el jugador todavía puede robar o reciclar.
- Si el mazo y el descarte están vacíos, `robarCarta` puede devolver un mensaje de reciclaje aunque no haya cartas que reciclar; el estado de la partida se actualiza y puede marcarse como terminada. Es un caso borde menor.
- `ListaCircular.insertarInicio` y `insertarFinal` son O(n) porque no se guarda una referencia a la cola; el impacto es mínimo para 24 cartas.
- Las pruebas son manuales y no usan JUnit; no hay integración continua.
- La interfaz está pensada para escritorio y no se adapta a pantallas pequeñas ni a dispositivos móviles.

## 11.3 Mejoras futuras

- Agregar JUnit y pruebas automatizadas para las estructuras y las reglas.
- Persistir el ranking en un archivo o base de datos.
- Implementar arrastrar y soltar y una representación gráfica de las cartas.
- Agregar deshacer/rehacer y un historial de movimientos.
- Incluir sugerencias de movimientos y un solucionador automático.
- Incorporar temporizador, animaciones y sonidos.
- Optimizar la lista circular con referencia a la cola para inserciones O(1).

# 12. Conclusiones

El proyecto cumple los objetivos académicos: implementa las cinco estructuras solicitadas, las usa en operaciones concretas del Solitario Klondike y mantiene una separación razonable entre modelo, vista y controlador. La partida permite repartir, robar, reciclar, mover cartas y grupos, enviar cartas a las fundaciones, devolverlas y detectar victoria o derrota.

Las pruebas manuales confirman el comportamiento esperado de las estructuras y del reparto. Las principales oportunidades de mejora están en la automatización de pruebas, la persistencia del ranking, la interfaz gráfica y las funciones de ayuda al jugador.

# 13. Referencias

- Oracle. **Java SE 21 API Specification**. https://docs.oracle.com/en/java/javase/21/docs/api/
- Oracle. **The Java Tutorials: Creating a GUI With Swing**. https://docs.oracle.com/javase/tutorial/uiswing/
- Weiss, Mark Allen. **Data Structures and Algorithm Analysis in Java**, 3rd ed. Pearson, 2012.
- Sedgewick, Robert; Wayne, Kevin. **Algorithms**, 4th ed. Addison-Wesley, 2011.
- Bicycle Cards. **Klondike Solitaire Rules**. https://bicyclecards.com/how-to-play/klondike-solitaire/
- Repositorio del proyecto: https://github.com/AnibalRiascos/solitario-klondike

---

# Anexo A. Salida de `PruebaEstructuras`

Salida obtenida al ejecutar la clase de prueba con JDK 21.0.13:

```text
===== PRUEBA DE PILA =====
Tope: 30
Retirado: 30
Tamaño: 2
¿Vacía?: false

===== PRUEBA DE COLA =====
Frente: 10
Retirado: 10
Tamaño: 2
¿Vacía?: false

===== PRUEBA DE LISTA CIRCULAR =====
Recorrido: 20 -> 25 -> 30 -> (vuelve al inicio)
Buscar 25: 1
Actual: 20
Avanzar: 25
Después de eliminar: 20 -> 30 -> (vuelve al inicio)

===== PRUEBA DE LISTA DOBLE =====
Adelante: 10 20 25 30
Atrás: 30 25 20 10
Buscar 25: 2
Después de eliminar: 10 20 30

===== PRUEBA DE ÁRBOL =====
Buscar 40: true
Inorden: 20 30 40 50 60 70 80
Preorden: 50 30 20 40 70 60 80
Posorden: 20 40 30 60 80 70 50
Después de eliminar 30: 20 40 50 60 70 80

===== CASOS ADICIONALES DE PILA =====
¿Pila vacía?: true
Retirar de pila vacía: null

===== CASOS ADICIONALES DE COLA =====
¿Cola vacía?: true
Retirar de cola vacía: null

===== CASOS ADICIONALES DE ÁRBOL =====
Buscar 100: false
Después de eliminar 20: 40 50 60 70 80

===== FIN DE LAS PRUEBAS =====
```

# Anexo B. Verificación del reparto y del reciclaje

Programa auxiliar ejecutado para comprobar el estado inicial del tablero y el reciclaje del mazo:

```text
Columnas (tamaños): 1 2 3 4 5 6 7
Cartas en columnas: 28
Cartas en mazo: 24
Cartas en descarte: 0
Victoria inicial: false
Movimientos disponibles: true
Robar 1: [Q♣]
Mazo: 21 | Descarte: 3
Tras 8 robos -> Mazo: 0 | Descarte: 24
Robo con mazo vacío: null
Tras reciclar -> Mazo: 24 | Descarte: 0
```

La carta `[Q♣]` es la que salió en la ejecución de referencia; el valor exacto cambia en cada partida porque el mazo se mezcla con `Random`.

# Anexo C. Comandos rápidos

| Tarea | Comando |
|---|---|
| Compilar con `javac` | `javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")` |
| Ejecutar la interfaz | `java -cp out com.mycompany.solitario.Solitario` |
| Ejecutar las pruebas | `java -cp out com.mycompany.solitario.PruebaEstructuras` |
| Compilar con Maven | `mvn clean package` |
| Ejecutar el jar | `java -jar target/Solitario-1.0-SNAPSHOT.jar` |
| Ver la versión de Java | `java -version` |


---

*Documento generado para el proyecto Solitario Klondike. John Anibal Riascos — Noviembre de 2026.*
