package com.ABMODEL.myapplication.UserI.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ABMODEL.myapplication.MainViewModel
import com.ABMODEL.myapplication.UserI.component.TopBar
import com.ABMODEL.myapplication.UserI.navigation.ScreenRoute
import com.ABMODEL.myapplication.data.database.entity.Family
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: MainViewModel ,
    navController: NavController
) {
    val familyList by viewModel.familyList.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                title = "MINSAL - Administrador de Familias",
                navController = navController,
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                Log.d("HomeScreen", "Refresh button clicked")
                                viewModel.migrateDataToAPI(context)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Migrate to API")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(ScreenRoute.AddFamilia.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(familyList) { family ->
                FamilyListItem(
                    family = family,
                    onItemClick = {
                        navController.navigate(ScreenRoute.FamilyDetails.createRoute(family.family_id))
                    }
                )
            }
        }
    }
}


@Composable
fun FamilyListItem(family: Family, onItemClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Text(
            text = "Familia: ${family.family_name}",
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onItemClick)
                .padding(16.dp),
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}