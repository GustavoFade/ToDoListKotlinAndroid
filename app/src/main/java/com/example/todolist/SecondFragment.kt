package com.example.todolist

import android.content.Context
import android.net.ConnectivityManager
import android.net.InetAddresses
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todolist.Entities.TarefaEntity
import com.example.todolist.RetrofitRequests.AtividadesRequest
import com.example.todolist.adapter.AdapterListTarefas
import com.example.todolist.database.AppDatabase
import com.example.todolist.databinding.FragmentSecondBinding
import com.example.todolist.model.Atividade
import com.example.todolist.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.InetAddress

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var recyclerView : RecyclerView
    private lateinit var _response: Response<MutableList<Atividade>>
    private var db : AppDatabase? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.root.findViewById(R.id.recyclerViewLista)
        db = AppDatabase.getDatabase(binding.root.context)

        var temConexaoComInternet = NetworkUtil.isNetworkConnected(context)
//        val atividadeAdapter : MutableList<Atividade> = if (temConexaoComInternet) buscarTodasTarefasApi() else buscarTodasDbRommTarefas()
        val atividadeAdapter : MutableList<Atividade> = buscarTodasTarefasApi()
        var adapterListTarefas = AdapterListTarefas(atividadeAdapter)
        var layoutManager : LayoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapterListTarefas
        adapterListTarefas.quandoClicaNoItemListener =
            object : AdapterListTarefas.QuandoClicaNoItemListener {
                override fun clickCell(atividade: Atividade) {
                    atualizarConcluido(atividade)
                }

                override fun clickCell(idTask: Int, position: Any) {
                    excluirTask(idTask)
                    adapterListTarefas.tarefas.removeAt(position as Int)
                    adapterListTarefas.notifyItemRemoved(position as Int)
                }


                private fun excluirTask(idTask: Int) {
                    var task = lifecycleScope.launch(Dispatchers.IO){
                        AtividadesRequest().deleteTask(idTask).execute()
                    }
                    task.start()
                    while(!task.isCompleted){

                    }
                }

                private fun atualizarConcluido(atividade: Atividade) {
                    var task = lifecycleScope.launch(Dispatchers.IO){
                        AtividadesRequest().alterarTask(atividade).execute()
                    }
                    task.start()
                    while(!task.isCompleted){

                    }
                }
            }
        recyclerView.addItemDecoration(
            DividerItemDecoration(binding.root.context,
                LinearLayout.VERTICAL)
        )


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun buscarTodasDbRommTarefas() : MutableList<Atividade>{
        var tarefasEntity : List<TarefaEntity> = arrayListOf()
        val task = lifecycleScope.launch(Dispatchers.IO){

            tarefasEntity = db!!.tarefaDao().getAll()
        }
        task.start()
        while(!task.isCompleted){

        }
        return EntityToAtividadeModel(tarefasEntity)
    }
//    private fun buscarTodasTarefasApi() : MutableList<Atividade>{
//
//        var task = lifecycleScope.launch(Dispatchers.IO){
//            val atividade : MutableList<Atividade> =  buscarTodasDbRommTarefas()
//            AtividadesRequest().insertAllTask(atividade).enqueue(object : Callback<Void> {
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                }
//
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                }
//            })
//            deletarDadosDbRoom()
//
//            var callApiTaskResponde: Call<MutableList<Atividade>> = AtividadesRequest().getAllTask()
//
//            response = callApiTaskResponde.execute()
//
//        }
//        task.start()
//        while(!task.isCompleted){
//
//        }
//        return response.body()
//    }

    private fun buscarTodasTarefasApi() : MutableList<Atividade>{

        var task = lifecycleScope.launch(Dispatchers.IO){
            _response = AtividadesRequest().obterAllTask().execute()
        }
        task.start()
        while(!task.isCompleted){

        }
        return _response.body()
    }

    private fun deletarDadosDbRoom() {
        lifecycleScope.launch(Dispatchers.IO){
            db!!.tarefaDao().deleteAll()
        }
    }

    private fun EntityToAtividadeModel(tarefasEntity: List<TarefaEntity>): MutableList<Atividade> {
        val mutableListAuxiliar : MutableList<Atividade> = arrayListOf()
        for (tarefa in tarefasEntity){
            val atv : Atividade = Atividade(tarefa.tarefa!!, tarefa.concluido)
            mutableListAuxiliar.add(atv)
        }
        return  mutableListAuxiliar
    }
}
