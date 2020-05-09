package com.wjx.android.wanandroidmvvm.common.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.view.BaseApplication
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.state.StateType
import com.wjx.android.wanandroidmvvm.module.common.data.Article
import com.wjx.android.wanandroidmvvm.module.footprint.data.database.FootPrintDataBase

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/9 15:02
 */
object RoomHelper {
    private val footPrintDataBase by lazy {
        FootPrintDataBase.getInstance(BaseApplication.instance)
    }

    private val footPrintDao by lazy {
        footPrintDataBase?.footPrintDao()
    }

    // 按最近顺序组织足迹列表
    suspend fun queryAllFootPrint(loadState: MutableLiveData<State>): List<Article> {
        val response = footPrintDao?.queryAllFootPrint()?.reversed()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.EMPTY))
        }
        return response
    }

    suspend fun insertFootPrint(article: Article) {
        footPrintDao?.let {
            it.queryArticleById(article.id)?.let {
                var i = footPrintDao!!.deleteArticle(it)
                Log.e("DELETE", i.toString())
            }
            it.insertFootPrint(article.apply { primaryKeyId = 0 })
        }
    }

    suspend fun deleteFootPrint(article: Article) {
        var i = footPrintDao?.deleteArticle(article)
        Log.e("DELETE", i.toString())
    }

    suspend fun deleteAll() {
        footPrintDao?.deleteAll()
    }
}

