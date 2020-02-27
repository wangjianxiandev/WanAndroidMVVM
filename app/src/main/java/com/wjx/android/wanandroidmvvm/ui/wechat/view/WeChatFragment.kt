package com.wjx.android.wanandroidmvvm.ui.wechat.view

import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R

import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.ui.wechat.adapter.WeChatTabAdapter
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.viewmodel.WeChatViewModel
import kotlinx.android.synthetic.main.layout_wechat.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class WeChatFragment : BaseLifeCycleFragment<WeChatViewModel>() {

    override fun getLayoutId(): Int = R.layout.layout_wechat

    override fun initView() {
        super.initView()
        initStatusColor()
    }

    override fun initData() {
        super.initData()
        mViewModel.loadWeChatTabName()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initStatusColor()
    }

    private fun initStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity!!.window.statusBarColor = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) { // 设置状态栏中字体的颜色为黑色
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else { // 跟随系统
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    override fun initDataObserver() {
        mViewModel.mWChatTabData.observe(this, Observer { response ->
            response?.let{
                initWeChatArticleFragment(it.data)
            }
        })
    }

    private fun initWeChatArticleFragment(dataList : List<WeChatTabNameResponse>) {
        val tabs = arrayListOf<String>()

        val fragments = arrayListOf<Fragment>()

        for (data in dataList) {
            tabs.add(data.name)
            fragments.add(WeChatArticleFragment.newInstance(data.id))
        }
        wechat_viewpager.adapter = WeChatTabAdapter(childFragmentManager, tabs, fragments)
        wechat_tab.setViewPager(wechat_viewpager)
    }
}