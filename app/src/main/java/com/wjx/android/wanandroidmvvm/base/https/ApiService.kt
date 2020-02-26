package com.wjx.android.wanandroidmvvm.base.https

import com.wjx.android.wanandroidmvvm.ui.home.data.bean.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.bean.HomeArticleResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 20:41
 */

interface ApiService {
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>

    @GET("/banner/json")
    fun loadBanner(): Observable<BaseResponse<List<BannerResponse>>>

    @GET("/article/list/{page}/json")
    fun loadHomeArticle(@Path("page") page: Int): Observable<BaseResponse<HomeArticleResponse>>
}