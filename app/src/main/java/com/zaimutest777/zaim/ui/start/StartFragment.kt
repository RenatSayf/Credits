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
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.ConfirmViewModel
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.util.*


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

        RxBus.getConfig().value?.let { frc ->
            val checkLink = frc.getString("check_link")
            val userId = confirmVM.androidId
            val packageId = mActivity.packageName
            val getz = TimeZone.getDefault().id
            val getr = URLEncoder.encode("utm_source=google-play&utm_medium=organic", "UTF-8")
            val userAgent = System.getProperty("http.agent")
            userAgent?.let { agent ->
                //startVM.nextPath(agent, checkLink)
                startVM.getConfirm(userAgent, checkLink, packageId, userId, getz, getr)
            }
        }

        startVM.confirm.observe(viewLifecycleOwner, { r ->
            when(r.code())
            {
                200 ->
                {
                    mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_loansListFragment)
                }
                403 ->
                {
                    mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_confirmFragment)
                }
            }
        })

//        startVM.checkLink.observe(viewLifecycleOwner, { r ->
//            when(r.code())
//            {
//                403 ->
//                {
//                    val userConfirm = mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).getBoolean(Consts.USER_CONFIRM, false)
//                    when(userConfirm)
//                    {
//                        true -> mActivity.findNavController(R.id.nav_host_fragment). navigate(R.id.action_startFragment_to_loansListFragment)
//                        else -> mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_confirmFragment)
//                    }
//                }
//                200 ->
//                {
//                    mActivity.findNavController(R.id.nav_host_fragment). navigate(R.id.action_startFragment_to_loansListFragment)
//                }
//            }
//        })

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