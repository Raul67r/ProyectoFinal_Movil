package com.example.proyectofinal

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.proyectofinal.Datos.BD.AppDatabase
import com.example.proyectofinal.Datos.DAO.MultimediaDao
import com.example.proyectofinal.Datos.DAO.MultimediaTareaDao
import com.example.proyectofinal.Datos.DAO.NotaDao
import com.example.proyectofinal.Datos.DAO.RecordatorioDAO
import com.example.proyectofinal.Datos.DAO.TareaDao
import com.example.proyectofinal.ui.VISTAS.AgregarNotaScreen
import com.example.proyectofinal.ui.VISTAS.AgregarTareaScreen
import com.example.proyectofinal.ui.VISTAS.AgregarTareaScreenEditar
import com.example.proyectofinal.ui.VISTAS.LoginViewModel
import com.example.proyectofinal.ui.VISTAS.LoginViewModelFactory
import com.example.proyectofinal.ui.VISTAS.NotaItemTASK
import com.example.proyectofinal.ui.VISTAS.PrimeraPantalla
import com.example.proyectofinal.ui.VISTAS.PrimeraPantallaTASK

import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {
    private lateinit var notaDao: NotaDao
    private lateinit var tareaDao: TareaDao
    private lateinit var MultimediaTareaDao: MultimediaTareaDao
    private lateinit var Multimedia: MultimediaDao
    private lateinit var Recordatorio: RecordatorioDAO
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear la base de datos y obtener el DAO
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mi_bd"
        ).build()
        notaDao = database.notaDao()
        Multimedia = database.multimediaDao()
        tareaDao=database.TareaDao()
        MultimediaTareaDao=database.MultimediaTareaDao()
        Recordatorio=database.RecordatorioDAO()

        // Usar la fábrica para obtener el ViewModel
        val factory = LoginViewModelFactory(notaDao,Multimedia,tareaDao,MultimediaTareaDao,Recordatorio)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        setContent {
            ProyectoFinalTheme {
                // Creamos el controlador de navegación
                val navController = rememberNavController()

                // Llamamos a la función NavegacionApp para manejar la navegación
                NavegacionApp(navController, viewModel)
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channelId = "notify-channel"
                        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                        if (notificationManager.getNotificationChannel(channelId) == null) {
                            val channel = NotificationChannel(channelId, "Recordatorio", NotificationManager.IMPORTANCE_HIGH)
                            notificationManager.createNotificationChannel(channel)
                        }
                    }
                    // Solicitar permisos necesarios
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PERMISSION_GRANTED) {
                            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
                        }
                    }
                }
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
            AgregarNotaScreen(navController = navController, viewModel = viewModel)
        }
        composable("TerceraPantalla") {
            AgregarTareaScreen(navController = navController, viewModel = viewModel)
        }
        composable("PantallainicioTareas") {
            PrimeraPantallaTASK(navController = navController, viewModel = viewModel)
        }
        composable("Pantallaeditar") {
            AgregarTareaScreenEditar(navController = navController, viewModel = viewModel)
        }

    }
}
