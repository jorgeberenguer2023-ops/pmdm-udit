# Reto 1 · Tarjeta de Presentación Profesional (Linktree App)

**Módulo:** 0489 · Programación Multimedia y Dispositivos Móviles  
**Alumno:** Jorge Berenguer Martín  
**Tecnología:** Kotlin + Jetpack Compose (Android nativo)  
**Repositorio GitHub:** [github.com/jorgeberenguer2023-ops/Tarjeta-presentacion](https://github.com/jorgeberenguer2023-ops/Tarjeta-presentacion)

## 📱 Qué es esta app

Una tarjeta de presentación digital interactiva desarrollada en Jetpack Compose. Además de mostrar la información profesional del autor (foto de perfil, nombre y rol), cada sección cuenta con **botones interactivos inteligentes de doble toque**:
1. **Primer toque:** Despliega un código QR generado dinámicamente en pantalla para que otros usuarios puedan escanearlo.
2. **Segundo toque (con el QR visible):** Ejecuta la acción correspondiente (abrir el perfil en el navegador, descargar el CV en el dispositivo o acceder al repositorio de proyectos).

## 🛠️ Componentes y funcionalidades principales

| Componente / Característica | Para qué se usa en esta app |
|---|---|
| `Column`, `Spacer` | Organizan los elementos y márgenes de la interfaz en vertical |
| `Image` + `clip(CircleShape)` | Muestra la foto de perfil recortada en círculo (`fotomia`) |
| `Text` | Nombre completo ("Jorge Berguer Martín") y rol ("Desarrollador de DAM") |
| `Button` + `Intent` | Botones interactivos para GitHub, LinkedIn y Mis Proyectos |
| Descarga de CV (`MediaStore`) | Guarda el archivo PDF del CV directamente en la carpeta *Descargas* del dispositivo (compatible con Scoped Storage) |
| Códigos QR Dinámicos (ZXing) | Genera mapas de bits de códigos QR en tiempo real para cada sección |
| Lógica de Doble Toque | Permite alternar entre mostrar el código QR o ejecutar la acción de navegación/descarga |

## 📂 Estructura del proyecto

```
app/src/main/java/com/example/myapplication/MainActivity.kt → pantalla principal (Compose, QRs y botones)
app/src/main/res/drawable/fotomia.png                   → imagen de perfil de usuario
app/src/main/res/raw/cvingles.pdf                       → archivo PDF del CV integrado
app/src/main/res/mipmap-*/                              → iconos adaptativos de la app
app/src/main/res/values/strings.xml                     → nombre visible de la app
```

## 🚀 Cómo ejecutar el proyecto

1. Clonar o abrir el repositorio en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar (▶) sobre un emulador o dispositivo Android real.

## 🔗 Enlaces de interés

- GitHub del autor: [github.com/jorgeberenguer2023-ops](https://github.com/jorgeberenguer2023-ops)
- Repositorio del proyecto: [Tarjeta-presentacion](https://github.com/jorgeberenguer2023-ops/Tarjeta-presentacion)
