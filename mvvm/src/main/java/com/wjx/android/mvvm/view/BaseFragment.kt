package com.wjx.android.mvvm.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wjx.android.mvvm.common.utils.CommonUtil
import com.wjx.android.mvvm.viewmodel.BaseViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 8:28
 */
abstract class BaseFragment<VM : BaseViewModel<*>, DB : ViewDataBinding> : Fragment() {
    protected lateinit var mViewModel: VM

    protected lateinit var mDataBinding: DB

    open fun initView() {}

    open fun initData() {}

    open fun reLoad() = initData()

    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(CommonUtil.getClass(this))
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mDataBinding.lifecycleOwner = this
        return mDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
    }
}