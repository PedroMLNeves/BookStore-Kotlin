package com.example.android.myapplicationbook.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.myapplicationbook.*
import com.example.android.myapplicationbook.Activity.MainActivity
import com.example.android.myapplicationbook.Model.ResponseItems
import com.example.android.myapplicationbook.ViewModel.MainViewModel
import com.example.android.myapplicationbook.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    lateinit var sharedViewModel: MainViewModel

    private val binding get() = _binding!!

    private lateinit var myListAdapter: MyListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        sharedViewModel = (activity as MainActivity).viewModel

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        if(sharedViewModel.lastResult == 0 || sharedViewModel.lastResult != sharedViewModel.currentBook.value!!.size){
            sharedViewModel.getBookList()
        }


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putBoolean("main_state_show_favorite", false)
                apply()
            }
        }
        myListAdapter = MyListAdapter(requireContext(), sharedViewModel.currentBook.value!!) { book ->
            val bundle = bundleOf("book" to book)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
        binding.listView.adapter = myListAdapter

        binding.listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                val lastVisiblePosition = binding.listView.lastVisiblePosition
                val lastPos: Int = sharedViewModel.currentBook.value!!.size - 1
                if(lastVisiblePosition == lastPos){

                    sharedViewModel.getBookList()
                }
            }

            override fun onScroll(p0: AbsListView, p1: Int, p2: Int, p3: Int) {
                //Not needed
            }
        })

        var favoriteItems = listOf<ResponseItems>()

        for(responseItem in sharedViewModel.currentBook.value!!){
            if (sharedPref != null) {
                if(sharedPref.contains("favorite_book_" + responseItem.id)){
                    if(sharedPref.getBoolean("favorite_book_" + responseItem.id, false)){
                        favoriteItems = favoriteItems.plus(responseItem)

                    } else {
                    }
                } else {
                }
            }
        }

        sharedViewModel.saveAllItems()
        sharedViewModel.setFavoriteItems(favoriteItems)

        binding.buttonFavoriteList.setOnClickListener{
            if (sharedPref != null) {
                if(sharedPref.contains("main_state_show_favorite")) {
                    if (sharedPref.getBoolean("main_state_show_favorite", false)) {
                        myListAdapter.updateList(sharedViewModel.allItems.value!!)
                        myListAdapter.notifyDataSetChanged()

                        binding.buttonFavoriteList.text =
                            resources.getString(R.string.favorites_favorites)
                        with (sharedPref.edit()) {
                            putBoolean("main_state_show_favorite", false)
                            apply()
                        }
                    } else {
                        myListAdapter.updateList(sharedViewModel.favoriteItems.value!!)
                        myListAdapter.notifyDataSetChanged()

                        binding.buttonFavoriteList.text =
                            resources.getString(R.string.all_favorites)
                        with (sharedPref.edit()) {
                            putBoolean("main_state_show_favorite", true)
                            apply()
                        }
                    }
                } else {
                    myListAdapter.updateList(sharedViewModel.favoriteItems.value!!)
                    myListAdapter.notifyDataSetChanged()

                    binding.buttonFavoriteList.text =
                        resources.getString(R.string.all_favorites)
                    with (sharedPref.edit()) {
                        putBoolean("main_state_show_favorite", true)
                        apply()
                    }
                }
            }
        }

        sharedViewModel.currentBook.observe(viewLifecycleOwner, Observer {
            myListAdapter.updateList(sharedViewModel.currentBook.value!!)
            myListAdapter.notifyDataSetChanged()
        })

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}