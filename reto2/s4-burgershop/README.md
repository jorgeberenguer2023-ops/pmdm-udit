# Reto 2 · E-Commerce Adaptativo (Responsive UI) - Camisetas Shop

**Módulo:** 0489 · Programación Multimedia y Dispositivos Móviles
**Tecnología:** Kotlin + Jetpack Compose (Android nativo)
**RA vinculado:** RA1 y RA2 · Interfaces adaptables y limitaciones del dispositivo

## 📱 Producto Final (Capturas)
*(Capturas de pantalla de la aplicación funcionando: pantalla de carga/splash, catálogo en móvil vertical y adaptable en tablet/horizontal).*

```
[Pantalla de Carga (Splash Screen)]  -->  [Catálogo Responsive (Grid Adaptativo) + Carrito 🛒]
```

## 🎯 Objetivo del reto
Comprender la fragmentación de pantallas en Android y resolver los problemas de visualización en distintos perfiles de dispositivos. Pasar de un diseño estático (una lista lineal que se deforma) a una interfaz *responsive* (una cuadrícula inteligente con `LazyVerticalGrid`) utilizando componentes de Jetpack Compose que calculan el espacio disponible en tiempo real.

## 🛠️ Componentes y conceptos utilizados

| Componente / Concepto | Para qué se usa en esta app |
| :--- | :--- |
| **LazyVerticalGrid** | Sustituye a `LazyColumn` para crear una cuadrícula adaptativa de productos. |
| **GridCells.Adaptive** | Calcula automáticamente cuántas columnas caben en la pantalla según el ancho mínimo por tarjeta (`minSize = 150.dp`). |
| **Card & Column** | Crean contenedores elevados con sombra y esquinas redondeadas para agrupar imagen, textos y botón. |
| **ContentScale.Crop** | Asegura que la imagen llene el espacio asignado recortando bordes para evitar deformaciones al rotar el dispositivo. |
| **maxLines & TextOverflow** | Fuerza a que los nombres largos ocupen una sola línea con puntos suspensivos (`...`) para mantener la uniformidad visual. |
| **LaunchedEffect & mutableStateOf** | Gestionan la pantalla de carga inicial (*Splash Screen*) con un temporizador de 1.5 segundos. |
| **Scaffold & TopAppBar & BadgedBox** | Implementan la barra superior de la tienda con un icono de carrito interactivo y contador de artículos. |
| **AlertDialog & LazyColumn** | Despliegan el modal del carrito de compras con el desglose de productos y cálculo del precio total. |

## 🧐 Reflexión y Pensamiento Crítico

1. **Sobre la adaptabilidad:** ¿Por qué hemos usado `GridCells.Adaptive(minSize = ...)` en lugar de fijar el número de columnas con `GridCells.Fixed(2)`? ¿Qué ventaja tiene en el mundo real?
   > `GridCells.Adaptive` calcula de forma dinámica y automática cuántas columnas caben en la pantalla basándose en el ancho disponible y el tamaño mínimo (`minSize`) que definamos para cada tarjeta. En el mundo real, esto permite que la misma interfaz se adapte perfectamente tanto a un teléfono móvil estrecho (mostrando 1 o 2 columnas) como a una tablet grande o un dispositivo plegable abierto (mostrando 3, 4 o más columnas), sin necesidad de crear layouts separados o hardcodear lógica de rotación. Con `GridCells.Fixed(2)`, las tarjetas se verían excesivamente estiradas en tablets o comprimidas en pantallas muy pequeñas.

2. **Sobre el diseño UI:** ¿Qué ocurría con las tarjetas si el nombre de un producto era muy largo y ocupaba dos líneas mientras los demás ocupaban una? ¿Cómo lo hemos solucionado?
   > Si el nombre de un producto ocupaba dos líneas mientras otros ocupaban una, las tarjetas sufrían desalineaciones verticales, rompiendo la armonía visual de la cuadrícula. Lo solucionamos aplicando `maxLines = 1` y `overflow = TextOverflow.Ellipsis` en el componente `Text` del título, asegurando que cualquier texto largo sea truncado con puntos suspensivos (`...`) y manteniendo todas las tarjetas con exactamente la misma altura y alineación.

3. **Sobre las imágenes:** ¿Por qué es importante usar `ContentScale.Crop` en la imagen del producto cuando cambiamos el ancho de la tarjeta al rotar la pantalla?
   > Al rotar la pantalla o cambiar de dispositivo, el ancho de las tarjetas de la cuadrícula varía. Si usáramos un escalado por defecto que mantenga la relación de aspecto original sin recortar (`Fit`), las imágenes dejarían espacios en blanco irregulares o deformarían sus proporciones. `ContentScale.Crop` escala la imagen para llenar por completo el ancho y alto asignados recortando los excedentes por los bordes, garantizando un aspecto visual limpio, profesional y uniforme.

4. **Visión de futuro:** Ahora mismo nuestros productos están escritos directamente en el código (*hardcodeados*). ¿Qué crees que necesitaría nuestra app en el futuro para que esta tienda mostrara productos reales de internet?
   > Para mostrar productos reales de internet, la aplicación necesitaría:
   > 1. Un cliente de red (como **Retrofit** o **Ktor**) junto con una librería de serialización (como **Kotlinx Serialization** o **Gson**) para consumir una API REST.
   > 2. Gestión de estados asíncronos (`Loading`, `Success`, `Error`) utilizando **Corrutinas** y **Flows** (`ViewModel`).
   > 3. Una librería de carga de imágenes en red (como **Coil** o **Glide**) para descargar y cachear las imágenes de los productos desde URLs remotas.
   > 4. Conexión con un backend o base de datos en la nube (como Firebase) para la gestión real del carrito de compras y pasarela de pago.

## 🚀 Cómo ejecutar el proyecto
1. Clonar el repositorio y abrir en Android Studio.
2. Ejecutar (▶) en un emulador o dispositivo físico (recomendado probar en móvil vertical y girar a horizontal o tablet).
3. Disfrutar de la experiencia de compra en **Camisetas Shop** con pantalla de carga, cuadrícula responsive y carrito de compras interactivo.
