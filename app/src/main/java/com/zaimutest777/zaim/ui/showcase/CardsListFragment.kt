package com.zaimutest777.zaim.ui.showcase

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.CardsListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardsListFragment : Fragment(R.layout.cards_list_fragment)
{
    private lateinit var binding: CardsListFragmentBinding
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        mActivity.findViewById<BottomNavigationView>(R.id.bottomNavView).apply {
            visibility = View.VISIBLE
        }
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.title_credits)
            it.show()
        }
        return inflater.inflate(R.layout.cards_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = CardsListFragmentBinding.bind(view)


    }



}