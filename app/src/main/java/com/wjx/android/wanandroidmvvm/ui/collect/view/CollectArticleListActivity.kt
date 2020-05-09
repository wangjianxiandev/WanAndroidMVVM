package com.wjx.android.wanandroidmvvm.ui.collect.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.ui.common.view.ArticleListActivity
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.ui.collect.viewmodel.CollectViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class CollectArticleListActivity : ArticleListActivity<CollectViewModel>() {
    private var mCurrentItem: Int = -1

    private var mCurrentPage = 0

    private lateinit var headerView: View

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
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "收藏"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.VISIBLE
            detail_search.setImageResource(R.drawable.ic_add)
            detail_search.setOnClickListener { onAddCollectArticle() }
            detail_back.setOnClickListener { onBackPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
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

    private fun onAddCollectArticle() {
        startActivity<AddCollectActivity>(this)
    }

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}
