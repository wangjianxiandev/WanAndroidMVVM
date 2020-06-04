package com.wjx.android.wanandroidmvvm.module.project.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:19
 */
data class ProjectResponse(
    var datas : List<Article>,
    var curPage : Int,
    var size : Int,
    var total : Int,
    var pageCount : Int
)