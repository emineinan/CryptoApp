package com.example.cryptoapp.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.OnboardingItemBinding

class OnBoardingAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: OnboardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onBoardingItem: OnBoardingItem) {
            binding.imageViewCryptoItem.setImageResource(onBoardingItem.onBoardingImage)
            binding.textViewTitleCrypto.text = onBoardingItem.title
            binding.textViewDescriptionCrypto.text = onBoardingItem.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingAdapter.ViewHolder {
        val itemBinding =
            LayoutInflater.from(parent.context).inflate(R.layout.onboarding_item, parent, false)
        return ViewHolder(OnboardingItemBinding.bind(itemBinding))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }
}
