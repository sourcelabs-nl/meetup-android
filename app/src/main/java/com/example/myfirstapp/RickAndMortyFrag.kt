package com.example.myfirstapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

// Create Retrofit
private val retrofit = Retrofit.Builder()
    .baseUrl("https://rickandmortyapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rick_and_morty, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharacter(param1)
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

                    view?.findViewById<TextView>(R.id.fragmentTitle)?.apply {
                        text = response.body()?.image
                    }

                    val imageView = view?.findViewById<ImageView>(R.id.rickAndMortyPicture)

                    imageView?.let {
                        Picasso.Builder(requireContext()).apply {
                            listener { _, _, exception -> exception.printStackTrace() }
                        }.build().load(response.body()?.image).into(imageView)
                    }

                    Log.d("Pretty Printed JSON :", response.body()?.image)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}