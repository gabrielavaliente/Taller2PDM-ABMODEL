package com.ABMODEL.myapplication

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ABMODEL.myapplication.data.database.MyApplication
import com.ABMODEL.myapplication.data.database.entity.Family
import com.ABMODEL.myapplication.data.database.entity.Persona
import com.ABMODEL.myapplication.data.network.ApiService
import com.ABMODEL.myapplication.data.network.RetrofitClient
import com.ABMODEL.myapplication.utils.NetworkUtils
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
            _familyList.value = db.familyDao().getAllFamilies()
        }
    }

    fun insertFamily(family: Family, context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    db.familyDao().insertFamily(family)
                    _familyList.value = db.familyDao().getAllFamilies()
                    Log.i("Mainviewmodel", "Er")
                } catch (e: Exception) {
                    Log.i("MainViewModel", "Error inserting family: ${e.message}")
                }
            } else {
                Log.i("MainViewModel", "No internet connection")
            }
        }
    }

    fun getFamily(familyId: Int): Family? {
        return _familyList.value.find { it.family_id == familyId }
    }

    fun insertPersona(persona: Persona) {
        viewModelScope.launch {
            try {
                db.personDao().insertPersona(persona)
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
                    val families = db.familyDao().getAllFamilies()
                    val personas = db.personDao().getAllPersonas()
                    families.forEach { family -> RetrofitClient.apiService.postFamily(family)}
                    personas.forEach { person -> RetrofitClient.apiService.postPerson(person)}

                    Log.i("Si", "np")
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
            _personasList.value = db.personDao().getAllFamilyMembers(familyId)
        }
    }

    fun refreshData(context: Context) {
        viewModelScope.launch {
            if (NetworkUtils.isInternetAvailable(context)) {
                try {
                    val apiFamilies = apiService.getAllFamilies()
                    apiFamilies.body()?.let { families ->
                        viewModelScope.launch {
                            db.familyDao().insertFamilies(families)
                            _familyList.value = db.familyDao().getAllFamilies()
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
        return db.personDao().getPersonaById(personaId)
    }
}