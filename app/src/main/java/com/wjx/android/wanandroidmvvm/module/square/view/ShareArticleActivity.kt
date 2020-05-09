package com.wjx.android.wanandroidmvvm.module.square.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.module.square.viewmodel.SquareViewModel
import kotlinx.android.synthetic.main.activity_share_article.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

class ShareArticleActivity : BaseLifeCycleActivity<SquareViewModel>(), View.OnClickListener {

    override fun initView() {
        super.initView()
        initHeaderView()
        edit_share_submit.setOnClickListener(this)
        showSuccess()
    }

    private fun initHeaderView() {
        share_bar.apply {
            detail_title.text = "分享文章"
            detail_back.visibility = View.VISIBLE
            detail_search.visibility = View.GONE
            detail_back.setOnClickListener(this@ShareArticleActivity)
        }
        initColor()
    }

    private fun initColor() {
        share_bar.setBackgroundColor(ColorUtil.getColor(this))
        edit_share_submit.setBackgroundColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mAddShareData.observe(this, Observer {
            finish()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.detail_back -> {
                finish()
            }
            R.id.edit_share_submit -> {
                if (edit_share_title.text.toString()
                        .isNullOrEmpty() || edit_share_link.text.toString().isNullOrEmpty()
                ) {
                    Toast.makeText(getApplication(), R.string.todo_empty, Toast.LENGTH_SHORT).show()
                } else {
                    mViewModel.addShareData(
                        edit_share_title.text.toString(),
                        edit_share_link.text.toString()
                    )
                }
            }
        }
    }

    override fun showCreateReveal(): Boolean = false

    override fun showDestroyReveal(): Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_share_article

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
