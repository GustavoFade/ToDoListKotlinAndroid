package com.example.todolist.IRetrofitRequests

import com.example.todolist.model.Atividade
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IAtividadesRequest {
//    @GET("/AllAtividades")
//    fun getAllTasks() : Call<MutableList<Atividade>>
//    @POST("/AllAtividades")
//    fun insertAllTasks(@Body atividades : MutableList<Atividade>) : Call<Void>


    //endpoints Crud completo
    @GET("/{idTask}")
    fun obterTask(@Path("idTask") idTask : Int) : Call<Atividade>
    @GET("/")
    fun obterAllTask() : Call<MutableList<Atividade>>
    @POST("/")
    fun insertTask(@Body atividade : Atividade) : Call<Atividade>
    @PUT("/{idTask}")
    fun alterarTask(@Path("idTask") idTask: Int, @Body atividade: Atividade) : Call<Atividade>
    @DELETE("/{idTask}")
    fun deleteTask(@Path("idTask") idTask: Int) : Call<Void>
}