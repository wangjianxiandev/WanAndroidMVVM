package com.wjx.android.wanandroidmvvm.module.footprint.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.utils.RoomHelper
import com.wjx.android.wanandroidmvvm.module.common.model.Article
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/9 17:05
 */
class FootPrintRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    suspend fun loadFootPrint() = RoomHelper.queryAllFootPrint(loadState)
    suspend fun deleteFootPrint(article: Article) = RoomHelper.deleteFootPrint(article)
    suspend fun deleteAll() = RoomHelper.deleteAll()
}