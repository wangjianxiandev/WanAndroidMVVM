package com.wjx.android.wanandroidmvvm.ui.wechat.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R

import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.ui.wechat.adapter.WeChatTabAdapter
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.viewmodel.WeChatViewModel
import kotlinx.android.synthetic.main.layout_wechat.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class WeChatFragment : BaseLifeCycleFragment<WeChatViewModel>() {

    companion object {
        fun getInstance(): WeChatFragment? {
            return WeChatFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_wechat

    override fun initView() {
        super.initView()
        initColor()
    }

    private fun initColor() {
        wechat_tab.dividerColor = ColorUtil.getColor(activity!!)
        wechat_tab.indicatorColor = ColorUtil.getColor(activity!!)
    }

    override fun initData() {
        super.initData()
        mViewModel.loadWeChatTabName()
    }

    override fun initDataObserver() {
        mViewModel.mWChatTabData.observe(this, Observer { response ->
            response?.let{
                initWeChatArticleFragment(it)
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

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}