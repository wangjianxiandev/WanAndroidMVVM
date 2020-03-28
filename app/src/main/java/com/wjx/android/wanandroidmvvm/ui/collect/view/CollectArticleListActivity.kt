package com.wjx.android.wanandroidmvvm.ui.collect.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.collect.viewmodel.CollectViewModel
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.custom_bar.view.custom_bar
import org.greenrobot.eventbus.Subscribe

class CollectArticleListActivity : BaseArticleListActivity<CollectViewModel>() {
    private var mCurrentItem: Int = -1

    private var mCurrentPage = 0

    private lateinit var headerView : View

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
        headerView.detail_title.text = "收藏"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.VISIBLE
        headerView.detail_search.setImageResource(R.drawable.ic_add)
        headerView.detail_search.setOnClickListener { onAddCollectArticle() }
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(Util.getColor(this))
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
        val intent = Intent(this, AddCollectActivity::class.java)
        startActivity(intent)
    }

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}
