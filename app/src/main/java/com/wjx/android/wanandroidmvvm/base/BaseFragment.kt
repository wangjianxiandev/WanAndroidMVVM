package com.wjx.android.wanandroidmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.base.utils.Util

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 19:35
 */

abstract class BaseFragment : Fragment() {

    protected lateinit var loadService : LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), container, false)
        loadService = LoadSir.getDefault().register(rootView) {reLoad()}
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
    }

    abstract fun initView()

    open fun initData() {}

    // 重新加载
    open fun reLoad() {
        initData()
    }

    abstract fun getLayoutId(): Int
}