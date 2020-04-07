package com.wjx.android.wanandroidmvvm.ui.todo.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.base.repository.ApiRepository
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.ui.todo.data.TodoPageResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/04
 * Time: 15:20
 */
class TodoRepository(var loadState: MutableLiveData<State>) : ApiRepository() {

    fun loadTodoList(pageNum: Int, liveData: MutableLiveData<BaseResponse<TodoPageResponse>>) {
        apiService.loadTodoData(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun addTodo(
        title: String, content: String, date: String, type: Int, priority: Int,
        liveData: MutableLiveData<BaseResponse<EmptyResponse>>
    ) {
        apiService.addTodo(title, content, date, type, priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun finishTodo(id: Int, status: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.finishTodo(id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun updateTodo(
        id: Int?, title: String, content: String, date: String, type: Int, priority: Int,
        liveData: MutableLiveData<BaseResponse<EmptyResponse>>
    ) {
        apiService.updateTodo(id, title, content, date, type, priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun deleteTodo(id: Int, liveData: MutableLiveData<BaseResponse<EmptyResponse>>) {
        apiService.deleteTodo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }
}