package com.zaimutest777.zaim.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.DetailsFragmentBinding
import com.zaimutest777.zaim.utils.RxBus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment)
{
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var mActivity: MyInitialActivity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mActivity = activity as MyInitialActivity
        mActivity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {
                mActivity.findNavController(R.id.nav_host_fragment).navigateUp()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mActivity.findViewById<BottomNavigationView>(R.id.bottomNavView).apply {
            visibility = View.GONE
        }
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.text_more_details)
            it.show()
        }
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)

        val product = RxBus.getProduct().value

        product?.let { p ->
            Glide.with(mActivity).load(Uri.parse(p.imageUrl)).into(binding.logoImgView)

            if (p.cash.isEmpty())
            {
                binding.cashIconView.visibility = View.GONE
            }

            if (p.mastercard.isEmpty())
            {
                binding.masterCardIconView.visibility = View.GONE
            }

            if (p.mir.isEmpty())
            {
                binding.mirIconView.visibility = View.GONE
            }

            if (p.visa.isEmpty())
            {
                binding.visaIconView.visibility = View.GONE
            }

            if (p.qiwi.isEmpty())
            {
                binding.qiviIconView.visibility = View.GONE
            }

            if (p.yandex.isEmpty())
            {
                binding.yandexIconView.visibility = View.GONE
            }

            val description = HtmlCompat.fromHtml(p.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.descriptionView.text = description

            binding.getBtn.setOnClickListener {
                mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_detailsFragment_to_registrationFragment)
            }
        }

    }




}