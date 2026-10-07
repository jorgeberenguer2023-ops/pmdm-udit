package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

// ACTIVITY PRINCIPAL
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

// PANTALLA DE PORTADA
@Composable
fun PantallaPrincipal() {

    // remember + mutableStateOf: crea una variable que Compose vigila
    // Cuando su valor cambia, Compose vuelve a dibujar la pantalla solo,
    // sin que tengas que hacer nada más

    var mostrarPortada by remember { mutableStateOf(true) }

    // LaunchedEffect: Lanza una tarea que se ejecuta una sola vez
    // cuando la pantalla aparece
    LaunchedEffect(Unit) {
        delay(1500L)
        mostrarPortada = false
    }

    AnimatedVisibility(
        visible = mostrarPortada,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        PantallaDeCarga()
    }

    AnimatedVisibility(
        visible = !mostrarPortada,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        TiendaMainScreen()
    }
}

// PANTALLA DE CARGA (Splash / Loading)
@Composable
fun PantallaDeCarga() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🍔 Burger Shop",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cargando nuestro catálogo...",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

// PANTALLA PRINCIPAL DE LA TIENDA CON TOOLBAR, CARRITO Y NOTIFICACIONES
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendaMainScreen() {
    val carrito = remember { mutableStateListOf<Producto>() }
    var mostrarDialogoCarrito by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "🍔 Burger Shop",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { mostrarDialogoCarrito = true }) {
                        BadgedBox(
                            badge = {
                                if (carrito.isNotEmpty()) {
                                    Badge {
                                        Text(text = "${carrito.size}")
                                    }
                                }
                            }
                        ) {
                            Text(text = "🛒", fontSize = 22.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CatalogoHamburguesas(
                productos = catalogoHamburguesas,
                onAgregarAlCarrito = { producto ->
                    carrito.add(producto)
                    scope.launch {
                        snackbarHostState.showSnackbar("¡${producto.nombre} añadida al carrito!")
                    }
                }
            )

            if (mostrarDialogoCarrito) {
                DialogoCarrito(
                    carrito = carrito,
                    onDismiss = { mostrarDialogoCarrito = false },
                    onVaciarCarrito = {
                        carrito.clear()
                    },
                    onRealizarPedido = {
                        carrito.clear()
                        mostrarDialogoCarrito = false
                        scope.launch {
                            snackbarHostState.showSnackbar("🎉 ¡Pedido realizado con éxito!")
                        }
                    }
                )
            }
        }
    }
}

// MODELO DE DATOS DE PRODUCTO
data class Producto(
    val nombre: String,
    val precio: String,
    val precioNumerico: Double,
    val imagenResId: Int
)

// DATOS DE PRUEBA
val catalogoHamburguesas = listOf(
    Producto(
        nombre = "Burger Clásica",
        precio = "6,50 €",
        precioNumerico = 6.50,
        imagenResId = R.drawable.burger_clasica
    ),
    Producto(
        nombre = "Burger BBQ",
        precio = "7,50 €",
        precioNumerico = 7.50,
        imagenResId = R.drawable.burger_bbq
    ),
    Producto(
        nombre = "Burger Doble",
        precio = "7,50 €",
        precioNumerico = 7.50,
        imagenResId = R.drawable.burger_doble
    ),
    Producto(
        nombre = "Burger Picante",
        precio = "8,50 €",
        precioNumerico = 8.50,
        imagenResId = R.drawable.burger_picante
    ),
    Producto(
        nombre = "Burger Pollo",
        precio = "7,50 €",
        precioNumerico = 7.50,
        imagenResId = R.drawable.burger_pollo
    ),
    Producto(
        nombre = "Burger Vegetariana",
        precio = "7,50 €",
        precioNumerico = 7.50,
        imagenResId = R.drawable.burger_vegetariana
    )
)

// CATÁLOGO DE HAMBURGUESAS ORGANIZADO EN CUADRÍCULA ADAPTATIVA (GRID)
@Composable
fun CatalogoHamburguesas(
    productos: List<Producto>,
    onAgregarAlCarrito: (Producto) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(productos) { producto ->
            TarjetaProducto(
                producto = producto,
                onAgregarAlCarrito = { onAgregarAlCarrito(producto) }
            )
        }
    }
}

// TARJETA DE PRODUCTO INDIVIDUAL
@Composable
fun TarjetaProducto(
    producto: Producto,
    onAgregarAlCarrito: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = producto.imagenResId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = producto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = producto.precio,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onAgregarAlCarrito,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Añadir al carrito")
                }
            }
        }
    }
}

// DIÁLOGO / MODAL DEL CARRITO DE COMPRAS
@Composable
fun DialogoCarrito(
    carrito: List<Producto>,
    onDismiss: () -> Unit,
    onVaciarCarrito: () -> Unit,
    onRealizarPedido: () -> Unit
) {
    val total = carrito.sumOf { it.precioNumerico }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🛒 Mi Carrito (${carrito.size})",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            if (carrito.isEmpty()) {
                Text(
                    text = "El carrito está vacío. ¡Añade algunas hamburguesas!",
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        items(carrito) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.nombre,
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = item.precio,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = String.format(Locale.getDefault(), "%.2f €", total),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (carrito.isNotEmpty()) {
                Button(onClick = onRealizarPedido) {
                    Text("Realizar Pedido")
                }
            }
        },
        dismissButton = {
            Row {
                if (carrito.isNotEmpty()) {
                    OutlinedButton(onClick = onVaciarCarrito) {
                        Text("Vaciar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            }
        }
    )
}
