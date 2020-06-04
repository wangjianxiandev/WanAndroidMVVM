package com.wjx.android.wanandroidmvvm.module.system.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:06
 */
data class SystemArticleResponse(
    var curPage: Int,
    var datas: List<Article>,
    var pageCount: Int,
    var total: Int
)