package com.example.todolist.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.Entities.TarefaEntity

@Dao
interface ITarefaDao {
    @Query("SELECT * FROM tarefa")
    fun getAll(): List<TarefaEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tarefas: TarefaEntity)

    @Update
    fun update(tarefa: TarefaEntity)

    @Delete
    fun delete(tarefa: TarefaEntity)

    @Query("DELETE FROM tarefa")
    fun deleteAll()
}