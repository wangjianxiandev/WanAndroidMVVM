package com.wjx.android.wanandroidmvvm.ui.collect.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.collect.viewmodel.CollectViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*

class CollectArticleListActivity : BaseArticleListActivity<CollectViewModel>() {
    private var mCurrentItem : Int = -1

    private var mCurrentPage = 0

    override fun initView() {
        super.initView()
        initHeaderView()
    }

    override fun initData() {
        super.initData()
        mCurrentPage = 0
        mViewModel.loadCollectArticle(mCurrentPage)
    }

    override fun initDataObserver() {
        mViewModel.mCollectArticleData.observe(this, Observer { response ->
            response?.let {
                for (article in it.data.datas) {
                    article.collect = true
                }
                addData(it.data.datas)
            }
        })

        mViewModel.mUnCollectData.observe(this, Observer { response ->
            response?.let {
                mAdapter.remove(mCurrentItem)
            }
        })
    }

    override fun onRefreshData() {
        mCurrentPage = 0
        mViewModel.loadCollectArticle(mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadCollectArticle(++mCurrentPage)
    }

    private fun initHeaderView() {
        val headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "收藏"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() = finish()

    override fun collect(position: Int) {
        val article = mAdapter.getItem(position)

        article?.let {
            mCurrentItem = position
            mViewModel.unCollect(it.id, it.originId)
        }
    }


}
