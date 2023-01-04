package com.example.todolist.model

import java.util.Date

data class Atividade( var id: Int?, var nomeTarefa: String, var concluido: Boolean?) {
    constructor(nomeTarefa: String, concluido: Boolean?) : this(null, nomeTarefa, concluido)
}