package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.proyectofinal.Datos.DAO.NotaDao
import com.example.proyectofinal.ui.VISTAS.AgregarTareaScreen
import com.example.proyectofinal.ui.VISTAS.PrimeraPantalla
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.Datos.BD.Notadb
import com.example.proyectofinal.ui.VISTAS.LoginViewModel
import com.example.proyectofinal.ui.VISTAS.LoginViewModelFactory


class MainActivity : ComponentActivity() {
    private lateinit var notaDao: NotaDao
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear la base de datos y obtener el DAO
        val database = Room.databaseBuilder(
            applicationContext,
            Notadb::class.java,
            "mi_bd"
        ).build()
        notaDao = database.NotaDao()

        // Usar la fábrica para obtener el ViewModel
        val factory = LoginViewModelFactory(notaDao)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        setContent {
            ProyectoFinalTheme {
                // Creamos el controlador de navegación
                val navController = rememberNavController()

                // Llamamos a la función NavegacionApp para manejar la navegación
                NavegacionApp(navController, viewModel)
            }
        }
    }
}

@Composable
fun NavegacionApp(navController: NavHostController, viewModel: LoginViewModel) {
    // Definimos el NavHost para manejar la navegación entre pantallas
    NavHost(
        navController = navController,
        startDestination = "primeraPantalla" // La pantalla inicial será PrimeraPantalla
    ) {
        // Primera Pantalla
        composable("primeraPantalla") {
            PrimeraPantalla(navController = navController, viewModel = viewModel)
        }
        // Segunda Pantalla (AgregarTareaScreen)
        composable("segundaPantalla") {
            AgregarTareaScreen(navController = navController, viewModel = viewModel)
        }
    }
}