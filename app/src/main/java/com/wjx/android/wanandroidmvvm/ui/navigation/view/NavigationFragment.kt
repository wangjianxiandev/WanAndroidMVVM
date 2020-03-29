package com.wjx.android.wanandroidmvvm.ui.navigation.view

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.navigation.adapter.NavigationLabelAdapter
import com.wjx.android.wanandroidmvvm.ui.navigation.adapter.NavigationTabAdapter
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.fragment_article_list.mSrlRefresh
import kotlinx.android.synthetic.main.layout_navigation.*
import org.greenrobot.eventbus.Subscribe


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class NavigationFragment : BaseLifeCycleFragment<NavigationViewModel>() {
    protected lateinit var mNavigationTabAdapter: NavigationTabAdapter

    protected lateinit var mNavigationLabelAdapter: NavigationLabelAdapter

    private lateinit var mNavigationLabelList: List<NavigationTabNameResponse>

    private val mNavigationTabNames = arrayListOf<String>()

    private lateinit var mLinearSnapHelper: LinearSnapHelper

    companion object {
        fun getInstance(): NavigationFragment? {
            return NavigationFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.layout_navigation

    override fun initDataObserver() {
        mViewModel.mNavigationTabData.observe(this, Observer { response ->
            response?.let {
                initNavigationTabData(it.data)
                initNavigationLabelData(it.data)
            }
        })
    }

    override fun initData() {
        mViewModel.loadNavigationTab()
    }

    override fun initView() {
        super.initView()
        initRefresh()
        nav_circle_recycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mNavigationTabAdapter = NavigationTabAdapter(R.layout.navigation_item, null)
        nav_circle_recycler.adapter = mNavigationTabAdapter

        mLinearSnapHelper = LinearSnapHelper()
        mLinearSnapHelper.attachToRecyclerView(nav_card_recycler)
        nav_card_recycler?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mNavigationLabelAdapter = NavigationLabelAdapter(R.layout.navigation_label_item, null)
        nav_card_recycler.adapter = mNavigationLabelAdapter

        mNavigationTabAdapter.setOnItemChildClickListener { _, _, position ->
            mNavigationTabAdapter.selectedPosition = position
            (nav_card_recycler.layoutManager as LinearLayoutManager).scrollToPosition(position)
            switchTab(position)
        }

        nav_card_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) { //获取右侧列表的第一个可见Item的position
                val nowPosition =
                    (nav_card_recycler.getLayoutManager() as LinearLayoutManager).findFirstVisibleItemPosition()
                (nav_circle_recycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    nowPosition,
                    0
                )
                switchTab(nowPosition)
            }
        })
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        nav_refresh.setProgressBackgroundColorSchemeColor(Util.getColor(activity!!))
        nav_refresh.setColorSchemeColors(Color.WHITE)
        nav_refresh.setOnRefreshListener { onRefreshData() }
    }

    private fun initNavigationTabData(navTabResponse: List<NavigationTabNameResponse>) {
        mNavigationLabelList = navTabResponse
        for (navTabResponseItem in navTabResponse) {
            mNavigationTabNames.add(navTabResponseItem.name)
        }
        // 初始化状态直接加载数据
        mNavigationTabAdapter.addData(mNavigationTabNames)
        switchTab(0)
    }

    private fun initNavigationLabelData(navTabResponse: List<NavigationTabNameResponse>) {
        mNavigationLabelAdapter.addData(navTabResponse)
    }


    // 切换 tab选择状态
    private fun switchTab(position: Int) {
        mNavigationTabAdapter.selectedPosition = position
        mNavigationTabAdapter.notifyDataSetChanged()
    }

    private fun onRefreshData() {
        mViewModel.loadNavigationTab()
        if (nav_refresh.isRefreshing) {
            nav_refresh.isRefreshing = false
        }
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        nav_refresh.setProgressBackgroundColorSchemeColor(Util.getColor(activity!!))
        mNavigationTabAdapter.notifyDataSetChanged()
        mNavigationLabelAdapter.notifyDataSetChanged()
    }
}