package com.wjx.android.mvvm.view

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.wjx.android.mvvm.viewmodel.BaseViewModel
import com.wjx.android.mvvm.common.state.State
import com.wjx.android.mvvm.common.state.StateType

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 8:29
 */
abstract class BaseLifeCycleActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> :
    BaseActivity<VM, DB>() {

    override fun initView() {
        showSuccess()
        mViewModel.loadState.observe(this, observer)
        initDataObserver()
    }

    open fun initDataObserver() {}


    open fun showLoading() {}

    open fun showSuccess() {}

    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    open fun showEmpty() {}

    /**
     * 分发应用状态
     */
    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showError("网络出现问题啦")
                    StateType.NETWORK_ERROR -> showError("网络出现问题啦")
                    StateType.EMPTY -> showEmpty()
                }
            }
        }
    }
}