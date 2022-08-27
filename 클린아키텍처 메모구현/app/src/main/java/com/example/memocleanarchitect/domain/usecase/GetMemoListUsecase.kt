package com.example.memocleanarchitect.domain.usecase

import com.example.memocleanarchitect.data.repository.MemoRepository
import com.example.memocleanarchitect.domain.Usecase

internal class GetMemoListUsecase(private val memoRepository: MemoRepository) : Usecase {
    suspend operator fun invoke(){
        memoRepository.getMemoList()
    }
}