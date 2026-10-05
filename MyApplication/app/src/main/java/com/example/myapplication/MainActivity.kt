package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

/**
 * Actividad principal de la aplicación.
 * Configura el contenedor principal y carga la interfaz de usuario en Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicación del tema Material 3
            MaterialTheme {
                // Superficie de fondo que ocupa toda la pantalla con el color del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamada al componente principal que dibuja la tarjeta de presentación
                    TarjetaPresentacion()
                }
            }
        }
    }
}

/**
 * Componente Composable principal que define la tarjeta de presentación personal.
 * Incluye la foto de perfil, datos personales, y botones interactivos con códigos QR
 * que implementan el comportamiento de doble clic (primer clic despliega el QR, segundo clic ejecuta la acción).
 */
@Composable
fun TarjetaPresentacion() {
    // Contexto local para iniciar Intents de Android, mostrar Toasts y acceder a recursos
    val context = LocalContext.current

    // Estados para controlar qué código QR está visible actualmente (se despliega en el primer clic)
    var showGithubQr by remember { mutableStateOf(false) }
    var showLinkedinQr by remember { mutableStateOf(false) }
    var showCvQr by remember { mutableStateOf(false) }
    var showProyectosQr by remember { mutableStateOf(false) }

    // Contenedor vertical principal (Columna) que centra todos los elementos en la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Imagen de perfil circular (cargada desde res/drawable/fotomia.png)
        Image(
            painter = painterResource(id = R.drawable.fotomia),
            contentDescription = "Foto de perfil de usuario",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Nombre del usuario
        Text(
            text = "Jorge Berguer Martín",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        // 3. Rol o profesión
        Text(
            text = "Desarrollador de DAM",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))


        // BOTÓN 1: GitHub Profile

        Button(
            onClick = {
                if (showGithubQr) {
                    // Segundo clic: abre el perfil de GitHub en el navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jorgeberenguer2023-ops"))
                    context.startActivity(intent)
                    showGithubQr = false
                } else {
                    // Primer clic: despliega el QR y oculta los demás
                    showGithubQr = true
                    showLinkedinQr = false
                    showCvQr = false
                    showProyectosQr = false
                }
            },
            modifier = Modifier.fillMaxWidth(fraction = 0.8f)
        ) {
            Text(text = "Mi Perfil de GitHub")
        }

        // Despliegue condicional del QR de GitHub
        if (showGithubQr) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escanea el QR de GitHub:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val qrBitmap = generateQRCode("https://github.com/jorgeberenguer2023-ops")
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "Código QR de GitHub",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        // BOTÓN 2: LinkedIn Profile

        Button(
            onClick = {
                if (showLinkedinQr) {
                    // Segundo clic: abre el perfil de LinkedIn en el navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/jorge-berenguer-martin-b9442b438/"))
                    context.startActivity(intent)
                    showLinkedinQr = false
                } else {
                    // Primer clic: despliega el QR y oculta los demás
                    showLinkedinQr = true
                    showGithubQr = false
                    showCvQr = false
                    showProyectosQr = false
                }
            },
            modifier = Modifier.fillMaxWidth(fraction = 0.8f)
        ) {
            Text(text = "Mi Perfil de LinkedIn")
        }

        // Despliegue condicional del QR de LinkedIn
        if (showLinkedinQr) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escanea el QR de LinkedIn:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val qrBitmap = generateQRCode("https://www.linkedin.com/in/jorge-berenguer-martin-b9442b438/")
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "Código QR de LinkedIn",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        // BOTÓN 3: Descargar / Ver CV

        Button(
            onClick = {
                if (showCvQr) {
                    // Segundo clic: descarga el archivo PDF del CV en la carpeta Descargas del dispositivo
                    try {
                        val filename = "CV_Jorge_Berenguer.pdf"
                        val inputStream = context.resources.openRawResource(R.raw.cvingles)
                        
                        // Compatible con Scoped Storage (Android 10+) y almacenamiento tradicional
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            val resolver = context.contentResolver
                            val contentValues = ContentValues().apply {
                                put(MediaStore.Downloads.DISPLAY_NAME, filename)
                                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                            }
                            val uri = resolver.insert(MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)
                            uri?.let {
                                resolver.openOutputStream(it)?.use { output ->
                                    inputStream.copyTo(output)
                                }
                            }
                        } else {
                            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            downloadsDir.mkdirs()
                            val file = File(downloadsDir, filename)
                            file.outputStream().use { output ->
                                inputStream.copyTo(output)
                            }
                        }
                        
                        Toast.makeText(context, "¡CV descargado en la carpeta Descargas!", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Error al descargar el CV", Toast.LENGTH_SHORT).show()
                    }
                    showCvQr = false
                } else {
                    // Primer clic: despliega el QR del CV y oculta los demás
                    showCvQr = true
                    showGithubQr = false
                    showLinkedinQr = false
                    showProyectosQr = false
                }
            },
            modifier = Modifier.fillMaxWidth(fraction = 0.8f)
        ) {
            Text(text = "Descargar / Ver CV")
        }

        // Despliegue condicional del QR del CV (enlace directo raw de GitHub)
        if (showCvQr) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escanea para descargar mi CV:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val qrBitmap = generateQRCode("https://raw.githubusercontent.com/jorgeberenguer2023-ops/CV/main/CV%20Jorge%20Berenguer%20Mart%C3%ADn.pdf")
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "Código QR para descargar el CV",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        // BOTÓN 4: Mis Proyectos

        Button(
            onClick = {
                if (showProyectosQr) {
                    // Segundo clic: abre el repositorio de proyectos en el navegador
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/jorgeberenguer2023-ops/Mis-proyectos"))
                    context.startActivity(intent)
                    showProyectosQr = false
                } else {
                    // Primer clic: despliega el QR y oculta los demás
                    showProyectosQr = true
                    showGithubQr = false
                    showLinkedinQr = false
                    showCvQr = false
                }
            },
            modifier = Modifier.fillMaxWidth(fraction = 0.8f)
        ) {
            Text(text = "Mis Proyectos")
        }

        // Despliegue condicional del QR de Mis Proyectos
        if (showProyectosQr) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Escanea para ver Mis Proyectos:",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            val qrBitmap = generateQRCode("https://github.com/jorgeberenguer2023-ops/Mis-proyectos")
            if (qrBitmap != null) {
                Image(
                    bitmap = qrBitmap,
                    contentDescription = "Código QR para Mis Proyectos",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

/**
 * Vista previa (Preview) de la tarjeta de presentación para el diseñador de Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun TarjetaPreview() {
    MaterialTheme {
        TarjetaPresentacion()
    }
}

/**
 * Función auxiliar para generar un mapa de bits (ImageBitmap) de un código QR a partir de un texto o URL
 * utilizando la librería de código abierto ZXing.
 *
 * @param text Texto o URL a codificar en el código QR.
 * @param width Ancho de la imagen generada en píxeles.
 * @param height Alto de la imagen generada en píxeles.
 * @return [ImageBitmap] renderizable en Jetpack Compose, o null si ocurre un error.
 */
fun generateQRCode(text: String, width: Int = 512, height: Int = 512): ImageBitmap? {
    return try {
        // Utiliza QRCodeWriter de ZXing para codificar la matriz de bits del QR
        val bitMatrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        
        // Recorre la matriz y asigna píxeles negros para true y blancos para false
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
