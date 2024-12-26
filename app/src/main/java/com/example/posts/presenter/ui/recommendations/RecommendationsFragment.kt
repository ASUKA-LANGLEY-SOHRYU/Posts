package com.example.posts.presenter.ui.recommendations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.posts.databinding.FragmentRecommendationsBinding


class RecommendationsFragment : Fragment() {

    private lateinit var binding: FragmentRecommendationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}