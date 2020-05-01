package com.wjx.android.wanandroidmvvm.common.state

import android.content.Context
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener
import com.wjx.android.wanandroidmvvm.common.utils.Constant
import com.wjx.android.wanandroidmvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.ui.collect.view.CollectArticleListActivity
import com.wjx.android.wanandroidmvvm.ui.meshare.view.MeShareActivity
import com.wjx.android.wanandroidmvvm.ui.rank.view.RankActivity
import com.wjx.android.wanandroidmvvm.ui.square.view.ShareArticleActivity
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

    override fun collect(context: Context, position: Int, listener: CollectListener) {
        listener.collect(position)
    }

    override fun login(context: Context) {}

    override fun startRankActivity(context: Context) {
        startActivity<RankActivity>(context)
    }

    override fun startCollectActivity(context: Context) {
        startActivity<CollectArticleListActivity>(context)
    }

    override fun startShareActivity(context: Context) {
        startActivity<MeShareActivity>(context)
    }

    override fun startAddShareActivity(context: Context) {
        startActivity<ShareArticleActivity>(context)
    }

    override fun startTodoActivity(context: Context) {
        startActivity<TodoActivity>(context)
    }

    override fun startEditTodoActivity(context: Context) {
        startActivity<EditTodoActivity>(context) {
            putExtra(Constant.KEY_TODO_HANDLE_TYPE, Constant.ADD_TODO)
        }
    }
}