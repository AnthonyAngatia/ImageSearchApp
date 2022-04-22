package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragementGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment: Fragment(R.layout.fragement_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragementGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragementGalleryBinding.bind(view)

        val adapter = UnsplashPhotoAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter{ adapter.retry()},
                footer = UnsplashPhotoLoadStateAdapter{ adapter.retry()}
            )
        }
        viewModel.photo.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}