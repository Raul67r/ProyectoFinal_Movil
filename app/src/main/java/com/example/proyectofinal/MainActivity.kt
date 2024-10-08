package com.example.proyectofinal

import AgregarTareaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.example.proyectofinal.ui.theme.PROYECTOFINALTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.Pantallas.PrimeraPantalla
import com.example.proyectofinal.Pantallas.PrimeraPantalla

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PROYECTOFINALTheme {
                // Creamos el controlador de navegación
                val navController = rememberNavController()
                // Llamamos a la función NavegacionApp para manejar la navegación
                NavegacionApp(navController)
            }
        }
    }
}

@Composable
fun NavegacionApp(navController: NavHostController) {
    // Definimos el NavHost para manejar la navegación entre pantallas
    NavHost(
        navController = navController,
        startDestination = "primeraPantalla" // La pantalla inicial será PrimeraPantalla
    ) {
        // Primera Pantalla
        composable("primeraPantalla") {
            PrimeraPantalla(navController = navController) // Navega desde la primera pantalla
        }
        // Segunda Pantalla (AgregarTareaScreen)
        composable("segundaPantalla") {
            AgregarTareaScreen(navController = navController) // La segunda pantalla es AgregarTareaScreen
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMain() {
    val navController = rememberNavController()
    PROYECTOFINALTheme {
        NavegacionApp(navController)
    }
}

