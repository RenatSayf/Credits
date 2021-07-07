package com.zaimutest777.zaim.ui.policy

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.SendTelFragmentBinding
import com.zaimutest777.zaim.models.Data
import com.zaimutest777.zaim.models.Phone
import com.zaimutest777.zaim.utils.Consts
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.utils.RxBus
import com.zaimutest777.zaim.viewmodels.StartViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


@AndroidEntryPoint
class SendTelFragment : Fragment(R.layout.send_tel_fragment)
{
    private lateinit var binding: SendTelFragmentBinding
    private lateinit var mActivity: MyInitialActivity
    private lateinit var netVM: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mActivity = activity as MyInitialActivity

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
                println("******************* count = $count ***********************")
            }
        })
        binding.telInputView.setOnFocusChangeListener { v, b ->
            val textView = v as TextInputEditText
            if (b && textView.text!!.isEmpty()) textView.setText("+7")
        }

        binding.getCodeBtnView.setOnClickListener {
            val baseUrl = RxBus.getConfig().value?.getString(Consts.SHEET_LINCK)
            val phone = binding.telInputView.text.toString().replace("-", "").replace(" ", "")
            baseUrl?.let {

                val phone1 = Phone(Data(phone))
                val json = Gson().toJson(phone1, Phone::class.java)
                netVM.sendPhoneNumber(baseUrl, json)
            }
        }

        netVM.phoneIsSent.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Completed ->
                {
                    println("********************** response code = ${state.code} *********************************")
                }
                is NetworkState.Error ->
                {

                }
                is NetworkState.Loading ->
                {

                }
            }
        })

    }



}