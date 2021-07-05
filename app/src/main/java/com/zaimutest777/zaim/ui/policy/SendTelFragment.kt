package com.zaimutest777.zaim.ui.policy

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.SendTelFragmentBinding

class SendTelFragment : Fragment(R.layout.send_tel_fragment)
{
    private lateinit var binding: SendTelFragmentBinding
    private lateinit var mActivity: MyInitialActivity

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

        binding.telInputView.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.telInputView.setOnFocusChangeListener { v, b ->
            val textView = v as TextInputEditText
            if (b && textView.text!!.isEmpty()) textView.setText("+7")
        }

    }



}