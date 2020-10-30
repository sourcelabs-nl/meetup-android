package com.example.myfirstapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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

        view?.findViewById<TextView>(R.id.fragmentTitle)?.apply {
            text = param1
        }

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
}