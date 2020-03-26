package com.wjx.android.wanandroidmvvm.ui.meshare.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.meshare.viewmodel.MeShareViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*

class MeShareActivity : BaseArticleListActivity<MeShareViewModel>() {
    private var mCurrentPage: Int = 1

    override fun initView() {
        super.initView()
        initHeaderView()
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mMeShareData.observe(this, Observer { response ->
            response.let {
                addData(it.data.shareArticles.datas)
            }
        })
    }

    override fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadMeShareArticle(mCurrentPage)
    }

    override fun onLoadMoreData() {
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

    override fun onBackPressed() = finish()

    override fun showDestroyReveal(): Boolean = true
}
