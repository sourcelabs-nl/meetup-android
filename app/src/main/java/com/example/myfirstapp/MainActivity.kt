package com.example.myfirstapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loadNextFragment(view: View) {

        val newInstance = RickAndMortyFrag.newInstance(Random.nextInt(100).toString())

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.RickAndMortyLayout, newInstance)
            addToBackStack(null)
            commit()
        }

    }
}