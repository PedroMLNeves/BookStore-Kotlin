package com.example.android.myapplicationbook.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.android.myapplicationbook.Model.ResponseItems
import com.example.android.myapplicationbook.R
import com.example.android.myapplicationbook.ViewModel.MainViewModel
import com.example.android.myapplicationbook.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return



        if(arguments?.get("book") is ResponseItems){
            val teste : ResponseItems = arguments?.get("book") as ResponseItems

            if(sharedPref.contains("favorite_book_" + teste.id)){
                if(sharedPref.getBoolean("favorite_book_" + teste.id, false)){
                    binding.buttonFavorite.text = resources.getString(R.string.remove_favorite)
                } else {
                    binding.buttonFavorite.text = resources.getString(R.string.add_favorite)
                }
            } else {
                binding.buttonFavorite.text = resources.getString(R.string.add_favorite)
            }

            binding.buttonFavorite.setOnClickListener{
                if(binding.buttonFavorite.text.equals(resources.getString(R.string.remove_favorite))){
                    with (sharedPref.edit()) {
                        putBoolean("favorite_book_" + teste.id, false)
                        apply()
                    }
                    binding.buttonFavorite.text = resources.getString(R.string.add_favorite)
                } else {
                    with (sharedPref.edit()) {
                        putBoolean("favorite_book_" + teste.id, true)
                        apply()
                    }
                    binding.buttonFavorite.text = resources.getString(R.string.remove_favorite)
                }
            }

            binding.textviewTitle.text = String.format("Title: %s", teste.volumeInfo.title)
            binding.textviewDescription.text = String.format("Description: %s", teste.volumeInfo.description)
            var authors = "Author/s:"
            if(teste.volumeInfo.authors.isNotEmpty()){
                for(author in teste.volumeInfo.authors){
                    authors += " $author"
                }
            }
            binding.textviewAuthor.text = authors
            binding.textviewNumPages.text = String.format("NumPages: %s", teste.volumeInfo.pageCount.toString())
            Glide.with(this).load(teste.volumeInfo.imageLinks.thumbnail.replace("http", "https")).into(binding.image);
        }


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}