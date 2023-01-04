package com.example.todolist

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todolist.Entities.TarefaEntity
import com.example.todolist.RetrofitRequests.AtividadesRequest
import com.example.todolist.database.AppDatabase
import com.example.todolist.databinding.FragmentFirstBinding
import com.example.todolist.model.Atividade
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null
    private var db : AppDatabase? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getDatabase(binding.root.context)

        binding.btnCriarTabela.setOnClickListener {
            var tarefa : Editable? = binding.textTarefa.text;
//            inserirTarefaRoom(tarefa);
            inserirTarefaApi(tarefa)
        }
        binding.btnListarTarefas.setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
    }

    private fun inserirTarefaApi(tarefa: Editable?) {
        var task = lifecycleScope.launch(Dispatchers.IO){
          var _response = AtividadesRequest().insertTask(Atividade(tarefa.toString(), null)).execute()
        }
        task.start()
        binding.textTarefa.setText("")
    }

    private fun inserirTarefaRoom(tarefa: Editable?) {
        lifecycleScope.launch(Dispatchers.IO){
           var uuid = db!!.tarefaDao().insert(TarefaEntity(null, tarefa.toString(), null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}