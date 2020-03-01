package com.wjx.android.wanandroidmvvm.ui.system.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleAdapter
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.ui.system.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import java.lang.ArithmeticException

class SystemListActivity : BaseArticleListActivity<SystemViewModel>() {
    private var mCurrentPageNum : Int = 0

    private val mTitle: String? by lazy { intent?.getStringExtra("title") }
    private val mCid: Int? by lazy { intent?.getIntExtra("id", 0) }


    override fun initView() {
        super.initView()
        initHeaderView()
    }

    private fun initHeaderView() {
        val headView = View.inflate(this, R.layout.custom_bar, null)

        headView.detail_title.text = mTitle
        headView.detail_back.visibility = View.VISIBLE
        headView.detail_back.setOnClickListener { finish() }
        headView.detail_search.visibility = View.GONE
        mAdapter.addHeaderView(headView)
    }

    override fun initData() {
        super.initData()
        mCurrentPageNum = 0
        mViewModel.loadSystemArticle(mCurrentPageNum, mCid)
    }
    override fun initDataObserver() {
        mViewModel.mSystemArticleData.observe(this, Observer { response ->
            response?.let { addData(it.data.datas) }
        })
    }

    override fun onRefreshData() {
        mCurrentPageNum = 0
        mViewModel.loadSystemArticle(mCurrentPageNum, mCid)
    }

    override fun onLoadMoreData() {
        mViewModel.loadSystemArticle(++mCurrentPageNum, mCid)
    }

}