package com.wjx.android.wanandroidmvvm.module.square.data

import com.wjx.android.wanandroidmvvm.module.common.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:01
 */
data class SquareResponse(
    var curPage : Int,
    var datas : List<Article>
)