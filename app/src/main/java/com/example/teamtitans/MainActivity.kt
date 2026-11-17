package com.example.teamtitans

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.teamtitans.ui.theme.TeamTitansTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TeamTitansTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()      // Ocupa toda la pantalla.
                        .background(MaterialTheme.colorScheme.background)
                        .statusBarsPadding()    // Respeta la barra de estado.
                ) {

                    Text(
                        text = "TEAM TITANS",       // Título del equipo
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)        // Centra el título.
                            .padding(top = 16.dp)       // Añade espacio superior.
                    )

                    Jugador(
                        nombre = "AlexPro", // Nombre del primer jugador.
                        rol = "Atacante", // Posición.
                        edad = 21, // Edad.
                        nivel = 87, // Nivel.
                        imagen = R.drawable.jugador1 // Fotografía.
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp), // Margen lateral.
                        thickness = 2.dp, // Grosor de la línea.
                        color = MaterialTheme.colorScheme.primary // Color del tema.
                    )


                    Jugador(
                        nombre = "ShadowX",
                        rol = "Defensor",
                        edad = 24,
                        nivel = 91,
                        imagen = R.drawable.jugador2
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Jugador(
                        nombre = "MartaGG",
                        rol = "Soporte",
                        edad = 20,
                        nivel = 84,
                        imagen = R.drawable.jugador3
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Jugador(
                        nombre = "Destroyer",
                        rol = "Atacante",
                        edad = 23,
                        nivel = 89,
                        imagen = R.drawable.jugador4
                    )
                }
            }
        }
    }
}


@Composable // Permite crear elementos de la interfaz.
fun Jugador(
    nombre: String, // Nombre del jugador.
    rol: String, // Posición dentro del equipo.
    edad: Int, // Edad del jugador.
    nivel: Int, // Nivel de habilidad.
    @DrawableRes imagen: Int // Recurso de imagen.
) {
    Row(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho.
            .padding(16.dp), // Añade separación exterior.
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imagen), // Carga la fotografía.
            contentDescription = "Foto de $nombre",
            modifier = Modifier
                .size(80.dp) // Tamaño de la imagen.
                .clip(CircleShape), // Recorta la imagen en círculo.
            contentScale = ContentScale.Crop // Ajusta la fotografía.
        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold // Destaca el nombre.
            )

            Text(text = "Rol: $rol")
            Text(text = "Edad: $edad años")
            Text(text = "Nivel: $nivel")
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true) // Activa la vista previa con fondo.
@Composable // Permite representar la interfaz.
fun JugadorPreview() {
    TeamTitansTheme { // Aplica el tema de la aplicación.
        Jugador(
            nombre = "AlexPro", // Nombre del jugador.
            rol = "Atacante", // Posición en el equipo.
            edad = 21, // Edad.
            nivel = 87, // Nivel de habilidad.
            imagen = R.drawable.jugador1 // Fotografía.
        )
    }
}
