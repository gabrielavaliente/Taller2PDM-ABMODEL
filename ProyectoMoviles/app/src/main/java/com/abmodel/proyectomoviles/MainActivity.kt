package com.abmodel.proyectomoviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.abmodel.proyectomoviles.ui.theme.navigation.Navigation
import com.abmodel.proyectomoviles.ui.theme.proyectomoviles

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view model
        val viewModel : MainViewModel by viewModels()

        setContent {
            proyectomoviles {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Navigation(viewModel)
                }
            }
        }
    }
}


