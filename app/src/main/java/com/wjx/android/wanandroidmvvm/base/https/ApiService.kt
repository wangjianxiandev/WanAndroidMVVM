package com.wjx.android.wanandroidmvvm.base.https

import com.wjx.android.wanandroidmvvm.ui.home.data.bean.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.data.bean.HomeArticleResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemArticleResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemTabResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatArticleResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/article/list/{pageNum}/json")
    fun loadHomeArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<HomeArticleResponse>>

    @GET("/wxarticle/chapters/json")
    fun loadWeChatName(): Observable<BaseResponse<List<WeChatTabNameResponse>>>

    @GET("/wxarticle/list/{cid}/{pageNum}/json")
    fun loadWeChatArticle(@Path("cid") cid: Int, @Path("pageNum") page: Int)
            : Observable<BaseResponse<WeChatArticleResponse>>

    @GET("/tree/json")
    fun loadSystem(): Observable<BaseResponse<List<SystemTabResponse>>>

    @GET("/article/list/{pageNum}/json")
    fun loadSystemArticle(@Path("pageNum") pageNum: Int, @Query("cid") id: Int): Observable<BaseResponse<SystemArticleResponse>>
}