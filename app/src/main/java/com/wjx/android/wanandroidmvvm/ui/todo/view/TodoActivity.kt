package com.wjx.android.wanandroidmvvm.ui.todo.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.todo.adapter.TodoAdapter
import com.wjx.android.wanandroidmvvm.ui.todo.data.TodoResponse
import com.wjx.android.wanandroidmvvm.ui.todo.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.custom_bar.view.custom_bar
import org.greenrobot.eventbus.Subscribe

class TodoActivity : BaseLifeCycleActivity<TodoViewModel>() {

    private lateinit var mAdapter: TodoAdapter

    private var mCurrentPageNum: Int = 1

    private lateinit var headerView : View

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
        mAdapter.setOnItemClickListener { adapter, view, position ->
            var todo = mAdapter.getItem(position)
            val intent = Intent(this, EditTodoActivity::class.java)
            intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.EDIT_TODO)
            intent.putExtra(Constant.KEY_TODO_TITLE, todo!!.title)
            intent.putExtra(Constant.KEY_TODO_CONTENT, todo!!.content)
            intent.putExtra(Constant.KEY_TODO_DATE, todo!!.dateStr)
            intent.putExtra(Constant.KEY_TODO_PRIORITY, todo!!.priority.toString() + "")
            intent.putExtra(Constant.KEY_TODO_ID, todo!!.id.toString() + "")
            intent.putExtra(Constant.KEY_TODO_TYPE, todo!!.type.toString() + "")
            startActivity(intent)
        }

        mAdapter.setOnItemChildClickListener { _, _, position ->
            // 底部弹出对话框
            val bottomDialog =
                Dialog(this, R.style.BottomDialog)
            val contentView =
                LayoutInflater.from(this).inflate(R.layout.dialog_handle_todo, null)
            bottomDialog.setContentView(contentView)
            val params = contentView.layoutParams as MarginLayoutParams
            params.width =
                getResources().getDisplayMetrics().widthPixels - Util.dp2Px(this, 16)
            params.bottomMargin = Util.dp2Px(this, 8)
            contentView.layoutParams = params
            bottomDialog.window!!.setGravity(Gravity.BOTTOM)
            bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
            var todo = mAdapter.getItem(position)
            if (todo?.status == 0) {
                contentView.findViewById<View>(R.id.done_todo).visibility = View.VISIBLE
            }
            bottomDialog.show()
            // 点击对话框中编辑
            contentView.findViewById<View>(R.id.edit_todo)
                .setOnClickListener { v1: View? ->
                    bottomDialog.dismiss()
                    val intent = Intent(this, EditTodoActivity::class.java)
                    intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.EDIT_TODO)
                    intent.putExtra(Constant.KEY_TODO_TITLE, todo!!.title)
                    intent.putExtra(Constant.KEY_TODO_CONTENT, todo!!.content)
                    intent.putExtra(Constant.KEY_TODO_DATE, todo!!.dateStr)
                    intent.putExtra(Constant.KEY_TODO_PRIORITY, todo!!.priority.toString() + "")
                    intent.putExtra(Constant.KEY_TODO_ID, todo!!.id.toString() + "")
                    intent.putExtra(Constant.KEY_TODO_TYPE, todo!!.type.toString() + "")
                    startActivity(intent)
                }

            // 点击对话框删除
            contentView.findViewById<View>(R.id.delete_todo)
                .setOnClickListener { v1: View? ->
                    mViewModel.deleteTodo(todo?.id!!)
                    mAdapter.remove(position)
                    bottomDialog.dismiss()
                }

            // 点击完成该ToDo
            contentView.findViewById<View>(R.id.done_todo)
                .setOnClickListener { v1: View? ->
                    mViewModel.finishTodo(todo?.id!!, 1)
                    // 直接将本地的状态置成完成状态
                    todo.status = 1
                    mAdapter.notifyDataSetChanged()
                    bottomDialog.dismiss()
                }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_todo

    private fun initHeadView() {
        headerView = View.inflate(this, R.layout.custom_bar, null)
        headerView.detail_title.text = "待办事项"
        headerView.detail_back.visibility = View.VISIBLE
        headerView.detail_search.visibility = View.VISIBLE
        headerView.detail_search.setImageResource(R.drawable.ic_add)
        headerView.detail_search.setOnClickListener { onAddTodo() }
        headerView.detail_back.setOnClickListener { onBackPressed() }
        mAdapter.addHeaderView(headerView)
        initColor()
    }

    private fun initColor() {
        headerView.setBackgroundColor(Util.getColor(this))
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        todo_refresh.setProgressBackgroundColorSchemeColor(Util.getColor(this))
        todo_refresh.setColorSchemeColors(Color.WHITE)
        todo_refresh.setOnRefreshListener {
            onRefreshData()
        }
    }

    private fun setTodoList(systemListName: List<TodoResponse>) {
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

    private fun onRefreshData() {
        mCurrentPageNum = 1
        mViewModel.loadTodoList(mCurrentPageNum)
    }

    override fun reLoad() {
        showLoading()
        onRefreshData()
        super.reLoad()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun onAddTodo() {
        UserInfo.instance.startEditTodoActivity(this)
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        todo_refresh.setProgressBackgroundColorSchemeColor(Util.getColor(this))
        todo_refresh.setColorSchemeColors(Color.WHITE)
        initColor()
    }
}
