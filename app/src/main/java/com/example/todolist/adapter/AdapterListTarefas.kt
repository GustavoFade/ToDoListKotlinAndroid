package com.example.todolist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.model.Atividade

class AdapterListTarefas(var tarefas: MutableList<Atividade>,    var quandoClicaNoItemListener: QuandoClicaNoItemListener =
    object : QuandoClicaNoItemListener {
        override fun clickCell(atividade: Atividade) {
        }
        override fun clickCell(idTask: Int, position: Any) {
        }
    }) : RecyclerView.Adapter<AdapterListTarefas.ViewHolder>() {


    interface QuandoClicaNoItemListener {
        fun clickCell(atividade: Atividade)
        fun clickCell(idTask: Int, position: Any)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var terefa : TextView = itemView.findViewById(R.id.row_text_tarefa)
        var id : TextView = itemView.findViewById(R.id.idCelula)
        var concluido : CheckBox = itemView.findViewById(R.id.checkBoxTarefa)
        var btnDelete : ImageButton = itemView.findViewById(R.id.deleteTask)

        init {
//            itemView.setOnClickListener {
//                quandoClicaNoItemListener.quandoClica(atividade = Atividade(id.text.toString().toInt(), terefa.text.toString(), concluido.isChecked))
//            }
            concluido.setOnClickListener {
                quandoClicaNoItemListener.clickCell(atividade = Atividade(id.text.toString().toInt(), terefa.text.toString(), concluido.isChecked))
            }
            btnDelete.setOnClickListener {
                quandoClicaNoItemListener.clickCell(idTask = id.text.toString().toInt(), terefa.tag)

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLista : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_tarefas, parent,false)
        return ViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var tarefaCurrent : Atividade = tarefas[position]
        holder.terefa.tag = position
        holder.terefa.text = tarefaCurrent.nomeTarefa
        holder.id.text = tarefaCurrent.id.toString()
        holder.concluido.isChecked = tarefaCurrent.concluido == true
    }

    override fun getItemCount(): Int {
        return tarefas.size
    }

}