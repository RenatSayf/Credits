package com.template.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.template.MyInitialActivity
import com.template.R
import com.template.databinding.PrivatePolicyFragmentBinding
import com.template.utils.RxBus


class PrivatePolicyFragment : Fragment(R.layout.private_policy_fragment)
{
    private lateinit var binding: PrivatePolicyFragmentBinding
    private val policyVM: PrivatePolicyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {

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
        return inflater.inflate(R.layout.private_policy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = PrivatePolicyFragmentBinding.bind(view)

        RxBus.getConfig().value?.let { frc ->
            val privatepolicy = frc.getString("privatepolicy")
            println("***************** $privatepolicy ************************************")
        }
    }



}