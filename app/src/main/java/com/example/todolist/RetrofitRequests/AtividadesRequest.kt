package com.example.todolist.RetrofitRequests

import com.example.todolist.IRetrofitRequests.IAtividadesRequest
import com.example.todolist.model.Atividade
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AtividadesRequest {
    private val atividadeRetrofit: IAtividadesRequest
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        atividadeRetrofit = retrofit.create(IAtividadesRequest::class.java)
    }
//    fun getAllTask(): Call<MutableList<Atividade>> {
//        return atividade.getAllTasks()
//    }
//    fun insertAllTask(atividades : MutableList<Atividade>) : Call<Void> {
//        return atividade.insertAllTasks(atividades)
//    }
    fun obterAllTask(): Call<MutableList<Atividade>> {
        return atividadeRetrofit.obterAllTask()
    }
    fun insertTask(atividade : Atividade) : Call<Atividade> {
        return atividadeRetrofit.insertTask(atividade)
    }
    fun obterTask(idTask : Int) : Call<Atividade> {
        return atividadeRetrofit.obterTask(idTask)
    }
    fun deleteTask(idTask : Int) : Call<Void> {
        return atividadeRetrofit.deleteTask(idTask)
    }
    fun alterarTask(atividade : Atividade) : Call<Atividade> {
        return atividadeRetrofit.alterarTask(atividade.id!!, atividade)
    }
}