package com.wjx.android.wanandroidmvvm.ui.system.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.system.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class SystemArticleListActivity : BaseArticleListActivity<SystemViewModel>() {
    private var mCurrentPageNum : Int = 0

    private lateinit var headView : View

    private val mTitle: String? by lazy { intent?.getStringExtra("title") }
    private val mCid: Int? by lazy { intent?.getIntExtra("id", 0) }


    override fun initView() {
        super.initView()
        initHeaderView()
        mAdapter.setOnItemChildClickListener{_,_,position ->
            UserInfo.instance.collect(this, position, this)
        }
    }

    private fun initHeaderView() {
        headView = View.inflate(this, R.layout.custom_bar, null)
        headView.detail_title.text = mTitle
        headView.detail_back.visibility = View.VISIBLE
        headView.detail_back.setOnClickListener { finish() }
        headView.detail_search.visibility = View.GONE
        headView.setBackgroundColor(Util.getColor(this))
        mAdapter.addHeaderView(headView)
    }

    override fun initData() {
        super.initData()
        mCurrentPageNum = 0
        mViewModel.loadSystemArticle(mCurrentPageNum, mCid)
    }
    override fun initDataObserver() {
        super.initDataObserver()
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

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        headView.setBackgroundColor(Util.getColor(this))
    }
}