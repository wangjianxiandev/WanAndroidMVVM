package com.wjx.android.wanandroidmvvm.base


import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kingja.loadsir.callback.SuccessCallback
import com.wjx.android.wanandroidmvvm.base.callback.EmptyCallBack
import com.wjx.android.wanandroidmvvm.base.callback.ErrorCallBack
import com.wjx.android.wanandroidmvvm.base.callback.LoadingCallBack
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.base.state.StateType
import com.wjx.android.wanandroidmvvm.base.utils.Util
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 16:36
 */

abstract class BaseLifeCycleFragment<VM : BaseViewModel<*>> : BaseFragment() {
    protected lateinit var mViewModel: VM

    override fun initView() {

        showLoading()

        mViewModel = ViewModelProviders.of(this).get(Util.getClass(this))

        mViewModel.loadState.observe(this, observer)

        initDataObserver()
    }

    abstract fun initDataObserver()

    private fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    private fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    private fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(ErrorCallBack::class.java)
    }

    open fun showTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showTip(it.message)
                    StateType.NETWORK_ERROR -> showError("网络异常")
                    StateType.TIP -> showTip(it.message)
                    StateType.EMPTY -> showEmpty()
                }
            }
        }
    }

    override fun reLoad() {
        showLoading()
        super.reLoad()
    }
}