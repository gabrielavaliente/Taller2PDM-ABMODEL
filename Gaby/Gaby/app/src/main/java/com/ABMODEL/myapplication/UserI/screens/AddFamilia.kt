package com.ABMODEL.myapplication.UserI.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ABMODEL.myapplication.MainViewModel
import com.ABMODEL.myapplication.UserI.component.TopBar
import com.ABMODEL.myapplication.data.database.entity.Family
import com.ABMODEL.myapplication.utils.rememberLocationPermissionHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFamilia(
    viewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    var familyName by remember { mutableStateOf("") }
    var community by remember { mutableStateOf("") }
    var houseType by remember { mutableStateOf("") }
    var risk by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    val locationPermissionHandler = rememberLocationPermissionHandler(context) { location ->
        latitude = location.latitude
        longitude = location.longitude
        Log.d("AddFamilia", "Location set: $latitude, $longitude")
    }

    val houseTypes = listOf(
        "Adobe", "Lamina", "Ladrillos",
        "etc"
    )
    val riskLevels = listOf("Bajo", "Medio", "Alto", "Deshabilitado")

    var houseTypeExpanded by remember { mutableStateOf(false) }
    var riskExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Añadir Familia",
                navController = navController
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = familyName,
                    onValueChange = { familyName = it },
                    label = { Text("Nombre de la Familia") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = community,
                    onValueChange = { community = it },
                    label = { Text("Comunidad") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = houseTypeExpanded,
                    onExpandedChange = { houseTypeExpanded = !houseTypeExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        value = houseType,
                        onValueChange = {},
                        label = { Text("Tipo de Casa") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = houseTypeExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = houseTypeExpanded,
                        onDismissRequest = { houseTypeExpanded = false }
                    ) {
                        houseTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    houseType = type
                                    houseTypeExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = riskExpanded,
                    onExpandedChange = { riskExpanded = !riskExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        value = risk,
                        onValueChange = {},
                        label = { Text("Riesgo") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = riskExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = riskExpanded,
                        onDismissRequest = { riskExpanded = false }
                    ) {
                        riskLevels.forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level) },
                                onClick = {
                                    risk = level
                                    riskExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            locationPermissionHandler.requestLocationPermission()
                        },
                        colors = ButtonDefaults.buttonColors(),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(text = "Registrar su ubicación")
                    }

                    Button(
                        onClick = {
                                    val family = Family(
                                        family_name = familyName,
                                        community = community,
                                        houseType = houseType,
                                        risk = risk,
                                    )
                                    viewModel.insertFamily(family, context)  // insertar la familia en la base de datos local
                                    navController.popBackStack()


                        },
                        colors = ButtonDefaults.buttonColors(),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(text = "Guardar ")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}
