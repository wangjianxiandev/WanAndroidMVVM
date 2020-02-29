package com.wjx.android.wanandroidmvvm.ui.navigation.view

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import com.wjx.android.wanandroidmvvm.ui.navigation.adapter.NavigationLabelAdapter
import com.wjx.android.wanandroidmvvm.ui.navigation.adapter.NavigationTabAdapter
import com.wjx.android.wanandroidmvvm.ui.navigation.data.NavigationTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.navigation.viewmodel.NavigationViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.layout_navigation.*
import kotlinx.android.synthetic.main.navigation_label_item.*
import kotlinx.android.synthetic.main.navigation_label_item.view.*


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

    private lateinit var mLinearSnapHelper : LinearSnapHelper

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
                (nav_circle_recycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(nowPosition, 0)
                switchTab(nowPosition)
            }
        })
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


    // Reyclerview移动到中间位置的方法
    fun moveToMiddle(
        recyclerView: RecyclerView,
        position: Int
    ) { //先从RecyclerView的LayoutManager中获取当前第一项和最后一项的Position
        val firstItem =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        val lastItem =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
        //中间位置
        val middle = (firstItem + lastItem) / 2
        // 取绝对值，index下标是当前的位置和中间位置的差，下标为index的view的top就是需要滑动的距离
        val index =
            if (position - middle >= 0) position - middle else -(position - middle)
        //左侧列表一共有getChildCount个Item，如果>这个值会返回null，程序崩溃，如果>getChildCount直接滑到指定位置,或者,都一样啦
        if (index >= recyclerView.childCount) {
            recyclerView.scrollToPosition(position)
        } else { //如果当前位置在中间位置上面，往下移动，这里为了防止越界
            if (position < middle) {
                recyclerView.scrollBy(0, -recyclerView.getChildAt(index).top)
                // 在中间位置的下面，往上移动
            } else {
                recyclerView.scrollBy(0, recyclerView.getChildAt(index).top)
            }
        }
    }
}