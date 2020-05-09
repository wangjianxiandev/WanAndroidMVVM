package com.wjx.android.wanandroidmvvm.module.square.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.module.common.view.ArticleListActivity
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.module.square.viewmodel.SquareViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class SquareActivity : ArticleListActivity<SquareViewModel>() {

    private var mCurrentPage: Int = 0
    private lateinit var headerView: View
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
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "广场"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.VISIBLE
            detail_search.setImageResource(R.drawable.ic_share_square)
            detail_back.setOnClickListener { onBackPressed() }
            detail_search.setOnClickListener { onShareArticle() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun onBackPressed() = finish()

    override fun showDestroyReveal(): Boolean = true

    private fun onShareArticle() {
        UserInfo.instance.startAddShareActivity(this)
    }

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}
