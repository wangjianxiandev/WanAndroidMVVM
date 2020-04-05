package com.wjx.android.wanandroidmvvm.base.BaseArticle

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.base.utils.SpeedLayoutManager
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 14:00
 */
abstract class BaseArticleListActivity <VM : BaseArticleViewModel<*>> : BaseLifeCycleActivity<VM>(), CollectListener {
    private var mCollectState : Boolean = false

    private var mCurrentItem : Int = 0

    protected lateinit var mAdapter : BaseArticleAdapter

    protected var isLoadMore: Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        initRefresh()
        mRvArticle?.layoutManager = SpeedLayoutManager(this, 10f)
        mAdapter = BaseArticleAdapter(R.layout.article_item, null)
        mRvArticle?.adapter  = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)

            article?.let {
                val intent : Intent = Intent(this, ArticleDetailActivity::class.java)
                intent.putExtra("url", it.link)
                intent.putExtra("title", it.title)
                startActivity(intent)
            }
        }

        mAdapter.setOnItemChildClickListener{_, _, position ->
            UserInfo.instance.collect(this, position, this)
        }
        mAdapter.setEnableLoadMore(isLoadMore)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
            mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
            mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    /**
     * 下拉刷新
     */
    abstract fun onRefreshData()

    /**
     * 加载更多数据
     */
    abstract fun onLoadMoreData()

    fun addData(articleList : List<Article>) {

        // 返回列表为空显示加载完毕
        if (articleList.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
            mAdapter.setNewData(articleList)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(articleList)
        mAdapter.loadMoreComplete()
    }

    override fun initDataObserver() {
        mViewModel.mCollectData.observe(this, Observer {
            var article = mAdapter.getItem(mCurrentItem)
            article?.let {
                it.collect = !mCollectState
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun collect(position: Int) {
        var article = mAdapter.getItem(position)

        article?.let {
            mCurrentItem = position
            mCollectState = it.collect
            if (mCollectState) mViewModel.unCollect(it.id) else mViewModel.collect(it.id)
        }
    }

    override fun reLoad() {
        showLoading()
        onRefreshData()
        super.reLoad()
    }

    @Subscribe
    open fun settingEvent(event: ChangeThemeEvent) {
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(this))
        mAdapter.notifyDataSetChanged()
    }
}