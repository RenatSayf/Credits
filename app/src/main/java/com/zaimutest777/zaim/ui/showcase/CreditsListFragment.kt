package com.zaimutest777.zaim.ui.showcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zaimutest777.zaim.MyInitialActivity
import com.zaimutest777.zaim.R
import com.zaimutest777.zaim.databinding.LoansListFragmentBinding
import com.zaimutest777.zaim.ui.adapters.ProductAdapter
import com.zaimutest777.zaim.utils.NetworkState
import com.zaimutest777.zaim.viewmodels.DbJsonViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreditsListFragment : Fragment(R.layout.loans_list_fragment)
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

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        mActivity.findViewById<BottomNavigationView>(R.id.bottomNavView).apply {
            visibility = View.VISIBLE
        }
        mActivity.supportActionBar?.let {
            it.title = getString(R.string.title_credits)
            it.show()
        }
        return inflater.inflate(R.layout.loans_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = LoansListFragmentBinding.bind(view)

        dbJsonVM = ViewModelProvider(this)[DbJsonViewModel::class.java]

        dbJsonVM.credits.observe(viewLifecycleOwner, {
            it?.let { list ->
                binding.productRecyclerView.apply {
                    val productAdapter = ProductAdapter(mActivity, list)
                    productAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(mActivity)
                    adapter = productAdapter
                }
            }
        })

        dbJsonVM.netState.observe(viewLifecycleOwner, { state ->
            when(state)
            {
                is NetworkState.Completed ->
                {
                    binding.loansLayout.setBackgroundColor(resources.getColor(R.color.gray))
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