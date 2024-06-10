package com.ABMODEL.myapplication.UserI.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ABMODEL.myapplication.MainViewModel
import com.ABMODEL.myapplication.UserI.component.TopBar
import com.ABMODEL.myapplication.data.database.entity.Persona

@Composable
fun AddPersona(
    viewModel: MainViewModel,
    familyId: Int,
    navController: NavController
) {
    var dui by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var read by remember { mutableStateOf(false) }
    var write by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            title = "Agregar Persona",
            navController = navController
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dui,
            onValueChange = { dui = it },
            label = { Text("DUI") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = birthdate,
            onValueChange = { birthdate = it },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = read,
                onCheckedChange = { read = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Sabe leer")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = write,
                onCheckedChange = { write = it },
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Sabe escribir")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val persona = Persona(
                    dui = dui,
                    name = name,
                    birthdate = birthdate,
                    grade = grade,
                    read = read,
                    write = write,
                    family = familyId
                )
                viewModel.insertPersona(persona)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text(text = "Guardar Persona")
        }
    }
}