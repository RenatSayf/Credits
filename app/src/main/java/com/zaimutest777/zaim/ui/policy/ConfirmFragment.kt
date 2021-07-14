package com.zaimutest777.zaim.ui.policy

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
import com.zaimutest777.zaim.databinding.ConfirmFragmentBinding
import com.zaimutest777.zaim.utils.Consts
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.ConfirmViewModel
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.util.*


@AndroidEntryPoint
class ConfirmFragment : Fragment(R.layout.confirm_fragment)
{
    private lateinit var binding: ConfirmFragmentBinding
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
                mActivity.finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.title_confirm_screen)
            it.setDisplayHomeAsUpEnabled(false)
        }
        return inflater.inflate(R.layout.confirm_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = ConfirmFragmentBinding.bind(view)
        startVM = ViewModelProvider(this)[StartViewModel::class.java]
        confirmVM = ViewModelProvider(this)[ConfirmViewModel::class.java]

        mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).getInt(Consts.SERVER_CODE, 0).apply {
            if (this == 403)
            {
                binding.apply {
                    confirmCheckBox.apply {
                        isChecked = false
                        isEnabled = false
                    }

                }
                binding.confirmCheckBox.isEnabled = false
                confirmVM.acceptTheAgreement(false)
            }
        }

        confirmVM.agreed.observe(viewLifecycleOwner, {
            binding.commitBtnView.isEnabled = it
        })

        binding.confirmCheckBox.setOnCheckedChangeListener { _, p1 ->
            confirmVM.acceptTheAgreement(p1)
        }

        binding.linkView.setOnClickListener {
            mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_confirmFragment_to_privatePolicyFragment)
        }

        binding.commitBtnView.setOnClickListener {
            val checkLink = RxBus.getConfig().value?.getString(Consts.CHECK_LINK)
            val userId = confirmVM.androidId
            val packageId = mActivity.packageName
            val getz = TimeZone.getDefault().id
            val userAgent = System.getProperty("http.agent")
            val getr = URLEncoder.encode("utm_source=google-play&utm_medium=organic", "UTF-8")
            if (checkLink != null && userAgent != null)
            {
                startVM.getConfirm(userAgent, checkLink, packageId, userId, getz, getr)
            }
            else
            {
                confirmVM.acceptTheAgreement(false)
            }
        }

        startVM.confirm.observe(viewLifecycleOwner, { r ->
            if (r.code() == 200)
            {
                //mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).edit().putInt(Consts.SERVER_CODE, r.code()).apply()
                mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_confirmFragment_to_sendTelFragment)
            }
//            else if (r.code() == 403 || r.code() == 1020)
//            {
//                mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).edit().putInt(Consts.SERVER_CODE, r.code()).apply()
//                mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_confirmFragment_to_sendTelFragment)
//            }
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