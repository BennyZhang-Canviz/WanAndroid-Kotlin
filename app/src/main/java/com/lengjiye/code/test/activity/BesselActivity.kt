package com.lengjiye.code.test.activity

import android.os.Bundle
import com.lengjiye.base.BaseActivity
import com.lengjiye.base.application.MasterApplication
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivityBesselBinding
import com.lengjiye.code.test.viewmode.BesselViewModel

/**
 * 贝塞尔曲线测试 demo
 */
open class BesselActivity : BaseActivity<ActivityBesselBinding, BesselViewModel>() {

    private var i: Int = 0

    override fun getViewModel(): BesselViewModel {
        return BesselViewModel(application)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bessel
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.tvText.text = "测试  测试  测试"
        mBinding.tvText.setOnClickListener {
            i++
            mBinding.tvText.text = "测试  测试  测试:$i applicationId: ${MasterApplication.getInstance().applicationId()}"
        }
    }

}



