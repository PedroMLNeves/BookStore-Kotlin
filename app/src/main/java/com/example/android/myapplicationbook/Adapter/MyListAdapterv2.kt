package com.example.android.myapplicationbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.android.myapplicationbook.Model.ResponseBook
import com.example.android.myapplicationbook.Model.ResponseItems
import com.example.android.myapplicationbook.databinding.CustomAdapterBinding

class MyListAdapter(context: Context, var bookInfoList: List<ResponseItems>, private val onItemClicked: (Any) -> Any) :
    ArrayAdapter<ResponseItems>(context, 0, bookInfoList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = CustomAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        //binding.item = items[position]
        binding.title.text = bookInfoList[position].volumeInfo.title
        var authors = "Author/s:"
        if( bookInfoList[position].volumeInfo.authors.isNotEmpty()){
            for(author in  bookInfoList[position].volumeInfo.authors){
                authors += " $author"
            }
        }
        binding.author.text = authors
        Glide.with(context).load(bookInfoList[position].volumeInfo.imageLinks.thumbnail.replace("http", "https")).into(binding.icon);
        binding.root.setOnClickListener{ onItemClicked(bookInfoList[position]) }
        return binding.root
    }


    override fun getCount(): Int {
        return bookInfoList.size
    }

    fun updateList(bookInfoList: List<ResponseItems>) {
        this.bookInfoList = bookInfoList
    }
}