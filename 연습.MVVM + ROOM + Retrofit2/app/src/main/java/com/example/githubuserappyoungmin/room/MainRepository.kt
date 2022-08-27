package com.example.githubuserappyoungmin.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserappyoungmin.AppDatabase
import com.example.githubuserappyoungmin.api.RetrofitService
import com.example.githubuserappyoungmin.model.ModelEntity
import com.example.githubuserappyoungmin.model.User
import com.example.githubuserappyoungmin.model.UserDao
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository constructor(
    private val retrofitService: RetrofitService,
    private val userDao: UserDao
) {
    fun selectUser(id: Int): LiveData<User> {
        return userDao.selectUser(id)
    }

    fun getAllRecords(): LiveData<List<User>> {
        return userDao.getAllRecords()
    }

    fun updateRecord(repositoryData: User) {
        userDao.update(repositoryData)
    }

    suspend fun insertRecord(repositoryData: User) {
        userDao.insertRecords(repositoryData)
    }


    fun makeApiCall() {
        val call: Call<ModelEntity> = retrofitService.getAllTasks()
        call?.enqueue(object : Callback<ModelEntity> {
            override fun onResponse(
                call: Call<ModelEntity>,
                response: Response<ModelEntity>
            ) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.deleteAllRecords()
                        response.body()?.items?.forEach {
                            insertRecord(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ModelEntity>, t: Throwable) {
                Log.d("this", "Error : ${t} ")
            }

        })
    }

}

