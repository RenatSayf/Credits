package com.zaimutest777.zaim.ui.policy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.PrivatePolicyFragmentBinding

class PrivatePolicyFragment : Fragment(R.layout.private_policy_fragment)
{
    private lateinit var binding: PrivatePolicyFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.private_policy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = PrivatePolicyFragmentBinding.bind(view)
    }
}