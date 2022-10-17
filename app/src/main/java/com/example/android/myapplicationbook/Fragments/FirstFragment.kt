package com.example.android.myapplicationbook.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.android.myapplicationbook.ApiInterface
import com.example.android.myapplicationbook.Model.ResponseItems
import com.example.android.myapplicationbook.MyListAdapter
import com.example.android.myapplicationbook.R
import com.example.android.myapplicationbook.RetrofitClient
import com.example.android.myapplicationbook.ViewModel.MainViewModel
import com.example.android.myapplicationbook.databinding.FragmentFirstBinding
import kotlinx.coroutines.runBlocking

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val binding get() = _binding!!

    private var lastResult: Int = 0

    lateinit var myListAdapter: MyListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)


        if(lastResult == 0 || lastResult != sharedViewModel.currentBook.value!!.size){
            getBookList()
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
                Log.e("POG", "POG$lastVisiblePosition - lastResult - $lastResult")
                val lastPos: Int = sharedViewModel.currentBook.value!!.size - 1
                if(lastVisiblePosition == lastPos){

                    getBookList()
                    Log.e("POG", "POG")
                }
            }

            override fun onScroll(p0: AbsListView, p1: Int, p2: Int, p3: Int) {
                val totalItemCount = p0.checkedItemCount
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
        return binding.root

    }

    private fun getBookList() = runBlocking {
        Log.e("POG", "lastResult - $lastResult")




        val retrofit = RetrofitClient.RetrofitClient.getInstance()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        try {

            val response = apiInterface.getAllBooks("ios",20,lastResult)
            lastResult +=20
            if (response.isSuccessful()) {

                response.body()?.let { sharedViewModel.setResponseBook(it.items) }
                myListAdapter.updateList(sharedViewModel.currentBook.value!!)
                myListAdapter.notifyDataSetChanged()

            } else {
                Log.e("Error",response.errorBody().toString())
            }
        }catch (Ex:Exception){
            Log.e("Error",Ex.localizedMessage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}