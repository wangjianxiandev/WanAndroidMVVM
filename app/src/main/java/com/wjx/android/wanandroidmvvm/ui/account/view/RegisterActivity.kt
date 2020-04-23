package com.wjx.android.wanandroidmvvm.ui.account.view

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.common.state.UserInfo
import com.wjx.android.wanandroidmvvm.common.utils.ChangeThemeEvent
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.ui.account.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.startActivity

class RegisterActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initView() {
        super.initView()
        button_register.setOnClickListener(this)
        login_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()
        showSuccess()
    }

    private fun initColor() {
        register_background.setBackgroundColor(ColorUtil.getColor(this))
        button_register.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mRegisterData.observe(this, Observer {
            it.let {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
                UserInfo.instance.loginSuccess(it.username, it.id.toString(), it.collectIds)
                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_register -> {
                mViewModel.registerCo(
                    account_text.text.toString(),
                    password_text.text.toString(),
                    repassword_text.text.toString()
                )
            }
            R.id.login_text -> {
                startActivity<LoginActivity>()
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
