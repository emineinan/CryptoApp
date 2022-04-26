package com.example.cryptoapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.cryptoapp.R
import com.example.cryptoapp.base.BaseFragment
import com.example.cryptoapp.databinding.FragmentDetailBinding
import com.example.cryptoapp.model.detail.CoinDetail
import com.example.cryptoapp.model.detail.DetailResponse
import com.example.cryptoapp.utils.Constants.API_KEY
import com.example.cryptoapp.utils.loadImage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailViewModel>(FragmentDetailBinding::inflate) {
    override val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateFinished() {
        viewModel.getDetail(API_KEY, args.symbol)
    }

    override fun observeEvents() {
        with(viewModel) {
            detailResponse.observe(viewLifecycleOwner, {
                parseData(it)
            })
            isLoading.observe(viewLifecycleOwner, {
                handleView(it)
            })
            onError.observe(viewLifecycleOwner, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun handleView(isLoading: Boolean = false) {
        binding.detailGroup.isVisible = !isLoading
        binding.progressBarDetail.isVisible = isLoading
    }

    private fun parseData(it: DetailResponse?) {
        val gson = Gson()
        val json = gson.toJson(it?.data)
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject[args.symbol] as JSONArray

        val coin = gson.fromJson(jsonArray.getJSONObject(0).toString(), CoinDetail::class.java)

        coin?.let {
            with(binding) {
                imageViewDetail.loadImage(it.logo)
                textViewTitleDetail.text = it.name
                textViewSymbolDetail.text = it.symbol
                textViewDescriptionDetail.text = it.description
            }
        }
    }

}