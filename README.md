# Solitario Klondike

Proyecto final de **Estructuras de Datos**: una implementación del juego de cartas **Solitario Klondike** en Java con interfaz gráfica Swing. Las estructuras principales —pila, cola, lista circular, lista doble y árbol binario de búsqueda— están implementadas desde cero, sin usar las colecciones de Java para la lógica del juego.

## Información general

| Campo | Detalle |
|---|---|
| **Nombre del juego** | Solitario Klondike |
| **Integrantes** | John Anibal Riascos (1 integrante) |
| **Modalidad** | Proyecto individual |
| **Lenguaje** | Java 21 |
| **Interfaz** | Swing (aplicación de escritorio) |
| **Estructuras de datos** | Pila, cola, lista circular, lista doble y árbol binario de búsqueda |
| **Dependencias externas** | Ninguna |
| **Repositorio** | https://github.com/AnibalRiascos/solitario-klondike |

## Descripción

El Solitario Klondike se juega con una baraja francesa de 52 cartas. El objetivo es trasladar todas las cartas a cuatro fundaciones, una por palo, ordenadas desde el As hasta el Rey.

La aplicación permite jugar una partida completa, mover cartas individuales o grupos entre columnas, robar cartas del mazo, devolver cartas de las fundaciones y consultar las reglas en cualquier momento. Al finalizar una partida ganada, la puntuación se registra en un ranking en memoria que se mantiene ordenado.

## Documentación

| Documento | PDF | Versión editable |
|---|---|---|
| **Manual de Usuario** | [Manual_de_Usuario_Solitario_Klondike.pdf](docs/Manual_de_Usuario_Solitario_Klondike.pdf) | [Markdown](docs/Manual_de_Usuario_Solitario_Klondike.md) |
| **Manual Técnico** | [Manual_Tecnico_Solitario_Klondike.pdf](docs/Manual_Tecnico_Solitario_Klondike.pdf) | [Markdown](docs/Manual_Tecnico_Solitario_Klondike.md) |

El **Manual de Usuario** explica la instalación, la interfaz, los controles, las reglas, las capturas y las condiciones de victoria y derrota. El **Manual Técnico** documenta los requisitos, la compilación, la organización del código, el diagrama de clases, el uso y la complejidad de cada estructura, y las pruebas con resultados esperados y obtenidos.

Las imágenes y el diagrama de clases están en [`docs/assets/`](docs/assets/). Las instrucciones para regenerar los PDF están en [`docs/README.md`](docs/README.md).

## Requisitos

- **JDK 21 o superior** (probado con JDK 21.0.13).
- **NetBeans** (recomendado) o **Maven 3.9+** para compilar desde la terminal.
- Sistema operativo con soporte para Swing: Windows, macOS o Linux.
- No se necesitan librerías externas para ejecutar el juego. Maven puede requerir internet la primera vez para descargar sus plugins.

> En este equipo el JDK está en `/home/anibalitho/jdk-21.0.13+9`. Si `java` y `javac` no están en el `PATH`, usa esa ruta.

## Cómo ejecutar

### Opción 1: NetBeans

1. Abre NetBeans.
2. Selecciona **File → Open Project**.
3. Elige la carpeta `Solitario` (la que contiene `pom.xml`).
4. Pulsa **Run → Run Project** o la tecla `F6`.

NetBeans compila el proyecto con Maven y abre la ventana del juego.

### Opción 2: terminal con `javac`

Desde la carpeta del proyecto:

```bash
# Compilar todas las clases en la carpeta out/
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")

# Ejecutar la interfaz gráfica
java -cp out com.mycompany.solitario.Solitario
```

En Windows, la compilación con `find` no funciona igual. Usa NetBeans, Maven o compila indicando los archivos manualmente.

Si el JDK no está en el `PATH`:

```bash
JAVA=/home/anibalitho/jdk-21.0.13+9/bin
$JAVA/javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")
$JAVA/java -cp out com.mycompany.solitario.Solitario
```

> La interfaz gráfica necesita un entorno de escritorio. En una terminal sin pantalla (`headless`) el programa no puede abrir la ventana.

### Opción 3: Maven

```bash
mvn clean package
java -jar target/Solitario-1.0-SNAPSHOT.jar
```

El `pom.xml` está configurado para generar un jar ejecutable con la clase principal `com.mycompany.solitario.Solitario`.

## Controles

La interfaz se maneja con botones y cuadros de diálogo:

| Botón | Acción |
|---|---|
| **Nueva partida** | Pide el nombre del jugador e inicia una partida nueva. |
| **Robar cartas** | Roba hasta tres cartas del mazo y las pasa al descarte. |
| **Descarte a columna** | Mueve la carta superior del descarte a una columna (1–7). |
| **Mover entre columnas** | Mueve una carta o un grupo de una columna a otra. |
| **Columna a fundación** | Envía la carta superior de una columna a su fundación. |
| **Descarte a fundación** | Envía la carta superior del descarte a su fundación. |
| **Fundación a columna** | Devuelve la carta superior de una fundación a una columna. |
| **Ver reglas** | Muestra las reglas del juego. |
| **Salir** | Cierra la aplicación. |

