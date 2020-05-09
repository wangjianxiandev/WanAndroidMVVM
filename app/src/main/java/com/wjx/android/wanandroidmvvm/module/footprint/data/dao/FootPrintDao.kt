package com.wjx.android.wanandroidmvvm.module.footprint.data.dao

import androidx.room.*
import com.wjx.android.wanandroidmvvm.module.common.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/9 15:36
 */
@Dao
interface FootPrintDao {
    @Transaction
    @Insert(entity = Article::class)
    suspend fun insertFootPrint(article: Article): Long

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun queryAllFootPrint(): List<Article>

    @Transaction
    @Query("SELECT * FROM article WHERE id = (:id)")
    suspend fun queryArticleById(id: Int): Article?

    @Transaction
    @Delete(entity = Article::class)
    suspend fun deleteArticle(article: Article) : Int

    @Transaction
    @Query("DELETE FROM article")
    suspend fun deleteAll()
}