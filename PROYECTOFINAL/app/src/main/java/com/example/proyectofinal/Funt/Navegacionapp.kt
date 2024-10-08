import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.Pantallas.PrimeraPantalla
import com.example.proyectofinal.Pantallas.PrimeraPantalla

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "primeraPantalla") {
        composable("primeraPantalla") { PrimeraPantalla(navController) }
        composable("segundaPantalla") { AgregarTareaScreen(navController) }
    }
}
