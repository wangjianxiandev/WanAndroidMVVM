package com.wjx.android.wanandroidmvvm.ui.todo.data

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/04
 * Time: 11:53
 */
data class TodoPageResponse(
    var curPage: Int,
    var datas: List<TodoResponse>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)