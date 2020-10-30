package com.example.myfirstapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loadNextFragment(view: View) {

        val newInstance = RickAndMortyFrag.newInstance("Number: " + Random.nextInt(100))

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.RickAndMortyLayout, newInstance)
            addToBackStack(null)
            commit()
        }

    }
}