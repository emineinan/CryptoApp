import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cryptoapp.ui.onboarding.OnBoardingAdapter
import com.example.cryptoapp.ui.onboarding.OnBoardingItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentOnBoardingBinding
import com.example.cryptoapp.utils.SharedPreferences

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var onBoardingAdapter: OnBoardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(inflater)

        initAdapter()
        initListeners()
        setCurrentIndicator(0)
        initViewPagerListener()

        return binding.root
    }

    private fun initViewPagerListener() {
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
                if (binding.viewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                    finishIsVisible(false)
                } else finishIsVisible(true)
            }
        })
        (binding.viewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.linearLayoutIndicators.childCount
        for (i in 0 until childCount) {
            val indicator = binding.linearLayoutIndicators.getChildAt(i) as ImageView

            if (i == position) {
                indicator.setImageResource(R.drawable.indicator_active_background)
            } else {
                indicator.setImageResource(R.drawable.indicator_inactive_background)
            }
        }
    }

    private fun initListeners() {
        binding.buttonNext.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < onBoardingAdapter.itemCount - 1) {
                binding.viewPager.currentItem += 1
            } else if (binding.viewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                finishIsVisible(true)
                binding.viewPager.currentItem += 1
            }
        }

        binding.buttonFinish.setOnClickListener {
            SharedPreferences(requireContext()).putBoolean(true)
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
        binding.buttonSkip.setOnClickListener {
            SharedPreferences(requireContext()).putBoolean(true)
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
        binding.imageViewFirst.setOnClickListener {
            binding.viewPager.currentItem = 0
        }
        binding.imageViewSecond.setOnClickListener {
            binding.viewPager.currentItem = 1
        }
    }

    private fun finishIsVisible(isVisible: Boolean) {
        if (isVisible) {
            binding.buttonNext.visibility = View.GONE
            binding.buttonSkip.visibility = View.GONE
            binding.buttonFinish.visibility = View.VISIBLE
        } else {
            binding.buttonNext.visibility = View.VISIBLE
            binding.buttonSkip.visibility = View.VISIBLE
            binding.buttonFinish.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        onBoardingAdapter = OnBoardingAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.start,
                    requireContext().getString(R.string.on_boarding_title1),
                    requireContext().getString(R.string.description1)
                ),
                OnBoardingItem(
                    R.drawable.earn,
                    requireContext().getString(R.string.on_boarding_title2),
                    requireContext().getString(R.string.description2)
                )
            )
        )
        binding.viewPager.adapter = onBoardingAdapter
    }
}