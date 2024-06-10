package com.ABMODEL.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ABMODEL.myapplication.data.database.entity.Persona

@Dao
interface PersonaDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPersona(persona: Persona)

    @Query("SELECT * FROM Persona WHERE family=:family")
    suspend fun getAllFamilyMembers(family: Int): MutableList<Persona>

    @Query("SELECT * FROM Persona WHERE persona_id=:personaId")
    suspend fun getPersonaById(personaId: Int): Persona?

    @Query("SELECT * FROM persona")
    suspend fun getAllPersonas(): List<Persona>
}