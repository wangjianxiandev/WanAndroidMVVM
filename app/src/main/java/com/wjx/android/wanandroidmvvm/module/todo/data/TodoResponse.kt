package com.wjx.android.wanandroidmvvm.module.todo.data

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/04
 * Time: 11:22
 */
data class TodoResponse(
    var completeDate: String,
    var content: String,
    var date : Long,
    var dateStr: String,
    var id: Int,
    var priority: Int,
    var status: Int,
    var type: Int,
    var title: String
)