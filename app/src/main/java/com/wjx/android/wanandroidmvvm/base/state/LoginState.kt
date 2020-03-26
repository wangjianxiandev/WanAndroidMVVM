package com.wjx.android.wanandroidmvvm.base.state

import android.content.Context
import android.content.Intent
import com.wjx.android.wanandroidmvvm.base.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.base.utils.Constant
import com.wjx.android.wanandroidmvvm.ui.collect.view.CollectArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.meshare.view.MeShareActivity
import com.wjx.android.wanandroidmvvm.ui.todo.view.EditTodoActivity
import com.wjx.android.wanandroidmvvm.ui.todo.view.TodoActivity

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

    override fun startShareActivity(context: Context?) {
        val intent = Intent(context, MeShareActivity::class.java)
        context?.startActivity(intent)
    }

    override fun startTodoActivity(context: Context?) {
        val intent = Intent(context, TodoActivity::class.java)
        context?.startActivity(intent)
    }

    override fun startEditTodoActivity(context: Context?) {
        val intent = Intent(context, EditTodoActivity::class.java)
        intent.putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.ADD_TODO)
        context?.startActivity(intent)
    }
}