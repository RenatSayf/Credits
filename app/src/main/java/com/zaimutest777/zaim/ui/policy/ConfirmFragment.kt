package com.zaimutest777.zaim.ui.policy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.ConfirmFragmentBinding
import com.zaimutest777.zaim.utils.RxBus

class ConfirmFragment : Fragment(R.layout.confirm_fragment)
{
    private lateinit var binding: ConfirmFragmentBinding


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
            it.title = getString(R.string.title_confirm_screen)
        }
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = ConfirmFragmentBinding.bind(view)

        RxBus.getConfig().value?.let { frc ->
            val privatepolicy = frc.getString("privatepolicy")
            println("*********************** $privatepolicy **********************************")
        }
    }

}