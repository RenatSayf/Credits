package com.template.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.template.MyInitialActivity
import com.template.R
import com.template.databinding.StartFragmentBinding
import com.template.utils.RxBus
import com.template.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartFragment : Fragment(R.layout.start_fragment)
{
    private lateinit var binding: StartFragmentBinding
    private lateinit var startVM: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
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
            it.title = getString(R.string.title_private_policy)
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
            val privatepolicy = frc.getString("privatepolicy")
            val checkLink = frc.getString("check_link")
            startVM.nextPath(checkLink)
            println("***************** $privatepolicy ************************************")
        }
    }



}