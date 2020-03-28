package com.wjx.android.wanandroidmvvm.ui.setting

import android.view.View
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseActivity
import com.wjx.android.wanandroidmvvm.base.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.base.utils.Util
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.custom_bar.view.*
import org.greenrobot.eventbus.Subscribe

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
        initColor()
    }

    private fun initColor() {
        setting_bar.setBackgroundColor(Util.getColor(this))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> {
                finish()
            }
        }
    }

    override fun showCreateReveal(): Boolean = false

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() {
        finish()
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
