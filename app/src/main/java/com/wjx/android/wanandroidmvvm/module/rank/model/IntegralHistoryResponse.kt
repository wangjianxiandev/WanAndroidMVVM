package com.wjx.android.wanandroidmvvm.module.rank.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/29 19:35
 */
data class IntegralHistoryResponse(
    var coinCount : Int,
    var date : Long,
    var desc : String,
    var reason: String
)