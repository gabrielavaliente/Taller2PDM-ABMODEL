package com.abmodel.proyectomoviles.ui.theme.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abmodel.proyectomoviles.data.database.entity.Family

@Composable
fun ListItem(
    family: Family,
    onItemClick: (Family) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(family) }
            .padding(16.dp)
    ) {
        Text(text = "ID: ${family.family_id}")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Apellido: ${family.family_name}")
    }
}