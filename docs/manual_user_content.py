# -*- coding: utf-8 -*-
"""Contenido del Manual de Usuario."""

COVER = {
    "title": "MANUAL DE USUARIO",
    "subtitle": "Solitario Klondike",
    "tagline": "Desarrollado en Java con interfaz gráfica Swing",
    "note": "Documento elaborado para acompañar la entrega y sustentación del proyecto.",
    "label": "Manual de Usuario",
}

BLOCKS = [
    ("h1", "1. Introducción"),
    ("p", "Este manual explica cómo instalar, abrir y utilizar la aplicación **Solitario Klondike**. "
          "El programa permite jugar una partida individual mediante una interfaz gráfica construida con "
          "**Java Swing**. La interfaz muestra el estado del tablero y ofrece botones para ejecutar las "
          "acciones principales. Para algunas operaciones, el programa solicita datos mediante cuadros de diálogo."),
    ("p", "El documento está pensado para una persona que nunca ha usado el programa. Por eso incluye los "
          "requisitos, las formas de ejecución, la descripción de cada botón, las reglas, ejemplos de movimientos "
          "y la solución de los problemas más frecuentes. Los detalles internos del código se explican en el "
          "**Manual Técnico**."),

    ("h2", "1.1 ¿Qué es el Solitario Klondike?"),
    ("p", "El Solitario Klondike es un juego de cartas para una sola persona. Se juega con una baraja francesa "
          "de 52 cartas, organizada en cuatro palos: corazones (♥), diamantes (♦), tréboles (♣) y picas (♠). "
          "El objetivo es ordenar todas las cartas en cuatro fundaciones, una por palo, desde el As hasta el Rey."),
    ("p", "En esta implementación, el tablero se representa como texto dentro de una ventana. Las cartas tapadas "
          "se muestran como **[##]** y las cartas descubiertas con su valor y su símbolo, por ejemplo **[7♥]** o "
          "**[K♠]**. El programa no usa imágenes de cartas ni arrastrar y soltar: los movimientos se hacen con "
          "botones y con números de columna."),

    ("h2", "1.2 Objetivo del juego"),
    ("p", "El objetivo es trasladar las **52 cartas** a las cuatro fundaciones. Cada fundación debe contener las "
          "13 cartas de un mismo palo, en orden ascendente: As, 2, 3, ..., 10, J, Q y K. La partida se gana cuando "
          "las cuatro fundaciones están completas."),
    ("p", "Para conseguirlo, el jugador puede mover cartas entre columnas, robar cartas del mazo, enviar cartas a "
          "las fundaciones y devolver cartas de una fundación a una columna cuando el movimiento sea válido."),

    ("h2", "1.3 Alcance de este manual"),
    ("bullets", [
        "**Instalación y apertura:** requisitos, NetBeans, terminal y Maven.",
        "**Interfaz:** qué muestra la pantalla y para qué sirve cada botón.",
        "**Reglas:** reparto, columnas, fundaciones, mazo, descarte y movimientos válidos.",
        "**Juego paso a paso:** cómo iniciar una partida y realizar cada acción.",
        "**Victoria y derrota:** cuándo termina la partida y qué mensajes aparecen.",
        "**Solución de problemas:** errores comunes y cómo resolverlos.",
        "**Preguntas frecuentes, glosario y recomendaciones.**",
    ]),
    ("p", "No se explican aquí las clases, las estructuras de datos ni las pruebas internas. Esa información está "
          "en el **Manual Técnico** del proyecto."),

    ("h2", "1.4 Requisitos"),
    ("table",
     ["Requisito", "Mínimo", "Observaciones"],
     [
         ["Sistema operativo", "Windows, macOS o Linux", "Debe tener entorno de escritorio para abrir la ventana."],
         ["Java", "JDK 21 o superior", "Probado con JDK 21.0.13 (Temurin)."],
         ["Memoria", "256 MB disponibles", "El juego es liviano; no carga imágenes ni recursos externos."],
         ["Pantalla", "Resolución 950 × 700 o superior", "La ventana principal mide 950 × 700 píxeles."],
         ["Librerías externas", "Ninguna", "Swing y las estructuras propias están incluidas en el proyecto."],
         ["NetBeans", "Recomendado, no obligatorio", "También se puede compilar con `javac` o Maven."],
     ],
     [4.2, 4.8, 8.0]),
    ("note", "En el equipo de desarrollo el JDK está en `/home/anibalitho/jdk-21.0.13+9`. "
             "Si `java` o `javac` no están en el `PATH`, usa esa ruta o configura las variables de entorno."),

    ("h2", "1.5 Instalación y apertura"),
    ("h3", "Opción A: NetBeans (recomendada)"),
    ("steps", [
        "Abre NetBeans.",
        "Selecciona **File → Open Project**.",
        "Elige la carpeta `Solitario`, que es la que contiene el archivo `pom.xml`.",
        "Espera a que NetBeans termine de cargar el proyecto.",
        "Pulsa **Run → Run Project** o la tecla **F6**.",
    ]),
    ("p", "NetBeans compila el proyecto con Maven y abre automáticamente la ventana del juego."),

    ("h3", "Opción B: terminal con `javac`"),
    ("p", "Desde la carpeta del proyecto, compila todas las clases en una carpeta llamada `out` y ejecuta la clase principal:"),
    ("code", """
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out com.mycompany.solitario.Solitario
"""),
    ("p", "Si el JDK no está en el `PATH`, usa la ruta completa:"),
    ("code", """
JAVA=/home/anibalitho/jdk-21.0.13+9/bin
$JAVA/javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name "*.java")
$JAVA/java -cp out com.mycompany.solitario.Solitario
"""),
    ("note", "El comando `find` funciona en Linux y macOS. En Windows es más sencillo usar NetBeans, "
             "Maven o compilar los archivos indicando las rutas manualmente."),

    ("h3", "Opción C: Maven"),
    ("p", "Si tienes Maven instalado, puedes generar un jar ejecutable:"),
    ("code", """
mvn clean package
java -jar target/Solitario-1.0-SNAPSHOT.jar
"""),
    ("p", "El `pom.xml` ya está configurado con Java 21, la codificación UTF-8 y la clase principal "
          "`com.mycompany.solitario.Solitario`."),

    ("h3", "Cómo abrir el juego después de compilar"),
    ("bullets", [
        "**Desde NetBeans:** pulsa F6 o el botón de ejecución.",
        "**Desde la terminal con `javac`:** usa el comando de la Opción B de la sección 1.5.",
        "**Desde el jar de Maven:** ejecuta `java -jar target/Solitario-1.0-SNAPSHOT.jar`.",
        "**En Windows:** si el jar está asociado a Java, también puedes hacer doble clic sobre él.",
    ]),

    ("h2", "1.6 Solución de problemas de instalación"),
    ("table",
     ["Problema", "Causa probable", "Solución"],
     [
         ["`java: command not found`", "Java no está en el `PATH`.", "Usa la ruta completa del JDK o agrega su carpeta `bin` al `PATH`."],
         ["`javac: invalid target release: 21`", "El JDK instalado es anterior a 21.", "Instala JDK 21 o superior y vuelve a compilar."],
         ["`UnsupportedClassVersionError`", "Se ejecutaron clases compiladas con otra versión de Java.", "Borra `out` y `target/classes`, y recompila con JDK 21."],
         ["`Could not find or load main class`", "El classpath o el nombre de la clase no son correctos.", "Verifica que estés en la carpeta del proyecto y usa `-cp out`."],
         ["`HeadlessException`", "No hay entorno gráfico disponible.", "Abre el juego en una sesión de escritorio; no funciona por SSH sin pantalla."],
         ["`mvn: command not found`", "Maven no está instalado o no está en el `PATH`.", "Usa NetBeans o compila con `javac`; Maven es opcional."],
     ],
     [4.7, 5.2, 7.1]),
    ("note", "Si el proyecto se compiló antes con otra versión de Java, pueden quedar clases viejas en `target/classes`. "
             "En ese caso ejecuta `mvn clean` o borra la carpeta `out` antes de volver a compilar."),

    ("pagebreak",),

    ("h1", "2. Descripción de la interfaz"),
    ("p", "Al abrir el programa aparece una ventana titulada **Solitario Klondike**. La pantalla está dividida en "
          "tres zonas: el encabezado, el tablero y el panel de acciones. La figura siguiente muestra la pantalla "
          "inicial, antes de comenzar una partida."),
    ("image", "01_pantalla_principal.png", "Figura 1. Pantalla principal del juego antes de iniciar una partida.", 15.6),
    ("p", "En la pantalla inicial el tablero muestra un mensaje de bienvenida. Los botones están disponibles, pero "
          "las acciones de juego piden primero una partida nueva. La puntuación aparece en cero y el estado indica "
          "**Esperando partida**."),

    ("h2", "2.1 Elementos de la pantalla"),
    ("table",
     ["Elemento", "Ubicación", "Función"],
     [
         ["**SOLITARIO KLONDIKE**", "Parte superior", "Título de la aplicación."],
         ["**Tablero**", "Zona central", "Muestra el mazo, el descarte, las fundaciones y las siete columnas."],
         ["**Puntuación**", "Debajo del tablero", "Puntos acumulados en la partida actual."],
         ["**Estado**", "Debajo del tablero", "Mensaje de la última acción o del resultado de la partida."],
         ["**Botones de acción**", "Panel inferior", "Nueva partida, robar, mover, fundaciones y reglas."],
         ["**Ayuda de colores**", "Parte inferior", "Recuerda qué palos son rojos y cuáles son negros."],
         ["**Ayuda de movimientos**", "Parte inferior", "Ejemplo de orden descendente y colores alternados."],
         ["**Salir**", "Lado derecho inferior", "Cierra la aplicación."],
     ],
     [4.2, 4.0, 8.8]),

    ("h2", "2.2 Cómo leer el tablero"),
    ("p", "El tablero se actualiza después de cada acción. Un ejemplo de partida en curso es el siguiente:"),
    ("image", "02_partida_en_curso.png", "Figura 2. Ejemplo de partida en curso después de robar cartas.", 15.6),
    ("p", "La información del tablero se organiza así:"),
    ("bullets", [
        "**Mazo de robo:** cantidad de cartas que todavía se pueden robar.",
        "**Descarte:** carta superior del descarte o la palabra **Vacío**.",
        "**Fundaciones:** cuatro líneas numeradas del 1 al 4; cada una muestra su carta superior o **Vacía**.",
        "**Columnas:** siete líneas numeradas del 1 al 7; cada una muestra sus cartas de arriba hacia abajo.",
    ]),
    ("table",
     ["Representación", "Significado", "Ejemplo"],
     [
         ["`[##]`", "Carta tapada; no se puede mover ni consultar su valor.", "Una carta oculta en una columna."],
         ["`[A♥]`", "As de corazones descubierto.", "Puede iniciar la fundación de corazones."],
         ["`[10♦]`", "Diez de diamantes descubierto.", "Puede colocarse sobre un J negro."],
         ["`[J♣]`", "Jota de tréboles descubierta.", "Puede colocarse sobre una Q roja."],
         ["`[Q♠]`", "Reina de picas descubierta.", "Puede colocarse sobre un K rojo."],
         ["`[K♥]`", "Rey de corazones descubierto.", "Puede iniciar una columna vacía."],
     ],
     [3.2, 7.0, 6.8]),
    ("p", "Los símbolos de los palos son: **♥ corazones**, **♦ diamantes**, **♣ tréboles** y **♠ picas**. "
          "Corazones y diamantes son rojos; tréboles y picas son negros."),

    ("h2", "2.3 Botones de la interfaz"),
    ("table",
     ["Botón", "Acción", "Cuándo usarlo"],
     [
         ["**Nueva partida**", "Pide el nombre del jugador y reparte una partida nueva.", "Al comenzar o después de terminar una partida."],
         ["**Robar cartas**", "Roba hasta tres cartas del mazo y las pasa al descarte.", "Cuando necesites cartas nuevas."],
         ["**Descarte a columna**", "Mueve la carta superior del descarte a una columna (1–7).", "Si la carta del descarte encaja en una columna."],
         ["**Mover entre columnas**", "Mueve una carta o un grupo de una columna a otra.", "Para ordenar columnas o liberar cartas."],
         ["**Columna a fundación**", "Envía la carta superior de una columna a su fundación.", "Cuando la carta sea la siguiente de su palo."],
         ["**Descarte a fundación**", "Envía la carta superior del descarte a su fundación.", "Cuando la carta del descarte sea la siguiente de su palo."],
         ["**Fundación a columna**", "Devuelve la carta superior de una fundación a una columna.", "Si necesitas reutilizar una carta ya subida."],
         ["**Ver reglas**", "Muestra un cuadro con las reglas resumidas.", "En cualquier momento."],
         ["**Salir**", "Cierra la aplicación.", "Al terminar de jugar."],
     ],
     [4.2, 6.8, 6.0]),

    ("h2", "2.4 Cuadros de diálogo"),
    ("p", "Las acciones que necesitan datos abren un cuadro de diálogo. Los datos se escriben con el teclado:"),
    ("bullets", [
        "**Nombre del jugador:** texto libre. Si se deja vacío, se usa **Jugador**.",
        "**Columna:** número entre **1 y 7**. La columna 1 es la primera de la izquierda.",
        "**Fundación:** número entre **1 y 4**. La fundación 1 es la primera de la izquierda.",
        "**Posición inicial del grupo:** número entre **1 y el tamaño de la columna**. "
        "La posición **1** es la carta superior de la columna; los números aumentan hacia abajo.",
    ]),
    ("note", "Si cancelas un cuadro de diálogo, la acción se cancela y no se modifica el tablero. "
             "Si escribes un número fuera de rango, el programa muestra un mensaje y no realiza el movimiento."),

    ("h2", "2.5 Ejemplo de una partida en curso"),
    ("p", "En la figura 2 se observa una partida después de robar dos veces. El mazo tiene menos cartas, el descarte "
          "muestra la carta superior y las columnas conservan su orden. La puntuación sigue en cero porque todavía "
          "no se ha realizado un movimiento que otorgue puntos."),
    ("p", "Cada vez que se realiza una acción, el mensaje de estado cambia. Por ejemplo: **Movimiento realizado**, "
          "**Movimiento no válido**, **Carta enviada a la fundación** o **Carta robada: [Q♣]**."),

    ("pagebreak",),

    ("h1", "3. Reglas del juego"),
    ("p", "El Solitario Klondike se juega con una baraja francesa de 52 cartas. Antes de jugar es importante "
          "entender el reparto inicial, el papel de las columnas y las fundaciones, y las reglas de movimiento."),

    ("h2", "3.1 Reparto inicial"),
    ("p", "Al iniciar una partida, el programa mezcla las 52 cartas y las reparte en siete columnas:"),
    ("bullets", [
        "La columna 1 recibe **1 carta**.",
        "La columna 2 recibe **2 cartas**.",
        "La columna 3 recibe **3 cartas**.",
        "La columna 4 recibe **4 cartas**.",
        "La columna 5 recibe **5 cartas**.",
        "La columna 6 recibe **6 cartas**.",
        "La columna 7 recibe **7 cartas**.",
    ]),
    ("p", "En total se reparten **28 cartas**. En cada columna, solo la **última carta** queda descubierta; las demás "
          "quedan tapadas. Las **24 cartas restantes** forman el mazo de robo. Al comenzar, el descarte y las cuatro "
          "fundaciones están vacíos."),
    ("table",
     ["Zona", "Cantidad inicial", "Estado inicial"],
     [
         ["Columnas", "28 cartas (1+2+3+4+5+6+7)", "Solo la última carta de cada columna está descubierta."],
         ["Mazo de robo", "24 cartas", "Preparado para robar de tres en tres."],
         ["Descarte", "0 cartas", "Vacío."],
         ["Fundaciones", "0 cartas", "Las cuatro están vacías."],
     ],
     [4.5, 5.5, 7.0]),

    ("h2", "3.2 Reglas de las columnas"),
    ("p", "Las columnas se ordenan de **mayor a menor** y con **colores alternados**. La carta que se coloca debe "
          "tener un valor inmediatamente menor que la carta sobre la que se apoya y un color distinto."),
    ("bullets", [
        "Un **7♥** puede colocarse sobre un **8♠** porque 7 es menor que 8 y los colores son distintos.",
        "Un **7♥** no puede colocarse sobre un **8♥** porque ambos son rojos.",
        "Un **10♦** puede colocarse sobre un **J♣**.",
        "Una **columna vacía** solo acepta un **Rey** de cualquier palo.",
        "Se puede mover una **carta individual** o un **grupo** que ya forme una secuencia válida.",
    ]),

    ("h2", "3.3 Reglas de las fundaciones"),
    ("p", "Las fundaciones son cuatro pilas, una por palo. Cada fundación se construye desde el **As** hasta el **Rey**, "
          "siempre con el mismo palo:"),
    ("bullets", [
        "La primera carta de una fundación debe ser un **As**.",
        "La siguiente carta debe ser del **mismo palo** y tener **un valor más**.",
        "Por ejemplo: **A♦ → 2♦ → 3♦ → ... → K♦**.",
        "No se puede colocar un **2♥** sobre un **A♦** porque el palo es distinto.",
        "La fundación se completa cuando llega al **Rey**.",
    ]),

    ("h2", "3.4 Mazo y descarte"),
    ("p", "El botón **Robar cartas** toma hasta **tres cartas** del mazo y las coloca en el descarte. La carta superior "
          "del descarte es la única que se puede usar para mover a una columna o a una fundación."),
    ("p", "Cuando el mazo se queda sin cartas, la siguiente acción de robo recicla el descarte: las cartas vuelven al "
          "mazo en orden y se reinicia el recorrido. En esta implementación el reciclaje ocurre automáticamente; no "
          "hay un botón separado para reciclar."),
    ("note", "Si el mazo y el descarte están vacíos y no existen movimientos posibles, la partida termina. "
             "El mensaje de estado y el cuadro final indican el resultado."),

    ("h2", "3.5 Movimientos permitidos"),
    ("table",
     ["Movimiento", "Origen", "Destino", "Condición"],
     [
         ["Descarte a columna", "Carta superior del descarte", "Cualquier columna", "Orden descendente y color alternado; Rey si la columna está vacía."],
         ["Columna a columna", "Carta o grupo de una columna", "Otra columna", "El grupo debe ser una secuencia válida y la carta base debe encajar."],
         ["Columna a fundación", "Carta superior de una columna", "Fundación de su palo", "As si está vacía; mismo palo y valor siguiente si no."],
         ["Descarte a fundación", "Carta superior del descarte", "Fundación de su palo", "As si está vacía; mismo palo y valor siguiente si no."],
         ["Fundación a columna", "Carta superior de una fundación", "Cualquier columna", "Debe cumplir la regla de columna."],
     ],
     [4.0, 4.2, 4.0, 5.4]),

    ("h2", "3.6 Ejemplos de movimientos"),
    ("table",
     ["Movimiento", "¿Válido?", "Explicación"],
     [
         ["`7♥` sobre `8♠`", "Sí", "7 es menor que 8 y los colores son distintos."],
         ["`7♥` sobre `8♥`", "No", "Los dos son rojos; no se alternan los colores."],
         ["`K♣` en columna vacía", "Sí", "Una columna vacía solo acepta un Rey."],
         ["`Q♦` en columna vacía", "No", "La columna vacía no acepta una Reina."],
         ["`A♦` en fundación vacía", "Sí", "Toda fundación comienza con un As."],
         ["`2♦` sobre `A♦`", "Sí", "Mismo palo y valor siguiente."],
         ["`2♥` sobre `A♦`", "No", "El palo es distinto."],
         ["`5♠` sobre `4♠`", "No", "Falta alternancia de color y el orden de columna es descendente."],
     ],
     [4.8, 3.0, 8.4]),

    ("h2", "3.7 Reglas en pantalla"),
    ("p", "El botón **Ver reglas** abre un cuadro con un resumen de las reglas. La figura siguiente muestra ese cuadro."),
    ("image", "03_reglas.png", "Figura 3. Cuadro de reglas que se muestra desde el botón «Ver reglas».", 12.8),

    ("h1", "4. Cómo jugar paso a paso"),
    ("p", "Esta sección describe una partida completa desde el inicio. Los números de columna y de fundación siempre "
          "empiezan en 1 y se cuentan de izquierda a derecha."),

    ("h2", "4.1 Iniciar una partida"),
    ("steps", [
        "Pulsa **Nueva partida**.",
        "Escribe el nombre del jugador y pulsa **OK**. Si dejas el campo vacío, se usará **Jugador**.",
        "El programa mezcla las cartas, reparte las siete columnas y deja 24 cartas en el mazo.",
        "La puntuación se reinicia en **0** y el estado muestra **Nueva partida iniciada. ¡Buena suerte!**.",
    ]),
    ("note", "Si todavía no has iniciado una partida, cualquier acción de juego muestra el mensaje "
             "**Primero debes comenzar una partida**."),

    ("h2", "4.2 Robar cartas"),
    ("steps", [
        "Pulsa **Robar cartas**.",
        "El programa toma hasta tres cartas del mazo y las coloca en el descarte.",
        "La carta superior del descarte queda disponible para moverla.",
        "El estado muestra la carta robada, por ejemplo **Carta robada: [Q♣]**.",
    ]),
    ("p", "Si el mazo se agota, la siguiente acción de robo recicla el descarte. El mensaje puede indicar que el mazo "
          "se recicló y que puedes volver a robar."),

    ("h2", "4.3 Mover del descarte a una columna"),
    ("steps", [
        "Pulsa **Descarte a columna**.",
        "Escribe el número de la columna de destino (1 a 7).",
        "Si la carta cumple la regla de orden y color, se mueve y el estado muestra **Movimiento realizado**.",
        "Si no cumple, el estado muestra **Movimiento no válido** y el tablero no cambia.",
    ]),

    ("h2", "4.4 Mover una carta o un grupo entre columnas"),
    ("steps", [
        "Pulsa **Mover entre columnas**.",
        "Escribe la columna de origen (1 a 7).",
        "Escribe la posición inicial del grupo. La posición **1** es la carta superior de la columna; "
        "los números aumentan hacia abajo.",
        "Escribe la columna de destino (1 a 7).",
        "Si el grupo es una secuencia válida y la carta base encaja en el destino, el grupo se mueve completo.",
    ]),
    ("p", "Por ejemplo, si la columna 3 tiene `[9♠] [8♥] [7♣]` y quieres mover el `8♥` y el `7♣` a otra columna, "
          "la posición inicial es **2**. El grupo se mueve unido; no se mueven cartas sueltas del medio."),
    ("note", "La posición se cuenta desde la carta superior, no desde la base. Si escribes una posición que apunta "
             "a una carta tapada o a un grupo que no forma una secuencia válida, el movimiento se rechaza."),

    ("h2", "4.5 Enviar una carta a una fundación"),
    ("steps", [
        "Para una carta de una columna, pulsa **Columna a fundación** e indica la columna de origen.",
        "Para la carta del descarte, pulsa **Descarte a fundación**.",
        "El programa busca la fundación del palo de la carta y comprueba si es el As o la siguiente carta de ese palo.",
        "Si el movimiento es válido, la carta desaparece de su origen y aparece en la fundación.",
    ]),

    ("h2", "4.6 Devolver una carta de una fundación a una columna"),
    ("steps", [
        "Pulsa **Fundación a columna**.",
        "Escribe el número de la fundación (1 a 4).",
        "Escribe la columna de destino (1 a 7).",
        "El movimiento solo se realiza si la carta cumple la regla de columna.",
    ]),
    ("p", "Este movimiento resta **5 puntos**, con un mínimo de 0. Úsalo solo cuando realmente necesites la carta "
          "para destapar una columna o continuar una secuencia."),

    ("h2", "4.7 Ver las reglas y salir"),
    ("bullets", [
        "**Ver reglas:** abre el cuadro con el resumen de las reglas. Se puede consultar en cualquier momento.",
        "**Salir:** cierra la aplicación inmediatamente. La puntuación y el ranking en memoria se pierden.",
    ]),

    ("h2", "4.8 Puntuación"),
    ("table",
     ["Acción", "Puntos", "Observación"],
     [
         ["Mover el descarte a una columna", "+5", "Solo si el movimiento es válido."],
         ["Mover una carta o grupo entre columnas", "+5", "Solo si el movimiento es válido."],
         ["Enviar una carta a una fundación", "+10", "Desde una columna o desde el descarte."],
         ["Devolver una carta de una fundación a una columna", "−5", "La puntuación nunca baja de 0."],
     ],
     [8.0, 3.0, 6.0]),
    ("p", "La puntuación se muestra en la parte inferior de la ventana. El programa también mantiene un ranking de "
          "mejores puntajes en memoria, ordenado de mayor a menor; ese ranking se conserva mientras la aplicación "
          "sigue abierta."),

    ("h2", "4.9 Consejos para jugar mejor"),
    ("bullets", [
        "**Descubre cartas tapadas:** es la prioridad al principio de la partida.",
        "**Busca los Ases:** son la base de las fundaciones y conviene subirlos cuanto antes.",
        "**No muevas cartas sin necesidad:** cada movimiento puede cerrar una opción futura.",
        "**Usa las columnas vacías para Reyes:** liberan espacio y permiten ordenar grupos largos.",
        "**Revisa el descarte:** a veces la carta que necesitas ya está disponible.",
        "**Recicla el mazo:** si no encuentras una jugada, roba hasta agotar el mazo y vuelve a empezar.",
        "**Consulta las reglas:** el botón **Ver reglas** está siempre disponible.",
    ]),

    ("pagebreak",),

    ("h1", "5. Cómo se gana y cómo se pierde"),
    ("h2", "5.1 Cómo se gana"),
    ("p", "La partida se gana cuando las **cuatro fundaciones** contienen las **13 cartas** de su palo. En ese momento "
          "las 52 cartas están ordenadas y el programa muestra el mensaje **¡Felicidades! Has ganado la partida.**"),
    ("p", "Al ganar, la puntuación final se registra en el ranking en memoria. El jugador puede iniciar otra partida "
          "con el botón **Nueva partida**."),
    ("note", "Para ganar no basta con tener muchas cartas en las fundaciones: deben estar las cuatro completas, "
             "desde el As hasta el Rey."),

    ("h2", "5.2 Cómo se pierde"),
    ("p", "La partida termina cuando **no quedan movimientos posibles** y **no quedan cartas en el mazo ni en el "
          "descarte**. El programa muestra un mensaje indicando que no quedan movimientos y que la partida ha "
          "terminado."),
    ("p", "También puedes cerrar la aplicación con el botón **Salir**; en ese caso la partida no se registra como "
          "ganada ni como perdida."),

    ("h2", "5.3 Qué ocurre al terminar"),
    ("bullets", [
        "El estado muestra el resultado de la partida.",
        "Aparece un cuadro con el resultado y la puntuación final.",
        "Las acciones de juego se bloquean hasta iniciar una partida nueva.",
        "La puntuación de una partida ganada se agrega al ranking en memoria.",
    ]),

    ("h2", "5.4 Resumen de condiciones"),
    ("table",
     ["Situación", "Resultado", "Mensaje o efecto"],
     [
         ["Cuatro fundaciones con 13 cartas", "Victoria", "¡Felicidades! Has ganado la partida."],
         ["Sin movimientos y sin cartas por robar", "Derrota", "No quedan movimientos posibles. La partida ha terminado."],
         ["El jugador pulsa Salir", "Cierre", "La aplicación se cierra; no se registra resultado."],
         ["El jugador inicia otra partida", "Reinicio", "Se reparte un tablero nuevo y la puntuación vuelve a 0."],
     ],
     [6.0, 3.5, 7.5]),

    ("pagebreak",),

    ("h1", "6. Preguntas frecuentes"),
    ("h2", "6.1 ¿Por qué no puedo mover una carta?"),
    ("p", "Revisa estas causas, en este orden:"),
    ("bullets", [
        "La carta está tapada (`[##]`); primero debes descubrirla.",
        "El color no se alterna: una carta roja solo puede ir sobre una negra, y viceversa.",
        "El valor no es el inmediatamente menor: por ejemplo, un 7 solo va sobre un 8.",
        "La columna de destino está vacía y la carta no es un Rey.",
        "Estás intentando mover un grupo que no forma una secuencia válida.",
        "La carta no es la siguiente de la fundación o el palo no coincide.",
    ]),

    ("h2", "6.2 ¿Por qué el programa pide la posición «desde arriba»?"),
    ("p", "Porque las columnas se muestran de arriba hacia abajo. La posición **1** es la carta superior de la "
          "columna; la posición **2** es la siguiente, y así sucesivamente. Si quieres mover solo la carta superior, "
          "escribe **1**."),

    ("h2", "6.3 ¿Qué significa `[##]`?"),
    ("p", "Es una carta tapada. El programa no muestra su valor ni su palo, y no se puede mover hasta que quede "
          "descubierta. Cuando retiras las cartas que están encima, la siguiente carta se descubre automáticamente."),

    ("h2", "6.4 ¿Qué pasa cuando el mazo llega a cero?"),
    ("p", "En la siguiente acción de robo, el descarte se recicla y vuelve al mazo. El programa reinicia el recorrido "
          "circular para que puedas seguir robando. No hay un botón separado para reciclar."),

    ("h2", "6.5 ¿Puedo deshacer un movimiento?"),
    ("p", "No. Esta versión no incluye la función de deshacer. Si cometes un error, puedes intentar corregirlo con "
          "otro movimiento o iniciar una partida nueva."),

    ("h2", "6.6 ¿Puedo arrastrar las cartas con el ratón?"),
    ("p", "No. La interfaz usa botones y cuadros de diálogo. Las cartas se mueven indicando números de columna, "
          "posición y fundación."),

    ("h2", "6.7 ¿La puntuación se guarda al cerrar el programa?"),
    ("p", "No. La puntuación y el ranking se mantienen en memoria durante la sesión. Al cerrar la aplicación se "
          "pierden. Esta es una limitación conocida de la versión académica."),

    ("h2", "6.8 ¿Puedo jugar sin entorno gráfico?"),
    ("p", "La interfaz principal es Swing y necesita un entorno de escritorio. Si ejecutas el programa por SSH o en "
          "una terminal sin pantalla, puede aparecer `HeadlessException`. Para probar las estructuras sin interfaz "
          "gráfica puedes ejecutar la clase `PruebaEstructuras` (ver el Manual Técnico)."),

    ("h2", "6.9 ¿Cómo reporto un error?"),
    ("p", "Abre un issue en el repositorio del proyecto: "
          "**https://github.com/AnibalRiascos/solitario-klondike/issues**. Incluye los pasos para reproducir el "
          "problema, el mensaje de estado y, si es posible, una captura de pantalla."),

    ("h1", "7. Limitaciones y recomendaciones"),
    ("h2", "7.1 Limitaciones de esta versión"),
    ("bullets", [
        "No hay arrastrar y soltar: los movimientos se hacen con botones y números.",
        "No hay función de deshacer ni de rehacer.",
        "La puntuación y el ranking no se guardan en disco; solo viven en memoria.",
        "No hay sugerencias automáticas, temporizador ni finalización automática.",
        "El tablero es textual; no se muestran imágenes de cartas ni animaciones.",
        "La aplicación está pensada para escritorio, no para navegador ni dispositivos móviles.",
    ]),

    ("h2", "7.2 Recomendaciones de uso"),
    ("bullets", [
        "Usa NetBeans si no estás familiarizado con la terminal; evita problemas de `PATH` y de classpath.",
        "Inicia siempre una partida nueva antes de probar los botones de juego.",
        "Lee el mensaje de estado después de cada acción: indica si el movimiento fue válido.",
        "Consulta el botón **Ver reglas** si dudas de una jugada.",
        "Si una acción no funciona, revisa primero el color, el valor y el estado de la carta.",
        "No cierres la aplicación si quieres conservar la puntuación de la sesión.",
    ]),

    ("h2", "7.3 Mejoras futuras"),
    ("p", "Algunas mejoras posibles para próximas versiones son: agregar arrastrar y soltar, guardar el ranking en "
          "un archivo, incluir deshacer/rehacer, mostrar sugerencias de movimientos y agregar animaciones. Estas "
          "mejoras no forman parte del alcance actual del proyecto."),

    ("h1", "8. Glosario"),
    ("table",
     ["Término", "Definición"],
     [
         ["**Baraja francesa**", "Conjunto de 52 cartas dividido en cuatro palos de 13 cartas cada uno."],
         ["**Palo**", "Familia de la carta: corazones, diamantes, tréboles o picas."],
         ["**Carta tapada**", "Carta cuyo valor y palo no se muestran; se representa como `[##]`."],
         ["**Carta descubierta**", "Carta visible y disponible para jugar."],
         ["**Columna**", "Una de las siete pilas verticales del tablero."],
         ["**Fundación**", "Una de las cuatro pilas donde se ordenan las cartas por palo, del As al Rey."],
         ["**Mazo de robo**", "Pila de cartas de donde se roban hasta tres cartas por turno."],
         ["**Descarte**", "Pila donde se colocan las cartas robadas; solo se usa la carta superior."],
         ["**Secuencia válida**", "Grupo de cartas ordenadas de mayor a menor y con colores alternados."],
         ["**Reciclar**", "Devolver las cartas del descarte al mazo cuando este se agota."],
     ],
     [4.0, 12.0]),

    ("h1", "9. Información del proyecto"),
    ("table",
     ["Campo", "Detalle"],
     [
         ["**Nombre del juego**", "Solitario Klondike"],
         ["**Integrante**", "John Anibal Riascos"],
         ["**Modalidad**", "Proyecto individual"],
         ["**Asignatura**", "Estructuras de Datos"],
         ["**Lenguaje**", "Java 21"],
         ["**Interfaz**", "Swing (aplicación de escritorio)"],
         ["**Estructuras**", "Pila, cola, lista circular, lista doble y árbol binario de búsqueda"],
         ["**Repositorio**", "https://github.com/AnibalRiascos/solitario-klondike"],
         ["**Documentación técnica**", "Manual_Tecnico_Solitario_Klondike.pdf"],
     ],
     [4.5, 12.0]),
    ("p", "Este manual se complementa con el **Manual Técnico**, que describe la arquitectura, las clases, las "
          "estructuras de datos, las pruebas y las decisiones de implementación."),
]
