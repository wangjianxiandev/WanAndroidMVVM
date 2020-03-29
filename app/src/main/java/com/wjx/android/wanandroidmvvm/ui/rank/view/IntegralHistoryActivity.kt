package com.wjx.android.wanandroidmvvm.ui.rank.view

import android.graphics.Color

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.rank.adapter.IntegralHistoryAdapter
import com.wjx.android.wanandroidmvvm.ui.rank.data.IntegralHistoryResponse
import com.wjx.android.wanandroidmvvm.ui.rank.viewmodel.RankViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import kotlinx.android.synthetic.main.integral_history_item.*
import org.greenrobot.eventbus.Subscribe

class IntegralHistoryActivity : BaseLifeCycleActivity<RankViewModel>() {
    private var mCurrentPage: Int = 1
    private lateinit var headerView: View
    private lateinit var mAdapter: IntegralHistoryAdapter
    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        mAdapter = IntegralHistoryAdapter(R.layout.integral_history_item, null)
        initHeaderView()
        initRefresh()
        mRvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRvArticle.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadIntegralHistoryList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    override fun initDataObserver() {
        mViewModel.mIntegralHistoryListData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadIntegralHistoryList(mCurrentPage)
    }

    fun onLoadMoreData() {
        mViewModel.loadIntegralHistoryList(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "积分记录"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(Util.getColor(this))
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(Util.getColor(this))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(integralHistoryList: List<IntegralHistoryResponse>) {

        // 返回列表为空显示加载完毕
        if (integralHistoryList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(integralHistoryList)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(integralHistoryList)
        mAdapter.loadMoreComplete()
    }

    override fun showCreateReveal(): Boolean = false
    override fun showDestroyReveal(): Boolean = false
    override fun onBackPressed() = finish()


    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
        mSrlRefresh.setProgressBackgroundColorSchemeColor(Util.getColor(this))
    }
}
