package com.ABMODEL.myapplication.data.database

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set

        val database: AppDataBase by lazy {
            Room.databaseBuilder(
                instance.applicationContext, // Usar la referencia de instancia para obtener el contexto
                AppDataBase::class.java,
                "AppDataBase"
            ).fallbackToDestructiveMigration().build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Asignar la instancia de la aplicación
    }
}