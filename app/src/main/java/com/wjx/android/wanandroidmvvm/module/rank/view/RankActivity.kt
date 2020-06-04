package com.wjx.android.wanandroidmvvm.module.rank.view

import android.graphics.Color
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.module.rank.adapter.RankAdapter
import com.wjx.android.wanandroidmvvm.module.rank.model.IntegralResponse
import com.wjx.android.wanandroidmvvm.module.rank.viewmodel.RankViewModel
import kotlinx.android.synthetic.main.activity_rank.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_article_list.mRvArticle
import kotlinx.android.synthetic.main.fragment_article_list.mSrlRefresh
import org.greenrobot.eventbus.Subscribe

class RankActivity : BaseLifeCycleActivity<RankViewModel>() {
    private var mCurrentPage: Int = 1
    private lateinit var headerView: View
    private lateinit var mAdapter: RankAdapter
    override fun getLayoutId(): Int = R.layout.activity_rank

    override fun initView() {
        super.initView()
        mAdapter = RankAdapter(R.layout.rank_item, null)
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
        mViewModel.loadRankList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    override fun initDataObserver() {
        mViewModel.mRankListData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
        mViewModel.mMeRankInfo.observe(this, Observer { response ->
            response.let {
                integral_melevel.text = "等级：" + it.data.level.toString()
                integral_mename.text = "用户：" + it.data.username
                integral_mecount.text = "积分：" + it.data.coinCount.toString()
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadRankList(mCurrentPage)
        mViewModel.loadMeRankInfo()
    }

    fun onLoadMoreData() {
        mViewModel.loadRankList(++mCurrentPage)
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "积分排行"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.VISIBLE
            detail_search.setImageResource(R.drawable.ic_history)
            detail_search.setOnClickListener { onHistoryPressed() }
            detail_back.setOnClickListener { onBackPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
        integral_mecard.setCardBackgroundColor(ColorUtil.getColor(this))
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(integralList: List<IntegralResponse>) {

        // 返回列表为空显示加载完毕
        if (integralList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(integralList)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(integralList)
        mAdapter.loadMoreComplete()
    }

    override fun showDestroyReveal(): Boolean = true
    override fun onBackPressed() = finish()

    private fun onHistoryPressed() {
        startActivity<IntegralHistoryActivity>(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
    }

}
