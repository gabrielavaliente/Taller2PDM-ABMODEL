package com.abmodel.proyectomoviles

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abmodel.proyectomoviles.data.database.MyApplication
import com.abmodel.proyectomoviles.data.database.entity.Family
import com.abmodel.proyectomoviles.data.database.entity.Persona
import com.abmodel.proyectomoviles.data.network.ApiService
import com.abmodel.proyectomoviles.data.network.RetrofitClient
import com.abmodel.proyectomoviles.ui.theme.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val _familyList = MutableStateFlow<List<Family>>(emptyList())
    val familyList: StateFlow<List<Family>> get() = _familyList

    private val _personasList = MutableStateFlow<List<Persona>>(emptyList())
    val personasList: StateFlow<List<Persona>> get() = _personasList

    private val db = MyApplication.database
    private val apiService: ApiService = RetrofitClient.apiService

    init {
        viewModelScope.launch {
            _familyList.value = db.familyDAO().getAllFamilies()
        }
    }

    fun insertFamily(family: Family, context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    db.familyDAO().insertFamily(family)
                    _familyList.value = db.familyDAO().getAllFamilies()
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error inserting family: ${e.message}")
                }
            } else {
                Log.e("MainViewModel", "No internet connection")
            }
        }
    }

    fun getFamily(familyId: Int): Family? {
        return _familyList.value.find { it.family_id == familyId }
    }

    fun insertPersona(persona: Persona) {
        viewModelScope.launch {
            try {
                db.personaDAO().insertPersona(persona)
                loadPersonas(persona.family)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error inserting persona: ${e.message}")
            }
        }
    }

    fun migrateDataToAPI(context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    val families = db.familyDAO().getAllFamilies()
                    val personas = db.personaDAO().getAllPersonas()

                    val dataMigrationRequest = ApiService.DataMigrationRequest(families, personas)
                    Log.d("MainViewModel", "Data to migrate: $dataMigrationRequest")

                    val response = apiService.migrateData(dataMigrationRequest)
                    if (response.isSuccessful) {
                        Log.d("MainViewModel", "Data migrated successfully")
                    } else {
                        Log.e("MainViewModel", "Failed to migrate data: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error migrating data: ${e.message}")
                }
            } else {
                Log.e("MainViewModel", "No internet connection")
            }
        }
    }


    fun loadPersonas(familyId: Int) {
        viewModelScope.launch {
            _personasList.value = db.personaDAO().getAllFamilyMembers(familyId)
        }
    }

    fun refreshData(context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    val apiFamilies = apiService.getAllFamilies()
                    apiFamilies.body()?.let { families ->
                        viewModelScope.launch {
                            db.familyDAO().insertFamilies(families)
                            _familyList.value = db.familyDAO().getAllFamilies()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainViewModel", "Error refreshing data: ${e.message}")
                }
            } else {
                Log.e("MainViewModel", "No internet connection")
            }
        }
    }

    suspend fun getPersona(personaId: Int): Persona? {
        return db.personaDAO().getPersonaById(personaId)
    }
}