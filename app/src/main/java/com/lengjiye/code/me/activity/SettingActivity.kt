package com.lengjiye.code.me.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lengjiye.base.BaseActivity
import com.lengjiye.code.R
import com.lengjiye.code.databinding.ActivitySettingBinding
import com.lengjiye.code.login.viewmodel.LoginViewModel
import com.lengjiye.code.utils.ToolBarUtil
import com.lengjiye.code.utils.toast
import com.lengjiye.tools.ResTool

class SettingActivity : BaseActivity<ActivitySettingBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun bindViewModel() {
        mBinding.viewModel = mViewModel
    }

    override fun getViewModel(): LoginViewModel {
        return LoginViewModel(application)
    }

    override fun initToolBar() {
        super.initToolBar()
        ToolBarUtil.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtil.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp)
            .setNormalTitle(R.string.s_27).setNormalTitleColor(R.color.c_ff).setBackListener {
                finish()
            }.builder()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.hnLogout.setOnClickListener {
            mViewModel.logout(this)
        }
    }

    override fun initLiveDataListener() {
        super.initLiveDataListener()
        mViewModel.logoutSuc.observe(this, Observer {
            if (it) {
                ResTool.getString(R.string.s_29).toast()
                finish()
            }
        })
    }
}