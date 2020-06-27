package com.wjx.android.wanandroidmvvm.module.system.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.module.common.view.ArticleListActivity
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.CommonUtil
import com.wjx.android.wanandroidmvvm.module.system.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class SystemArticleListActivity : ArticleListActivity<SystemViewModel>() {
    private var mCurrentPageNum : Int = 0

    private lateinit var headView : View

    private val mTitle: String? by lazy { intent?.getStringExtra("title") }
    private val mCid: Int? by lazy { intent?.getIntExtra("id", 0) }


    override fun initView() {
        super.initView()
        initHeaderView()
        mAdapter.setOnItemChildClickListener{_,_,position ->
            CommonUtil.Vibrate(this, 50)
            UserInfo.instance.collect(this, position, this)
        }
    }

    private fun initHeaderView() {
        headView = View.inflate(this, R.layout.custom_bar, null)
        headView.apply {
            detail_title.text = mTitle
            detail_back.visibility = View.VISIBLE
            detail_back.setOnClickListener { finish() }
            detail_search.visibility = View.GONE
            setBackgroundColor(ColorUtil.getColor(this@SystemArticleListActivity))
        }
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
            response?.let { addData(it.datas) }
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
        headView.setBackgroundColor(ColorUtil.getColor(this))
    }
}