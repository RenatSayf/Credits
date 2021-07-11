package com.zaimutest777.zaim.ui.registration

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.RegistrationFragmentBinding
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.registration_fragment)
{
    private lateinit var binding: RegistrationFragmentBinding
    private lateinit var mActivity: MyInitialActivity
    private lateinit var startVM: StartViewModel

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
        mActivity.supportActionBar?.hide()
        mActivity.findViewById<BottomNavigationView>(R.id.bottomNavView).apply {
            visibility = View.GONE
        }
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = RegistrationFragmentBinding.bind(view)
        startVM = ViewModelProvider(this)[StartViewModel::class.java]

        RxBus.getProduct().value?.let { p ->
            val url = p.url
            binding.registrationWebView.apply {
                webViewClient = RegistrationWebViewClient()
                settings.javaScriptEnabled = true
            }.loadUrl(url)
            startVM.setNetState(NetworkState.Loading(0))
        }

        startVM.netState.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Loading ->
                {
                    binding.loadProgBar.visibility = View.VISIBLE
                }
                is NetworkState.Completed ->
                {
                    binding.loadProgBar.visibility = View.INVISIBLE
                }
                is NetworkState.Error ->
                {
                    binding.loadProgBar.visibility = View.INVISIBLE
                }
            }
        })

    }

    inner class RegistrationWebViewClient : WebViewClient()
    {
        override fun onPageFinished(view: WebView?, url: String?)
        {
            super.onPageFinished(view, url)
            startVM.setNetState(NetworkState.Completed(200))
        }
    }

}