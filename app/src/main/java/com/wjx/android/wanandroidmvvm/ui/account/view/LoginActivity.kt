package com.wjx.android.wanandroidmvvm.ui.account.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.ui.account.viewmodel.AccountViewModel
import com.wjx.android.wanandroidmvvm.ui.todo.view.TodoActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        button_login.setOnClickListener(this)
        register_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        showSuccess()
    }

    override fun initDataObserver() {
        mViewModel.mLoginData.observe(this, Observer {
            it?.data?.let {
                loginResponse ->
                UserInfo.instance.loginSuccess(loginResponse.username, loginResponse.collectIds)
                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = true

    override fun onBackPressed() {
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_login -> {
                mViewModel.login(account_text.text.toString(), password_text.text.toString())
            }
            R.id.register_text -> {
                startActivity<TodoActivity>()
                finish()
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }
}
