package com.wjx.android.wanandroidmvvm.base.state

import android.app.Activity
import android.content.Context
import com.wjx.android.wanandroidmvvm.base.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.base.state.callback.LoginSuccessState
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import com.wjx.android.wanandroidmvvm.base.utils.Preference

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:30
 */
class UserInfo private constructor() {

    private var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    // 设置默认状态
    var mState: UserState = if (isLogin) LoginState() else LogoutState()

    companion object {
        val instance =
            Holder.INSTANCE
    }

    // 内部类 单利
    private object Holder {
        val INSTANCE = UserInfo()
    }

    // 收藏
    fun collect(context: Context?, position: Int, listener: CollectListener) {
        mState.collect(context, position, listener)
    }

    fun startCollectActivity(context: Context?) {
        mState.startCollectActivity(context)
    }

    fun startTodoActivity(context: Context?) {
        mState.startTodoActivity(context)
    }

    // 跳转去登录
    fun login(context: Activity?) {
        mState.login(context)
    }

    fun loginSuccess(username: String, collectIds: List<Int>?) {
        // 改变 sharedPreferences   isLogin值
        isLogin = true
        UserInfo.instance.mState = LoginState()

        // 登录成功 回调 -> DrawerLayout -> 个人信息更新状态
        LoginSuccessState.notifyLoginState(username, collectIds)
    }

    fun logoutSuccess() {
        UserInfo.instance.mState = LogoutState()
        // 清除 cookie、登录缓存
        Preference.clear()
        LoginSuccessState.notifyLoginState("未登录", null)
    }
}