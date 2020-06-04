package com.wjx.android.wanandroidmvvm.module.collect.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

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