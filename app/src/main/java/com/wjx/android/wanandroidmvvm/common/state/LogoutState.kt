package com.wjx.android.wanandroidmvvm.common.state

import android.content.Context
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.ui.account.view.LoginActivity
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:39
 */
class LogoutState :UserState {
    override fun collect(context: Context, position: Int, listener: CollectListener) {
        startLoginActivity(context)
    }

    override fun login(context: Context) {
        startLoginActivity(context)
    }

    override fun startRankActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startCollectActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startShareActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startAddShareActivity(context: Context) {
        startLoginActivity(context)
    }

    override fun startTodoActivity(context: Context) {
        startLoginActivity(context)
    }

    private fun startLoginActivity(context: Context) {
        context?.let {
            it.toast(it.getString(R.string.please_login))
            startActivity<LoginActivity>(it)
        }
    }

    override fun startEditTodoActivity(context: Context) {
        startLoginActivity(context)
    }
}