## Reglas del juego

1. Se reparten **7 columnas**: la primera tiene 1 carta, la segunda 2, y así hasta la séptima con 7. Solo la última carta de cada columna queda descubierta.
2. Las columnas se ordenan de **mayor a menor** y con **colores alternados** (rojo/negro). Por ejemplo, un `7♥` puede colocarse sobre un `8♠`.
3. Una **columna vacía** solo acepta un **Rey**.
4. Las **fundaciones** empiezan con un **As** y suben en orden hasta el **Rey**, siempre con el mismo palo.
5. El mazo de robo entrega hasta **3 cartas** al descarte. Cuando se agota, el descarte se recicla.
6. Se pueden mover **grupos de cartas** entre columnas si forman una secuencia válida y la carta base respeta la regla de color y valor.
7. Se puede devolver la carta superior de una fundación a una columna si el movimiento es válido.

### Cómo se gana

La partida se gana cuando las **cuatro fundaciones** contienen las **13 cartas** de su palo, es decir, las 52 cartas están ordenadas.

### Cómo se pierde

La partida termina cuando **no quedan movimientos posibles** y **no quedan cartas por robar o reciclar**. También se puede cerrar la aplicación con el botón **Salir**.

## Puntuación

| Movimiento | Puntos |
|---|---|
| Descarte a columna | +5 |
| Grupo entre columnas | +5 |
| Carta a una fundación | +10 |
| Fundación a columna | −5 (mínimo 0) |

El ranking de mejores puntajes se mantiene ordenado de mayor a menor en memoria durante la sesión. Se registra al ganar una partida y se pierde al cerrar la aplicación; la interfaz gráfica no incluye una pantalla de ranking.

## Estructuras de datos utilizadas

| Estructura | Uso en el juego | Por qué se eligió |
|---|---|---|
| **Pila** | Fundaciones y pila de descarte. | Las operaciones del juego son LIFO: solo importa la carta superior. |
| **Cola** | Reparto inicial y lote temporal de hasta tres cartas robadas. | Las cartas se reparten y se procesan en el mismo orden en que llegan. |
| **Lista circular** | Mazo de robo. | Permite recorrer el mazo y reciclarlo volviendo al inicio sin crear otra estructura. |
| **Lista doble** | Las 7 columnas del tablero. | Permite insertar, eliminar y mover grupos, y recorrer la columna en ambos sentidos. |
| **Árbol binario de búsqueda** | Ranking de mejores puntajes. | Mantiene las puntuaciones ordenadas y permite mostrarlas con un recorrido inorden. |

Las operaciones mínimas solicitadas están implementadas:

- **Pila:** `apilar`, `desapilar`, `verTope`, `estaVacia`, `tamanio`.
- **Cola:** `encolar`, `desencolar`, `verFrente`, `estaVacia`, `tamanio`.
- **Lista circular:** insertar al inicio, al final y en posición; eliminar, buscar y recorrer.
- **Lista doble:** insertar al inicio, al final y en posición; eliminar, buscar y recorrer en ambos sentidos.
- **Árbol:** insertar, buscar, eliminar y recorridos inorden, preorden y posorden.

## Estructura del proyecto

```text
Solitario/
├── pom.xml
├── README.md
├── docs/
│   ├── Manual_de_Usuario_Solitario_Klondike.pdf
│   ├── Manual_de_Usuario_Solitario_Klondike.md
│   ├── Manual_Tecnico_Solitario_Klondike.pdf
│   ├── Manual_Tecnico_Solitario_Klondike.md
│   ├── generar_pdf.py
│   ├── manual_user_content.py
│   ├── manual_tech_content.py
│   ├── README.md
│   └── assets/
│       ├── 01_pantalla_principal.png
│       ├── 02_partida_en_curso.png
│       ├── 03_reglas.png
│       └── 04_diagrama_clases.png
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

## Pruebas

La clase `PruebaEstructuras` ejecuta operaciones de las cinco estructuras e imprime los resultados en consola. No usa JUnit ni aserciones; sirve como evidencia manual.

```bash
# Si compilaste con javac en out/
java -cp out com.mycompany.solitario.PruebaEstructuras

# Si generaste el jar con Maven
java -cp target/Solitario-1.0-SNAPSHOT.jar com.mycompany.solitario.PruebaEstructuras
```

La prueba incluye:

- Pila: apilar, consultar el tope, desapilar, tamaño y caso vacío.
- Cola: encolar, consultar el frente, desencolar, tamaño y caso vacío.
- Lista circular: insertar, buscar, recorrer, avanzar el cursor y eliminar.
- Lista doble: insertar, buscar, recorrer hacia adelante y hacia atrás, y eliminar.
- Árbol: insertar, buscar, recorrer en inorden, preorden y posorden, y eliminar un nodo con dos hijos y una hoja.

## Autor

**John Anibal Riascos**  
Proyecto final de Estructuras de Datos  
Noviembre de 2026
