package com.zaimutest777.zaim.ui.showcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.ui.adapters.ProductAdapter
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.viewmodels.DbJsonViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardsListFragment : Fragment()
{
    private lateinit var mActivity: MyInitialActivity
    private lateinit var dbJsonVM: DbJsonViewModel


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
            it.title = getString(R.string.title_cards)
            it.show()
        }
        return inflater.inflate(R.layout.cards_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        dbJsonVM = ViewModelProvider(this)[DbJsonViewModel::class.java]

        val cardsRecyclerView = view.findViewById<RecyclerView>(R.id.cardsRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(mActivity)
        }

        val tabLayoutView = view.findViewById<TabLayout>(R.id.tabLayoutView)
        tabLayoutView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                when(tab!!.text)
                {
                    mActivity.getString(R.string.text_credit) ->
                    {
                        val list = dbJsonVM.creditCards.value
                        list?.let {
                            val productAdapter = ProductAdapter(mActivity, it).apply {
                                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                            }
                            cardsRecyclerView.adapter = productAdapter
                        }
                    }
                    mActivity.getString(R.string.text_debit) ->
                    {
                        val list = dbJsonVM.debitCards.value
                        list?.let {
                            val productAdapter = ProductAdapter(mActivity, it).apply {
                                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                            }
                            cardsRecyclerView.adapter = productAdapter
                        }
                    }
                    mActivity.getString(R.string.text_rassrochka) ->
                    {
                        val list = dbJsonVM.rasrochka.value
                        list?.let {
                            val productAdapter = ProductAdapter(mActivity, it).apply {
                                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                            }
                            cardsRecyclerView.adapter = productAdapter
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?){}

            override fun onTabReselected(tab: TabLayout.Tab?){}
        })

        dbJsonVM.creditCards.observe(viewLifecycleOwner, { list ->
            list?.let {
                val productAdapter = ProductAdapter(mActivity, it).apply {
                    stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
                cardsRecyclerView.adapter = productAdapter
            }
        })

        val loadProgBar = view.findViewById<ProgressBar>(R.id.loadProgBar)
        dbJsonVM.netState.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Completed ->
                {
                    view.findViewById<ConstraintLayout>(R.id.cardsLayout)?.setBackgroundColor(resources.getColor(R.color.gray))
                    loadProgBar.visibility = View.INVISIBLE
                }
                is NetworkState.Error ->
                {
                    loadProgBar.visibility = View.INVISIBLE
                }
                is NetworkState.Loading ->
                {
                    loadProgBar.visibility = View.VISIBLE
                }
            }
        })


    }



}