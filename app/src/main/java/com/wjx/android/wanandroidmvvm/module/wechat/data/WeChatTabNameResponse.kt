package com.wjx.android.wanandroidmvvm.module.wechat.data

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:46
 */
data class WeChatTabNameResponse(
    var courseId : Int,
    var name : String,
    var id : Int,
    var order : Int,
    var parentChapterId : Int,
    var userControlSetTop : Boolean
)