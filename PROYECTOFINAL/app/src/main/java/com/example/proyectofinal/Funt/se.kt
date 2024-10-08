package com.example.proyectofinal.Funt

sealed class Pantallas(val route:String){
    object PrimeraPantalla:Pantallas("PantallaInicio")
    object segundaPantalla:Pantallas("SegundaPnatalla")
}