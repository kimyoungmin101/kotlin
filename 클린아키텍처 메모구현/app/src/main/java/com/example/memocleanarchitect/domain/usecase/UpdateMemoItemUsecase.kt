package com.example.memocleanarchitect.domain.usecase

import com.example.memocleanarchitect.data.entity.Memo
import com.example.memocleanarchitect.data.repository.MemoRepository
import com.example.memocleanarchitect.domain.Usecase

internal class UpdateMemoItemUsecase(private val memoRepository: MemoRepository) : Usecase {
    suspend operator fun invoke(memo : Memo){
        memoRepository.updateToDoItem(memo)
    }
}