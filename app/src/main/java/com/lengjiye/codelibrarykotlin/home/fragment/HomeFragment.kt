package com.lengjiye.codelibrarykotlin.home.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.lengjiye.base.BaseFragment
import com.lengjiye.codelibrarykotlin.R
import com.lengjiye.codelibrarykotlin.databinding.FragmentHomeBinding
import com.lengjiye.codelibrarykotlin.home.adapter.HomeFragmentAdapter
import com.lengjiye.codelibrarykotlin.home.viewmodel.HomeViewMode

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewMode>() {

    private val adapter by lazy { HomeFragmentAdapter(getBaseActivity(), null) }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewMode {
        return HomeViewMode(getBaseActivity().application)
    }

    override fun bindViewModel() {
        getBinding().viewModel = mViewModel
    }

    override fun getBinding(): FragmentHomeBinding {
        return mBinding
    }

    override fun initData() {
        super.initData()
        getBinding().rlList.layoutManager = LinearLayoutManager(getBaseActivity())
        getBinding().rlList.adapter = adapter

        test()
    }

    private fun test() {
        mViewModel.getHomeData(this, 0)
    }

}