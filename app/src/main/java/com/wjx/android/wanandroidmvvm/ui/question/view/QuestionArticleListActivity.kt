package com.wjx.android.wanandroidmvvm.ui.question.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.question.viewModel.QuestionViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*

class QuestionArticleListActivity : BaseArticleListActivity<QuestionViewModel>() {
    private var mCurrentPage : Int = 0

    override fun initView() {
        super.initView()
        initHeaderView()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mQuestionData.observe(this, Observer { response ->
            response.let {
                addData(it.data.datas)
            }
        })
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 0
        mViewModel.loadQuestionList(mCurrentPage)
    }
    override fun onRefreshData() {
        mCurrentPage = 0
        mViewModel.loadQuestionList(mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadQuestionList(++mCurrentPage)
    }

    private fun initHeaderView() {
        val headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "每日一问"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() = finish()

}