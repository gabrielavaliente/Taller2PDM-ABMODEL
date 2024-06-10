package com.ABMODEL.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ABMODEL.myapplication.data.database.dao.FamilyDAO
import com.ABMODEL.myapplication.data.database.dao.PersonaDAO
import com.ABMODEL.myapplication.data.database.entity.Family
import com.ABMODEL.myapplication.data.database.entity.Persona

@Database(entities = [Family::class,Persona::class], version = 3, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun familyDao():FamilyDAO

    abstract fun personDao():PersonaDAO
}
