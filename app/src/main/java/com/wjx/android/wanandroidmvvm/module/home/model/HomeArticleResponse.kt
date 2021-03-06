package com.wjx.android.wanandroidmvvm.module.home.model

import com.wjx.android.wanandroidmvvm.module.common.model.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:58
 */
data class HomeArticleResponse(var curPage: Int,
                               var datas: List<Article>,
                               var offset: Int,
                               var over: Boolean,
                               var pageCount: Int,
                               var size: Int,
                               var total: Int)