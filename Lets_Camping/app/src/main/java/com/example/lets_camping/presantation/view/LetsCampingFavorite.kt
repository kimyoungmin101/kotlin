package com.example.lets_camping.presantation.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lets_camping.R
import com.example.lets_camping.databinding.FragmentLetsCampingFavoriteBinding
import com.example.lets_camping.databinding.FragmentLetsCampingSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LetsCampingFavorite : Fragment() {
    private var _binding: FragmentLetsCampingFavoriteBinding? = null
    private val binding get() = _binding!!

    // private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetsCampingFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}