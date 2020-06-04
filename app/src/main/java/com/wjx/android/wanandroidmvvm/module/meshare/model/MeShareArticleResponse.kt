package com.wjx.android.wanandroidmvvm.module.meshare.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 16:58
 */
data class MeShareArticleResponse(
    var curPage :Int,
    var datas : List<Article>
)