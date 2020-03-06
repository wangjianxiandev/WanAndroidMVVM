package com.wjx.android.wanandroidmvvm.ui.setting

import android.view.View
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.custom_bar.view.*

class SettingActivity : BaseActivity(), View.OnClickListener{
    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun initView() {
        super.initView()
        initHeadView()
        initSettingFragment()
    }

    private fun initSettingFragment() {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.setting_container, SettingFragment())
        fragmentTransaction.commit()
    }

    private fun initHeadView() {
        setting_bar.detail_title.text = "设置"
        setting_bar.detail_back.visibility = View.VISIBLE
        setting_bar.detail_search.visibility = View.GONE
        setting_bar.detail_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> {
                finish()
            }
        }
    }

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() {
        finish()
    }

}
