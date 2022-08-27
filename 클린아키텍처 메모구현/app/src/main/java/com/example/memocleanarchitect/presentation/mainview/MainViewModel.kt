package com.example.memocleanarchitect.presentation.mainview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memocleanarchitect.data.entity.Memo
import com.example.memocleanarchitect.data.repository.MemoRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {

    //Room
    fun saveMemo(memo : Memo) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.insertMemoItem(memo)
    }

    fun deleteMemo(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        memoRepository.deleteMemoItem(id)
    }
}