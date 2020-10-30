package com.example.myfirstapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.random.Random

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

// Create Retrofit
val RETROFIT: Retrofit = Retrofit.Builder()
    .baseUrl("https://rickandmortyapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImage()
    }

    private fun loadImage() {
        val newInstance = RickAndMortyFrag.newInstance(Random.nextInt(100).toString())

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.RickAndMortyLayout, newInstance)
            addToBackStack(null)
            commit()
        }
    }

    fun loadNextFragment(view: View) {
        loadImage()
    }
}