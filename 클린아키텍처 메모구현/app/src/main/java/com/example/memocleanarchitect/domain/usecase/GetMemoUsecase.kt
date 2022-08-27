package com.example.memocleanarchitect.domain.usecase

import com.example.memocleanarchitect.data.repository.MemoRepository
import com.example.memocleanarchitect.domain.Usecase

internal class GetMemoUsecase(private val memoRepository: MemoRepository) : Usecase {
    suspend operator fun invoke(id:Long){
        memoRepository.getMemoItem(id)
    }
}