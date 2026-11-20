
package com.example.teamtitans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teamtitans.ui.theme.TeamTitansTheme


data class Jugador(
    val id: Int, // Identificador único.
    val nombre: String, // Nombre real.
    val nickname: String, // Apodo.
    val rol: String, // Posición del jugador.
    val edad: Int? = null, // Opcional: null si no se conoce.
    val nivel: Int? = null, // Opcional: null si no se conoce.
    @DrawableRes val imagen: Int? = null // Imagen opcional de drawable.
)


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Inicializa la actividad.
        enableEdgeToEdge() // Permite dibujar detrás de las barras del sistema.

        setContent { // Define la interfaz con Compose.
            TeamTitansTheme { // Aplica el tema original.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaTeamTitans(
                        modifier = Modifier.padding(innerPadding) // Respeta las barras del sistema.
                    )
                }
            }
        }
    }
}


@Composable
fun PantallaTeamTitans(modifier: Modifier = Modifier) {

    // LISTA OBSERVABLE: contiene los cinco jugadores iniciales.
    val jugadores = remember {
        mutableStateListOf(
            Jugador(
                id = 1,
                nombre = "Alejandro Portillo",
                nickname = "AlexPro",
                rol = "Atacante",
                edad = 21,
                nivel = 87,
                imagen = R.drawable.jugador1
            ),
            Jugador(
                id = 2,
                nombre = "Xavi Colom Rives",
                nickname = "ShadowX",
                rol = "Defensor",
                edad = 24,
                nivel = 91,
                imagen = R.drawable.jugador2
            ),
            Jugador(
                id = 3,
                nombre = "Marta Sanchez Trillo",
                nickname = "MartaGG",
                rol = "Soporte",
                edad = 20,
                nivel = 84,
                imagen = R.drawable.jugador3
            ),
            Jugador(
                id = 4,
                nombre = "Daniel Rodriguez Tovar",
                nickname = "Destroyer",
                rol = "Atacante",
                edad = 23,
                nivel = 89,
                imagen = R.drawable.jugador4
            ),
            Jugador(
                id = 5,
                nombre = "Dorotea Barceló Mir",
                nickname = "Dory",
                rol = "Atacante",
                edad = 23,
                nivel = 90
            )
        )
    }

    var siguienteId by remember { mutableIntStateOf(6) } // Siguiente ID disponible.


    var nombre by rememberSaveable { mutableStateOf("") } // Conserva el texto al recrear la actividad.
    var nickname by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("") }

    var mensajeError by rememberSaveable { mutableStateOf("") } // Mensaje de validación.



    var idEnEdicion by remember { mutableStateOf<Int?>(null) } // null: estamos añadiendo.
    var jugadorAEliminar by remember { mutableStateOf<Jugador?>(null) } // null: diálogo cerrado.

    Column(
        modifier = modifier
            .fillMaxSize() // Ocupa el espacio disponible.
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {



        Text(
            text = "TEAM TITANS",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        OutlinedTextField(
            value = nombre, // Texto almacenado.
            onValueChange = {
                nombre = it // Actualiza el estado.
                mensajeError = ""
            },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )



        OutlinedTextField(
            value = nickname,
            onValueChange = {
                nickname = it
                mensajeError = ""
            },
            label = { Text("Nickname") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )



        OutlinedTextField(
            value = rol,
            onValueChange = {
                rol = it
                mensajeError = ""
            },
            label = { Text("Rol") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )



        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )
        }



        Button(
            onClick = {

                val nombreLimpio = nombre.trim() // Elimina espacios iniciales y finales.
                val nicknameLimpio = nickname.trim()
                val rolLimpio = rol.trim()


                if (
                    nombreLimpio.isBlank() ||
                    nicknameLimpio.isBlank() ||
                    rolLimpio.isBlank()
                ) {
                    mensajeError = "Debes completar nombre, nickname y rol."

                } else {

                    val idActual = idEnEdicion

                    if (idActual == null) {

                        // AÑADIR: crea un objeto Jugador.
                        val nuevo = Jugador(
                            id = siguienteId,
                            nombre = nombreLimpio,
                            nickname = nicknameLimpio,
                            rol = rolLimpio
                        )

                        jugadores.add(nuevo) // Añade el jugador a la lista observable.
                        siguienteId++ // Incrementa el ID para el siguiente jugador.

                        nombre = ""
                        nickname = ""
                        rol = ""
                        mensajeError = ""

                    } else {


                        val indice = jugadores.indexOfFirst {
                            it.id == idActual
                        }

                        if (indice != -1) {

                            jugadores[indice] = jugadores[indice].copy(
                                nombre = nombreLimpio,
                                nickname = nicknameLimpio,
                                rol = rolLimpio
                            )

                            nombre = ""
                            nickname = ""
                            rol = ""
                            idEnEdicion = null // Sale del modo edición.
                            mensajeError = ""

                        } else {
                            mensajeError = "No se ha encontrado el jugador."
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (idEnEdicion == null) {
                    "Añadir jugador"
                } else {
                    "Guardar cambios"
                }
            )
        }


        if (idEnEdicion != null) {
            TextButton(
                onClick = {
                    nombre = ""
                    nickname = ""
                    rol = ""
                    idEnEdicion = null
                    mensajeError = ""
                }
            ) {
                Text("Cancelar edición")
            }
        }


        Text(
            text = "Jugadores inscritos: ${jugadores.size}",
            style = MaterialTheme.typography.titleMedium
        )


        LazyColumn(
            modifier = Modifier
                .weight(1f) // Ocupa el espacio vertical restante.
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            items(
                items = jugadores, // Lista que recorre.
                key = { it.id } // Identifica cada jugador mediante su ID.
            ) { jugador -> // Cada elemento de la colección.

                TarjetaJugador(
                    jugador = jugador,

                    // EDITAR: carga los datos en el formulario.
                    onEditar = {
                        idEnEdicion = jugador.id
                        nombre = jugador.nombre
                        nickname = jugador.nickname
                        rol = jugador.rol
                        mensajeError = ""
                    },


                    onEliminar = {
                        jugadorAEliminar = jugador
                    }
                )
            }
        }
    }


    val pendiente = jugadorAEliminar

    if (pendiente != null) {

        AlertDialog(
            onDismissRequest = {
                jugadorAEliminar = null // Cierra sin eliminar.
            },

            title = {
                Text("Eliminar jugador")
            },

            text = {
                Text("¿Estás seguro de que deseas eliminar a ${pendiente.nickname}?")
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        jugadores.removeAll {
                            it.id == pendiente.id // Elimina al jugador por ID.
                        }

                        // Cancela la edición si eliminamos ese mismo jugador.
                        if (idEnEdicion == pendiente.id) {
                            nombre = ""
                            nickname = ""
                            rol = ""
                            idEnEdicion = null
                            mensajeError = ""
                        }

                        jugadorAEliminar = null // Cierra el diálogo.
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        jugadorAEliminar = null // Cancela la operación.
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}



@Composable
fun TarjetaJugador(
    jugador: Jugador, // Objeto que queremos mostrar.
    onEditar: () -> Unit, // Acción recibida desde la pantalla principal.
    onEliminar: () -> Unit
) {

    Card(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val recursoImagen = jugador.imagen

                if (recursoImagen != null) {

                    // Muestra la fotografía original del jugador.
                    Image(
                        painter = painterResource(id = recursoImagen),
                        contentDescription = "Foto de ${jugador.nombre}",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape), // Imagen circular.
                        contentScale = ContentScale.Crop
                    )

                } else {

                    // Avatar para jugadores sin fotografía asignada.
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = jugador.nickname.take(1).uppercase(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = jugador.nickname,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(text = jugador.nombre)
                    Text(text = "Rol: ${jugador.rol}")

                    // Solo muestra edad y nivel si existen.
                    if (jugador.edad != null) {
                        Text(text = "Edad: ${jugador.edad} años")
                    }

                    if (jugador.nivel != null) {
                        Text(text = "Nivel: ${jugador.nivel}")
                    }
                }
            }

            // BOTONES
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(onClick = onEditar) {
                    Text("Editar")
                }

                Button(onClick = onEliminar) {
                    Text("Eliminar")
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TeamTitansPreview() {
    TeamTitansTheme {
        PantallaTeamTitans()
    }
}
