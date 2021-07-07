package com.zaimutest777.zaim.ui.showcase

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaimutest777.zaim.R

class LoansListFragment : Fragment(R.layout.loans_list_fragment)
{



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.loans_list_fragment, container, false)
    }


}