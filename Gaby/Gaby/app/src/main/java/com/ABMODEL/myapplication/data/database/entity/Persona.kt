package com.ABMODEL.myapplication.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Persona",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Family::class,
            parentColumns = arrayOf("family_id"),
            childColumns = arrayOf("family"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    )

)

data class Persona(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "persona_id")
    val id: Int = 0,

    @ColumnInfo(name = "dui")
    val dui : String = " ",

    @ColumnInfo(name = "name")
    val name : String = " ",

    @ColumnInfo(name = "birthdate")
    val birthdate : String = " ",

    @ColumnInfo(name = "grade")
    val grade : String = " ",

    @ColumnInfo(name = "read")
    val read : Boolean,

    @ColumnInfo(name = "write")
    val write : Boolean,

    //fk
    @ColumnInfo(name = "family", index =true)
    val family : Int
)