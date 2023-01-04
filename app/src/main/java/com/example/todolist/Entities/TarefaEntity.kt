package com.example.todolist.Entities

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefa")
data class TarefaEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long? = 0,
    @ColumnInfo(name = "tarefa") val tarefa: String?,
    @ColumnInfo(name = "tarefa_concluida") val concluido: Boolean?
)