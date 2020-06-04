package com.wjx.android.wanandroidmvvm.module.todo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.viewmodel.BaseViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.module.todo.model.TodoPageResponse
import com.wjx.android.wanandroidmvvm.module.todo.repository.TodoRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/04
 * Time: 15:39
 */
class TodoViewModel(application: Application) : BaseViewModel<TodoRepository>(application) {
    val mTodoListData: MutableLiveData<BaseResponse<TodoPageResponse>> = MutableLiveData()
    val mTodoAddData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoUpdateData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoFinishData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()
    val mTodoDeleteData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadTodoList(pageNum: Int = 1) {
        mRepository.loadTodoList(pageNum, mTodoListData)
    }

    fun addTodo(title: String, content: String, date: String, type: Int, priority: Int) {
        mRepository.addTodo(title, content, date, type, priority, mTodoAddData)
    }

    fun finishTodo(id: Int, status: Int) {
        mRepository.finishTodo(id, status, mTodoFinishData)
    }

    fun updateTodo(
        id: Int?,
        title: String,
        content: String,
        date: String,
        type: Int,
        priority: Int
    ) {
        mRepository.updateTodo(id, title, content, date, type, priority, mTodoUpdateData)
    }

    fun deleteTodo(id: Int) {
        mRepository.deleteTodo(id, mTodoDeleteData)
    }
}