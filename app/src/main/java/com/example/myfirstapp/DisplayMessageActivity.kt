package com.example.myfirstapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL


class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        getCharacter(message)
    }

    fun getCharacter(characterId: String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            /*
             * For @Query: You need to replace the following line with val response = service.getEmployees(2)
             * For @Path: You need to replace the following line with val response = service.getEmployee(53)
             */

            // Do the GET request and get response
            val response = service.getCharacter(characterId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val textView = findViewById<TextView>(R.id.textView).apply {
                        text = response.body()?.image }

//                    val url = URL(response.body()?.image)
//                    val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//                    imageView.setImageBitmap(bmp)

                    Log.d("Pretty Printed JSON :", response.body()?.image)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}