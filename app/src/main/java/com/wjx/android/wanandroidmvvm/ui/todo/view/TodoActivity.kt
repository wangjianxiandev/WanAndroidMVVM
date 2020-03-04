package com.wjx.android.wanandroidmvvm.ui.todo.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.ui.todo.adapter.TodoAdapter
import com.wjx.android.wanandroidmvvm.ui.todo.data.TodoResponse
import com.wjx.android.wanandroidmvvm.ui.todo.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.custom_bar.view.*

class TodoActivity : BaseLifeCycleActivity<TodoViewModel>() {

    private lateinit var mAdapter: TodoAdapter

    private var mCurrentPageNum: Int = 1

    override fun initDataObserver() {
        mViewModel.mTodoListData.observe(this, Observer { response ->
            response?.let {
                setTodoList(it.data.datas)
            }
        })
    }

    override fun initData() {
        mViewModel.loadTodoList(mCurrentPageNum)
    }

    override fun showDestroyReveal(): Boolean = true

    override fun initView() {
        super.initView()
        mAdapter = TodoAdapter(R.layout.todo_item, null)
        initHeadView()
        initRefresh()
        recycler_view?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recycler_view?.adapter = mAdapter
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener(
            { mViewModel.loadTodoList(++mCurrentPageNum) },
            recycler_view
        )
    }

    override fun getLayoutId(): Int = R.layout.activity_todo

    private fun initHeadView() {
        val headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "待办事项"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.GONE
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        todo_refresh.setColorSchemeResources(R.color.colorPrimary)
        todo_refresh.setOnRefreshListener {
            mCurrentPageNum = 1
            mViewModel.loadTodoList(mCurrentPageNum)
        }
    }

    private fun setTodoList(systemListName: List<TodoResponse>) {
        val chileItems = arrayListOf<TodoResponse>()
        // 返回列表为空显示加载完毕
        if (systemListName.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (todo_refresh.isRefreshing) {
            todo_refresh.isRefreshing = false
            mAdapter.setNewData(systemListName)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(systemListName)
        mAdapter.loadMoreComplete()
    }

    override fun onBackPressed() {
        finish()
    }
}
