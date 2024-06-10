package com.ABMODEL.myapplication.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

@Composable
fun rememberLocationPermissionHandler(
    context: Context,
    onLocationReceived: (Location) -> Unit
): LocationPermissionHandler {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation(context, onLocationReceived)
        } else {
        }
    }
    return remember {
        LocationPermissionHandler(
            context = context,
            locationPermissionLauncher = locationPermissionLauncher,
            onLocationReceived = onLocationReceived
        )
    }
}

class LocationPermissionHandler(
    private val context: Context,
    private val locationPermissionLauncher: androidx.activity.result.ActivityResultLauncher<String>,
    private val onLocationReceived: (Location) -> Unit
) {
    fun requestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        when (ContextCompat.checkSelfPermission(context, permission)) {
            PermissionChecker.PERMISSION_GRANTED -> {
                getLocation(context, onLocationReceived)
            }
            else -> {
                locationPermissionLauncher.launch(permission)
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun getLocation(context: Context, onLocationReceived: (Location) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.getCurrentLocation(
        LocationRequest.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationReceived(location)
            Log.d("AddFamilia", "Location received: ${location.latitude}, ${location.longitude}")
            Toast.makeText(context, "Ubicación recibida correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("AddFamilia", "Location is null")
        }
    }.addOnFailureListener { exception ->
        Log.e("AddFamilia", "Error getting location: ${exception.message}")
    }
}
