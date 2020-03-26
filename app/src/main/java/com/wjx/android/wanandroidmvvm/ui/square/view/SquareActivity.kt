package com.wjx.android.wanandroidmvvm.ui.square.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.square.viewmodel.SquareViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*

class SquareActivity : BaseArticleListActivity<SquareViewModel>() {

    private var mCurrentPage : Int = 0
    override fun initView() {
        super.initView()
        initHeaderView()
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 0
        mViewModel.loadSquareArticle(mCurrentPage)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSquareData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
    }


    override fun onRefreshData() {
        mCurrentPage = 0
        mViewModel.loadSquareArticle(mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadSquareArticle(++mCurrentPage)
    }

    private fun initHeaderView() {
        val headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "广场"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
    }

    override fun onBackPressed() = finish()

    override fun showDestroyReveal(): Boolean = true
}
