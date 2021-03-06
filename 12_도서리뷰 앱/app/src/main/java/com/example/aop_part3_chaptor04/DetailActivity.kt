package com.example.aop_part3_chaptor04
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.aop_part3_chaptor04.databinding.ActivityDetailBinding
import com.example.aop_part3_chaptor04.model.Book
import com.example.aop_part3_chaptor04.model.Review

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB")
            .build()


        val bookModel = intent.getParcelableExtra<Book>("bookModel")

        binding.titleTextView.text = bookModel?.title.orEmpty()

        Glide
            .with(binding.coverImageView.context)
            .load(bookModel?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)

        binding.descriptionTextView.text = bookModel?.description.orEmpty()

        Thread {
            val review = db.reviewDao().getOne(bookModel?.uid?.toInt() ?: 0)
            runOnUiThread {
                binding.reviewEditText.setText(
                    review.review.toString())
            }
        }.start()

        binding.saveButton.setOnClickListener {
            Thread {
                db.reviewDao().saveReview(
                    Review(
                        bookModel?.uid?.toInt() ?: 0,
                        binding.reviewEditText.text.toString()
                    )
                )
            }.start()
        }

    }

}
