package com.zaimutest777.zaim.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.SplashFragmentBinding
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.RConfigViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment)
{
    private lateinit var mActivity: MyInitialActivity
    private lateinit var binding: SplashFragmentBinding
    private val rconfigVM: RConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mActivity = activity as MyInitialActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mActivity.supportActionBar!!.hide()
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = SplashFragmentBinding.bind(view)

        rconfigVM.remoteConfig.observe(viewLifecycleOwner, { frc ->
            frc?.let {
                RxBus.sendConfig(it)
                val checkLink = frc.getString("check_link")
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    (activity as MyInitialActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_privatePolicyFragment)
                }
            }
        })


    }

}