package com.zaimutest777.zaim.ui.policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.PrivatePolicyFragmentBinding
import com.zaimutest777.zaim.utils.Consts
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PrivatePolicyFragment : Fragment(R.layout.private_policy_fragment)
{
    private lateinit var binding: PrivatePolicyFragmentBinding
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
                try
                {
                    mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_privatePolicyFragment_to_confirmFragment)
                } catch (e: Exception)
                {}
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.title_private_policy)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }
        return inflater.inflate(R.layout.private_policy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = PrivatePolicyFragmentBinding.bind(view)
        startVM = ViewModelProvider(this)[StartViewModel::class.java]

        RxBus.getConfig().value?.let { frc ->
            val privatePolicyLink = frc.getString(Consts.PRIVATE_POLICY)
            if (privatePolicyLink.isNotEmpty())
            {
                binding.policyWebView.apply {
                    webViewClient = PolicyWebViewClient()
                }.loadUrl(privatePolicyLink)
                startVM.setNetState(NetworkState.Loading(0))
            }
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

    inner class PolicyWebViewClient : WebViewClient()
    {
        override fun onPageFinished(view: WebView?, url: String?)
        {
            super.onPageFinished(view, url)
            startVM.setNetState(NetworkState.Completed(200))
        }
    }
}