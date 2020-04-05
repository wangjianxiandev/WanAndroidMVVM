package com.wjx.android.wanandroidmvvm.ui.account.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.account.data.LoginResponse
import com.wjx.android.wanandroidmvvm.ui.account.data.RegisterResponse
import com.wjx.android.wanandroidmvvm.ui.account.repository.AccountReponsitory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:58
 */
class AccountViewModel(application: Application) : BaseViewModel<AccountReponsitory>(application) {
    val mLoginData : MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val mRegisterData : MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()

    fun login(username: String, password: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(getApplication(), R.string.accountOrpasswordempty, Toast.LENGTH_SHORT).show()
        } else {
            mRepository.login(username, password, mLoginData)
        }
    }

    fun register(username: String, password: String, repassword: String) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty() || password != repassword) {
            Toast.makeText(getApplication(), R.string.accountOrpasswordempty, Toast.LENGTH_SHORT).show()
        } else {
            mRepository.register(username, password, repassword, mRegisterData)
        }
    }
}
