package com.abmodel.proyectomoviles.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abmodel.proyectomoviles.MainViewModel
import com.abmodel.proyectomoviles.ui.theme.component.TopBar
import com.abmodel.proyectomoviles.ui.theme.navigation.ScreenRoute

@Composable
fun FamilyDetailsScreen(
    viewModel: MainViewModel,
    familyId: Int,
    navController: NavController
) {
    val family by remember { mutableStateOf(viewModel.getFamily(familyId)) }
    val personas by viewModel.personasList.collectAsState()

    LaunchedEffect(familyId) {
        viewModel.loadPersonas(familyId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Familia",
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Detalles de la familia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.DarkGray
            )
            family?.let {
                ElevatedCard(
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Apellido: ${it.family_name}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Comunidad: ${it.community}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tipo de Casa: ${it.houseType}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Riesgo: ${it.risk}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Latitud: ${it.latitude} - Longitud: ${it.longitude}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                    }
                }
                Button(
                    onClick = {
                        navController.navigate(ScreenRoute.AddPersona.createRoute(familyId))
                    },
                    colors = ButtonDefaults.buttonColors(),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Añadir Persona", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Miembros de la familia",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.DarkGray
                )
                LazyColumn(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    items(personas) { persona ->
                        ElevatedCard(
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        ScreenRoute.PersonaDetails.createRoute(persona.id)
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.LightGray
                            )
                        ) {
                            Text(
                                text = persona.name,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                color = Color.DarkGray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
