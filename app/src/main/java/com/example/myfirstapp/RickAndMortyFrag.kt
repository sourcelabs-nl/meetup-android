package com.example.myfirstapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [RickAndMortyFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class RickAndMortyFrag : Fragment(R.layout.fragment_rick_and_morty) {
    private lateinit var param1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) ?: "ERROR"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharacter(param1)

//        view?.findViewById<TextView>(R.id.fragmentTitle)?.apply {
//            text = param1
//        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            RickAndMortyFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private fun getCharacter(characterId: String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        val job = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + job)


        uiScope.launch {
            /*
             * For @Query: You need to replace the following line with val response = service.getEmployees(2)
             * For @Path: You need to replace the following line with val response = service.getEmployee(53)
             */

            // Do the GET request and get response
            val response = service.getCharacter(characterId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val textView = view?.findViewById<TextView>(R.id.textView)?.apply {
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