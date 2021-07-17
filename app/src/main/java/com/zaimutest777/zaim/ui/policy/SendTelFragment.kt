package com.zaimutest777.zaim.ui.policy

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.SendTelFragmentBinding
import com.zaimutest777.zaim.models.Data
import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.ui.notify.AppNotification
import com.zaimutest777.zaim.ui.notify.AppNotification.NOTIFICATION_ID
import com.zaimutest777.zaim.utils.Consts
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class SendTelFragment : Fragment(R.layout.send_tel_fragment)
{
    private lateinit var binding: SendTelFragmentBinding
    private lateinit var mActivity: MyInitialActivity
    private lateinit var netVM: StartViewModel
    private lateinit var appPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mActivity = activity as MyInitialActivity
        appPreferences = mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE)
        mActivity.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed()
            {
                mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_sendTelFragment_to_confirmFragment)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.title_send_tel)
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }
        return inflater.inflate(R.layout.send_tel_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = SendTelFragmentBinding.bind(view)
        netVM = ViewModelProvider(this)[StartViewModel::class.java]

        binding.telInputView.addTextChangedListener(object : PhoneNumberFormattingTextWatcher()
        {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                super.onTextChanged(s, start, before, count)
                val pureStr = s.toString().replace("-", "").replace(" ", "")
                netVM.telNumberIsValid.value = pureStr.length == 10
            }
        })

        binding.getCodeBtnView.setOnClickListener {
            val serverCode = appPreferences.getInt(Consts.SERVER_CODE, 0)
            val baseUrl = RxBus.getConfig().value?.getString(Consts.SHEET_LINCK)
            val prefix = binding.telNumberLayout.prefixText
            val phone = binding.telInputView.text.toString().replace("-", "").replace(" ", "")
            val phone1 = Phone(Data(prefix.toString().plus(phone)))
            if (serverCode == 200)
            {

                netVM.sendPhoneNumber(baseUrl, phone1)
            }
            else
            {
                netVM.sendPhoneNumber(null, phone1)
            }
        }

        netVM.phoneIsSent.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Completed ->
                {
                    when
                    {
                        state.code == 201 ->
                        {
                            val code = Random.nextInt(1000, 9999)
                            val notification = AppNotification.create(mActivity, getString(R.string.text_confirm_code), code.toString())
                            val notificationManager = mActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            notificationManager.notify(NOTIFICATION_ID, notification)
                            binding.confirmCodeView.setText(code.toString())
                            netVM.confirmCode.value = code.toString()
                            mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).edit().putInt(Consts.CONFIRM_CODE, code).apply()
                        }
                        state.code != 201 ->
                        {
                            mActivity.getSharedPreferences(Consts.APP_PREF, Context.MODE_PRIVATE).edit().putInt(Consts.CONFIRM_CODE, -1).apply()
                            netVM.confirmCode.value = ""
                            netVM.telNumberIsValid.value = false
                        }
                    }
                    binding.progressView.visibility = View.INVISIBLE
                }
                is NetworkState.Error ->
                {
                    binding.progressView.visibility = View.INVISIBLE
                    netVM.confirmCode.value = ""
                    netVM.telNumberIsValid.value = false
                }
                is NetworkState.Loading ->
                {
                    binding.progressView.visibility = View.VISIBLE
                }
                else -> {}
            }
        })

        binding.confirmBtnView.setOnClickListener {
            mActivity.findNavController(R.id.nav_host_fragment).navigate(R.id.action_sendTelFragment_to_loansListFragment)
        }

        netVM.confirmCode.observe(viewLifecycleOwner, { code ->
            binding.confirmBtnView.isEnabled = code.isNotEmpty()
        })

        netVM.telNumberIsValid.observe(viewLifecycleOwner, {
            binding.getCodeBtnView.isEnabled = it
            binding.confirmCodeView.isEnabled = it
        })



    }



}