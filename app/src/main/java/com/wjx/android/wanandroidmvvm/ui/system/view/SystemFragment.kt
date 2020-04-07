package com.wjx.android.wanandroidmvvm.ui.system.view

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.ui.system.adapter.SystemAdapter
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemLabelResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.system.viewmodel.SystemViewModel
import kotlinx.android.synthetic.main.layout_system.*
import org.greenrobot.eventbus.Subscribe

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:01
 */
class SystemFragment : BaseLifeCycleFragment<SystemViewModel>() {
    protected lateinit var mAdapter: SystemAdapter

    companion object {
        fun getInstance(): SystemFragment? {
            return SystemFragment()
        }
    }

    override fun initDataObserver() {
        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
            response?.let {
                setSystemTabData(it.data)
            }
        })
    }

    override fun initData() {
        mViewModel.loadSystemTab()
    }

    override fun getLayoutId(): Int = R.layout.layout_system

    override fun initView() {
        super.initView()
        initRefresh()
        recycler_view?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = SystemAdapter(R.layout.system_item, null)
        recycler_view.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            item?.let {
            }
        }
    }

    private fun initRefresh() {
        // 设置下拉刷新的loading颜色
        system_refresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(activity!!))
        system_refresh.setColorSchemeColors(Color.WHITE)
        system_refresh.setOnRefreshListener { onRefreshData() }
    }

    private fun onRefreshData() {
        mViewModel.loadSystemTab()
    }

    private fun setSystemTabData(systemListName : List<SystemTabNameResponse>) {
        val chileItems = arrayListOf<SystemLabelResponse>()
        // 返回列表为空显示加载完毕
        if (systemListName.isEmpty()) {
            mAdapter.loadMoreEnd()
            return
        }

        // 如果是下拉刷新状态，直接设置数据
        if (system_refresh.isRefreshing) {
            system_refresh.isRefreshing = false
            mAdapter.setNewData(systemListName)
            mAdapter.loadMoreComplete()
            return
        }

        // 初始化状态直接加载数据
        mAdapter.addData(systemListName)
        mAdapter.loadMoreComplete()
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        system_refresh.setProgressBackgroundColorSchemeColor(ColorUtil.getColor(activity!!))
        mAdapter.notifyDataSetChanged()
    }
}