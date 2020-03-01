package com.wjx.android.wanandroidmvvm.ui.account.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseLifeCycleActivity
import com.wjx.android.wanandroidmvvm.base.state.UserInfo
import com.wjx.android.wanandroidmvvm.ui.account.viewmodel.AccountViewModel
import com.wjx.android.wanandroidmvvm.ui.todo.TodoActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        mBtnLogin.setOnClickListener(this)
        mTvRegister.setOnClickListener(this)
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

    override fun onBackPressed() {
        finish()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mBtnLogin -> {
                mViewModel.login(mTieAccount.text.toString(), mTiePassword.text.toString())
            }
            R.id.mTvRegister -> {
                startActivity<TodoActivity>()
                finish()
            }
        }
    }
}
