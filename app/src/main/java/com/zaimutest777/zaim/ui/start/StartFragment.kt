package com.zaimutest777.zaim.ui.start

import android.content.Context
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
import com.zaimutest777.zaim.utils.Consts
import com.zaimutest777.zaim.viewmodels.ConfirmViewModel
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StartFragment : Fragment(R.layout.start_fragment)
{
    private lateinit var binding: StartFragmentBinding
    private lateinit var startVM: StartViewModel
    private lateinit var confirmVM: ConfirmViewModel
    private lateinit var mActivity: MyInitialActivity

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mActivity = activity as MyInitialActivity
        mActivity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
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
        confirmVM = ViewModelProvider(this)[ConfirmViewModel::class.java]

        val serverCode = mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).getInt(Consts.SERVER_CODE, 0)
        val confirmCode = mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).getInt(Consts.CONFIRM_CODE, -1)
        when
        {
            serverCode == 0 -> mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_confirmFragment)
            serverCode == 200 && confirmCode > 0 -> mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_loansListFragment)
            serverCode == 403 || confirmCode < 0 -> mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_confirmFragment)
        }


    }



}