import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectofinal.R
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarTareaScreen(navController: NavController? = null) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Opción 1")
                    Text("Opción 2")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            FormularioAgregarTarea(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun FormularioAgregarTarea(modifier: Modifier = Modifier) {
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var fechaSeleccionada by remember { mutableStateOf("") }
    var archivoSeleccionado by remember { mutableStateOf("") }

    // Configuración del calendario para la selección de fecha
    val calendario = Calendar.getInstance()
    val anio = calendario.get(Calendar.YEAR)
    val mes = calendario.get(Calendar.MONTH)
    val dia = calendario.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            fechaSeleccionada = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, anio, mes, dia
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Agregar Tarea", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text(text = "Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text(text = "Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para seleccionar fecha
        OutlinedButton(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = if (fechaSeleccionada.isEmpty()) "Seleccionar fecha estimada" else fechaSeleccionada)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para simular selección de archivo
        OutlinedButton(onClick = {
            archivoSeleccionado = "archivo_simulado.pdf" // Simulación de archivo seleccionado
        }) {
            Text(text = if (archivoSeleccionado.isEmpty()) "Seleccionar archivo" else archivoSeleccionado)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar la tarea (no incluye lógica de almacenamiento)
        Button(onClick = {
            // Lógica para agregar la tarea (puedes implementar la funcionalidad según sea necesario)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Agregar Tarea")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAgregarTareaScreen() {
    AgregarTareaScreen() // Quitamos el navController para la vista previa
}
