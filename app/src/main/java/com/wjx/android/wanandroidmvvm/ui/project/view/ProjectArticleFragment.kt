package com.wjx.android.wanandroidmvvm.ui.project.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.base.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import com.wjx.android.wanandroidmvvm.ui.project.adapter.ProjectArticleAdapter
import com.wjx.android.wanandroidmvvm.ui.project.data.Project
import com.wjx.android.wanandroidmvvm.ui.project.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 16:42
 */
class ProjectArticleFragment : BaseLifeCycleFragment<ProjectViewModel>() , LoginSuccessListener,
    CollectListener {
    private var mCurrentPage = 1

    private var mCurrentItem : Int = 0

    private var mCollectState : Boolean = false

    private lateinit var mAdapter: ProjectArticleAdapter

    private lateinit var mLinearSnapHelper : LinearSnapHelper

    private val mCid: Int by lazy { arguments?.getInt("id") ?: -1 }

    companion object {
        fun newInstance(id : Int) : Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = ProjectArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        initRefresh()
        mRvArticle?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mLinearSnapHelper = LinearSnapHelper()
        mLinearSnapHelper.attachToRecyclerView(mRvArticle)
        mAdapter = ProjectArticleAdapter(R.layout.project_item, null)
        mRvArticle?.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val article = mAdapter.getItem(position)

            article?.let {
                val intent : Intent = Intent(activity, ArticleDetailActivity::class.java)
                intent.putExtra("url", it.link)
                intent.putExtra("title", it.title)
                startActivity(intent)
            }
        }
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({ onLoadMoreData() }, mRvArticle)
        LoginSuccessState.addListener(this)
    }

    override fun initData() {
        super.initData()
        mViewModel.loadProjectArticle(mCurrentPage, mCid)
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        mSrlRefresh.setColorSchemeResources(R.color.colorPrimary)
        mSrlRefresh.setOnRefreshListener { onRefreshData() }
    }

    override fun initDataObserver() {
        mViewModel.mProjectArticleData.observe(this, Observer { reponse ->
            reponse?.let {
                setProjectArticle(it.data.datas)
            }
        })
        mViewModel.mCollectData.observe(this, Observer {
            var article = mAdapter.getItem(mCurrentItem)
            article?.let {
                it.isCollect = !mCollectState
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun collect(position: Int) {
        var article = mAdapter.getItem(position)

        article?.let {
            mCurrentItem = position
            mCollectState = it.isCollect
            if (mCollectState) mViewModel.unCollect(it.id) else mViewModel.collect(it.id)
        }
    }

    override fun loginSuccess(userName: String, collectArticleIds: List<Int>?) {
        collectArticleIds?.let {
            it.forEach { id ->
                mAdapter.data.forEach { article ->
                    if (article.id == id) {
                        article.isCollect = true
                    }
                }
            }
        } ?: let {
            mAdapter.data.forEach { article ->
                if (article.id == id) {
                    article.isCollect = false
                }
            }
        }
        mAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        LoginSuccessState.removeListsner(this)
    }

    fun onRefreshData() {
        mCurrentPage = 1
        mViewModel.loadProjectArticle(mCurrentPage, mCid)
    }

     fun onLoadMoreData() {
        mViewModel.loadProjectArticle(++mCurrentPage, mCid)
    }

    private fun setProjectArticle(articleList : List<Article>) {

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
}