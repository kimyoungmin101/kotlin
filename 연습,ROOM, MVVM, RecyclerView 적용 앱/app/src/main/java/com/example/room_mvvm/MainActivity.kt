package com.example.room_mvvm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_mvvm.adapter.recyclerViewAdapter
import com.example.room_mvvm.databinding.ActivityMainBinding
import com.example.room_mvvm.model.History
import com.example.room_mvvm.model.HistoryViewModel
import com.example.room_mvvm.setting.OnItemClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var adapter: recyclerViewAdapter
    private lateinit var binding: ActivityMainBinding
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        historyViewModel.getAllTasks.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

        binding.addButton.setOnClickListener {
            val songTitle = binding.songTitleName.text.toString()
            val singer = binding.singerName.text.toString()
            val history = History(
                0,
                songTitle,
                singer
            )

            historyViewModel.insert(history)

            binding.singerName.setText("")
            binding.songTitleName.setText("")
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = recyclerViewAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun deleteTodo(history: History) {
        historyViewModel.delete(history)
    }

    override fun updateTodo(history: History) {
        binding.cardView.isVisible = true
        binding.scrollView.isVisible = false

        binding.updateSong.setText(history.songName)
        binding.updatePassword.setText(history.singerName)

        binding.updateResultButton.setOnClickListener {
            val songTitle = binding.updateSong.text.toString()
            val singer = binding.updatePassword.text.toString()
            val newHistory = History(
                history.id,
                songTitle,
                singer
            )

            historyViewModel.update(newHistory)

            binding.updateSong.setText("")
            binding.updatePassword.setText("")

            binding.cardView.isVisible = false
            binding.scrollView.isVisible = true


        }

    }
}
