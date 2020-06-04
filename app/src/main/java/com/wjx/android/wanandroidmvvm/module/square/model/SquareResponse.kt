package com.wjx.android.wanandroidmvvm.module.square.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

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