package com.wjx.android.wanandroidmvvm.base.state

import android.content.Context
import android.content.Intent
import com.wjx.android.wanandroidmvvm.base.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.ui.collect.CollectArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.system.view.SystemArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.todo.TodoActivity

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:32
 */
class LoginState : UserState {

    override fun collect(context: Context?, position: Int, listener: CollectListener) {
        listener.collect(position)
    }

    override fun login(context: Context?) {}

    override fun startCollectActivity(context: Context?) {
        val intent = Intent(context, CollectArticleListActivity::class.java)
        context?.startActivity(intent)
    }

    override fun startTodoActivity(context: Context?) {
        val intent = Intent(context, TodoActivity::class.java)
        context?.startActivity(intent)
    }
}