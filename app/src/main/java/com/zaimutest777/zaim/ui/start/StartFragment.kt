package com.zaimutest777.zaim.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.StartFragmentBinding
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartFragment : Fragment(R.layout.start_fragment)
{
    private lateinit var binding: StartFragmentBinding
    private lateinit var startVM: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        (activity as MyInitialActivity).onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {
                requireActivity().finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        (activity as MyInitialActivity).supportActionBar?.let {
            it.title = getString(R.string.title_splash_screen)
            it.show()
        }
        return inflater.inflate(R.layout.start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = StartFragmentBinding.bind(view)
        startVM = ViewModelProvider(this)[StartViewModel::class.java]

        RxBus.getConfig().value?.let { frc ->
            val checkLink = frc.getString("check_link")
            startVM.nextPath(checkLink)
        }

        startVM.checkLink.observe(viewLifecycleOwner, { r ->
            when(r.code())
            {
                403 ->
                {
                    (activity as MyInitialActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_confirmFragment)
                }
                200 ->
                {

                }
            }
        })

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



}