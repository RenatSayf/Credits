package com.template.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.template.MyInitialActivity
import com.template.R
import com.template.databinding.SplashFragmentBinding
import com.template.models.RConfigViewModel
import com.template.utils.RxBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment)
{
    private lateinit var binding: SplashFragmentBinding
    private val rconfigVM: RConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        (requireActivity() as MyInitialActivity).supportActionBar!!.hide()
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = SplashFragmentBinding.bind(view)

        rconfigVM.remoteConfig.observe(viewLifecycleOwner, { frc ->
            frc?.let {
                val checkLink = it.getString("check_link")
                val sheetLink = it.getString("sheet_link")
                val cardsLink = it.getString("cards_link")

                RxBus.sendConfig(it)
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    (activity as MyInitialActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_privatePolicyFragment)
                }
            }
        })


    }

}