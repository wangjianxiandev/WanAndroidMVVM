package com.wjx.android.wanandroidmvvm.ui.account.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.ui.account.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity

class LoginActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        button_login.setOnClickListener(this)
        register_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()
        showSuccess()
    }

    private fun initColor() {
        login_background.setBackgroundColor(ColorUtil.getColor(this))
        button_login.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mLoginData.observe(this, Observer {
            it?.let {
                loginResponse ->
                UserInfo.instance.loginSuccess(loginResponse.username, loginResponse.id.toString(),loginResponse.collectIds)
                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_login -> {
                mViewModel.loginCo(account_text.text.toString(), password_text.text.toString())
            }
            R.id.register_text -> {
                startActivity<RegisterActivity>()
                finish()
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
