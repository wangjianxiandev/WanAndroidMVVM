package com.wjx.android.wanandroidmvvm.ui.meshare.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.utils.SpeedLayoutManager
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import com.wjx.android.wanandroidmvvm.ui.meshare.adapter.MeShareAdapter
import com.wjx.android.wanandroidmvvm.ui.meshare.viewmodel.MeShareViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*

class MeShareActivity : BaseLifeCycleActivity<MeShareViewModel>() {
    private var mCurrentPage: Int = 1

    private lateinit var mAdapter: MeShareAdapter

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        mAdapter = MeShareAdapter(R.layout.article_item, null)
        initHeaderView()
        initRefresh()
        mRvArticle?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRvArticle.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)
            article?.let {
                val intent: Intent = Intent(this, ArticleDetailActivity::class.java)
                intent.putExtra("url", it.link)
                intent.putExtra("title", it.title)
                startActivity(intent)
            }
        }

        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.deleteMeShareArticle(mAdapter.getItem(position)!!.id)
            mAdapter.remove(position)
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    override fun initDataObserver() {
        mViewModel.mMeShareData.observe(this, Observer { response ->
            response.let {
                addData(it.data.shareArticles.datas)
            }
        })
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    fun onLoadMoreData() {
        mViewModel.loadMeShareArticle(++mCurrentPage)
    }

    private fun initHeaderView() {
        val headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "我的分享"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    fun addData(articleList: List<Article>) {

        // 返回列表为空显示加载完毕
        if (articleList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(articleList)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(articleList)
        mAdapter.loadMoreComplete()
    }

    override fun onBackPressed() = finish()
}
