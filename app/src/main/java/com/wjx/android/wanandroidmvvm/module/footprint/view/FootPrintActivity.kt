package com.wjx.android.wanandroidmvvm.module.footprint.view

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.CommonUtil
import com.wjx.android.wanandroidmvvm.module.common.model.Article
import com.wjx.android.wanandroidmvvm.module.common.view.ArticleListActivity
import com.wjx.android.wanandroidmvvm.module.footprint.viewmodel.FootPrintViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

class FootPrintActivity : ArticleListActivity<FootPrintViewModel>() {

    private lateinit var headerView: View
    override fun initView() {
        super.initView()
        initHeaderView()
        showSuccess()
        showToast()
        mAdapter.setOnItemLongClickListener { _, _, position ->
            AlertDialog.Builder(this)
                .setMessage(R.string.clear_history)
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .setPositiveButton(R.string.done) { _, _ ->
                    mViewModel.deleteFootPrint(mAdapter.getItem(position) as Article)
                    mAdapter.remove(position)
                }.show()
            true
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)
            article?.let {
                mViewModel.addFootPrint(article)
                CommonUtil.startWebView(this, it.link, it.title)
            }
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.loadFootPrint()
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mFootPrintData.observe(this, Observer { response ->
            response?.let {
                addData(it)
            }
        })
    }

    override fun onRefreshData() {
        mViewModel.loadFootPrint()
    }

    override fun onLoadMoreData() {
        mAdapter.loadMoreEnd()
    }

    private fun initHeaderView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.apply {
            detail_title.text = "足迹"
            detail_back.visibility = View.VISIBLE
            detail_back.setOnClickListener { onBackPressed() }
            detail_search.visibility = View.VISIBLE
            detail_search.setImageResource(R.drawable.ic_clear_all)
            detail_search.setOnClickListener { onClearAllPressed() }
        }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(ColorUtil.getColor(this))
    }

    private fun showToast() {
        toast(getString(R.string.long_to_delete))
    }

    private fun onClearAllPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.clear_all)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.done) { _, _ ->
                mViewModel.deleteAll()
            }.show()
    }

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    @Subscribe
    override fun settingEvent(event: ChangeThemeEvent) {
        super.settingEvent(event)
        initColor()
    }
}
