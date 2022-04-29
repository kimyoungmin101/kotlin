package com.example.githubuserappyoungmin.ui

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserappyoungmin.*
import com.example.githubuserappyoungmin.api.RetrofitService
import com.example.githubuserappyoungmin.databinding.AcitivityDetailBinding
import com.example.githubuserappyoungmin.model.User
import com.example.githubuserappyoungmin.mvvm.MainViewModel
import com.example.githubuserappyoungmin.mvvm.MyViewModelFactory
import com.example.githubuserappyoungmin.room.MainRepository
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), OnItemClick {
    lateinit var binding: AcitivityDetailBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        val actionbar = supportActionBar
        actionbar!!.title = "Detail Activity"

        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val intent: Intent = getIntent()

        val db = AppDatabase.getDataBase(this)
        val userDao = db.getUserDao()

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService, userDao)

        viewModel  = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        var userData = intent.getParcelableExtra<User>("user")
        val cntUser = viewModel.selectUser(userData!!.id)
        cntUser.observe(this, Observer<User>{
            binding.webTextView.text = it?.url
            binding.titleTextView.text = it?.title

            val user = it

            if(user.hart == true){
                binding.detailHart.setBackgroundResource(R.drawable.ic_favorite_pink)
            }
            else{
                binding.detailHart.setBackgroundResource(R.drawable.ic_favorite_grey)
            }

            binding.detailHart.setOnClickListener {

                updateUser(user)
            }

            binding.webTextView.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("${userData?.url}"))
                startActivity(intent)
            }

            val imageUrl = it?.avatar_url

            putPoster(imageUrl.toString())


        })

    }


    private fun putPoster(avatar_url: String) {
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this))
        builder.build().load(avatar_url)
            .error(R.drawable.ic_launcher_background)
            .into(binding.mainImageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun intentAcitivity(user: User) {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: User) {
        if (user.hart == false){
            user.hart = true
        }
        else{
            user.hart = false
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getUpdateTakst(user)
        }
    }

}