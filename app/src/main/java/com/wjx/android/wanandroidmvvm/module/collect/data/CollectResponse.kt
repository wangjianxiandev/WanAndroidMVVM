package com.wjx.android.wanandroidmvvm.module.collect.data

import com.wjx.android.wanandroidmvvm.module.common.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/03
 * Time: 14:41
 */
data class CollectResponse(
    var curPage : Int,
    var datas : List<Article>,
    var total : Int
)