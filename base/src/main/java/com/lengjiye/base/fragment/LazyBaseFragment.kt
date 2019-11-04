package com.lengjiye.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.lengjiye.base.viewmodel.BaseViewModel


/**
 * 懒加载基类
 *
 * 适用于非viewpager
 */
abstract class LazyBaseFragment<T : ViewDataBinding, VM : BaseViewModel> : BaseFragment<T, VM>() {
    // 是否已经加载过数据
    private var isDataLoaded = false
    // 记录当前fragment的是否隐藏 配合show()、hide()使用
    private var isHiddenToUser = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load()
    }

    /**
     * 请求加在数据
     */
    abstract fun loadData()


    /**
     * fragment再次可见时，是否重新请求数据，默认为false 只请求一次数据
     *
     * 需要重新请求  重写此方法  返回true
     */
    protected open fun isNeedReload(): Boolean {
        return false
    }

    private fun load() {
        // 第一次add时候不加载数据
        isHiddenToUser = false
        startLoadData()
    }

    /**
     * fragment 切换调用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHiddenToUser = hidden
        Log.e("LazyBaseFragment", "onHiddenChanged:$hidden")
        if (!hidden)
            startLoadData()
    }

    /**
     * 开始请求数据
     */
    private fun startLoadData() {
        Log.d("LazyBaseFragment", "startLoadData")
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded) && !isHiddenToUser) {
            Log.d("LazyBaseFragment", "loadData")
            loadData()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }

    /**
     * 父fragment是否隐藏
     *
     * 配合show()、hide()使用
     */
    private fun isParentHidden(): Boolean {
        val fragment = parentFragment
        if (fragment == null) {
            return false
        } else if (fragment is LazyBaseFragment<*, *> && !fragment.isHiddenToUser) {
            return false
        }
        return true
    }

    /**
     * 加载子fragment数据
     *
     * 配合show()、hide()使用
     */
    private fun dispatchParentHiddenState() {
        val fragmentManager = childFragmentManager
        val fragmentList = fragmentManager.fragments
        if (fragmentList.isEmpty()) return
        fragmentList.forEach {
            if (it is LazyBaseFragment<*, *> && !it.isHidden) {
                Log.d("LazyBaseFragment", "child startLoadData")
                it.startLoadData()
            }
        }
    }

    override fun onDestroy() {
        isDataLoaded = false
        isHiddenToUser = true
        super.onDestroy()
    }
}