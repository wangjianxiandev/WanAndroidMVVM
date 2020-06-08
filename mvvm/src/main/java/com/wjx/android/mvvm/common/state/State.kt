package com.wjx.android.mvvm.common.state

import androidx.annotation.StringRes

/**
 * Created with Android Studio.
 * Description: 状态类
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 15:28
 */
data class State(var code : StateType, var message : String = "", @StringRes var tip : Int = 0)