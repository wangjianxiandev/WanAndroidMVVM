package com.wjx.android.wanandroidmvvm.ui.common.view

import android.graphics.Color
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.ui.common.data.Article
import com.wjx.android.wanandroidmvvm.ui.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.ui.common.adapter.ArticleAdapter
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.common.state.callback.LoginSuccessListener
import com.wjx.android.wanandroidmvvm.common.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.common.utils.*
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:59
 */
abstract class ArticleListFragment<VM : ArticleViewModel<*>> : BaseLifeCycleFragment<VM>(),
    LoginSuccessListener, CollectListener {

    private var mCollectState: Boolean = false

    private var mCurrentItem: Int = 0

    protected lateinit var mAdapter: ArticleAdapter

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initView() {
        super.initView()
        initRefresh()
        mRvArticle?.layoutManager = SpeedLayoutManager(context, 10f)
        mAdapter =
            ArticleAdapter(
                R.layout.article_item,
                null
            )
        mRvArticle?.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)

            article?.let {
                CommonUtil.startWebView(activity!!, it.link, it.title)
            }
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            UserInfo.instance.collect(activity!!, position, this)
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
        LoginSuccessState.addListener(this)
    }


    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(activity!!))
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

    fun addData(articleList: List<Article>) {
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
            if (mCollectState) mViewModel.unCollectCo(it.id) else mViewModel.collectCo(it.id)
        }
    }

    override fun loginSuccess(userName: String, userId: String, collectArticleIds: List<Int>?) {
        collectArticleIds?.let {
            it.forEach { id ->
                mAdapter.data.forEach { article ->
                    if (article.id == id) {
                        article.collect = true
                    }
                }
            }
        } ?: let {
            mAdapter.data.forEach { article ->
                article.collect = false
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListener(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        mSrlRefresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(activity!!))
        mAdapter.notifyDataSetChanged()
    }
}