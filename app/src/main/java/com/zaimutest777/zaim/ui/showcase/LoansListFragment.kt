package com.zaimutest777.zaim.ui.showcase

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaimutest777.zaim.utils.NetworkState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.LoansListFragmentBinding
import com.zaimutest777.zaim.ui.adapters.ProductAdapter
import com.zaimutest777.zaim.viewmodels.DbJsonViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoansListFragment : Fragment(R.layout.loans_list_fragment)
{
    private lateinit var binding: LoansListFragmentBinding
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
            it.title = getString(R.string.title_loans)
            it.show()
        }
        return inflater.inflate(R.layout.loans_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = LoansListFragmentBinding.bind(view)

        dbJsonVM = ViewModelProvider(this)[DbJsonViewModel::class.java]

        dbJsonVM.loans.observe(viewLifecycleOwner, {
            it?.let { list ->
                binding.loansRecyclerView.apply {
                    val loansAdapter = ProductAdapter(list)
                    loansAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(mActivity)
                    adapter = loansAdapter
                }
            }
        })

        dbJsonVM.netState.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Completed ->
                {
                    binding.loadProgBar.visibility = View.INVISIBLE
                }
                is NetworkState.Error ->
                {
                    binding.loadProgBar.visibility = View.INVISIBLE
                }
                is NetworkState.Loading ->
                {
                    binding.loadProgBar.visibility = View.VISIBLE
                }
            }
        })


    }


}