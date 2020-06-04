package com.wjx.android.wanandroidmvvm.module.project.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.network.dataConvert
import com.wjx.android.wanandroidmvvm.module.project.model.ProjectResponse
import com.wjx.android.wanandroidmvvm.module.project.model.ProjectTabResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:42
 */
class ProjectRespository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadProjectTab(liveData: MutableLiveData<BaseResponse<List<ProjectTabResponse>>>) {
        apiService.loadProjectTab()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun loadProjectArticle(
        pageNum: Int,
        cid: Int,
        liveData: MutableLiveData<BaseResponse<ProjectResponse>>
    ) {
        apiService.loadProjectArticles(pageNum, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    suspend fun loadProjectTabCo(): List<ProjectTabResponse> {
        return apiService.loadProjectTabCo().dataConvert(loadState)
    }

    suspend fun loadProjectArticleCo(pageNum: Int, cid: Int): ProjectResponse {
        return apiService.loadProjectArticlesCo(pageNum, cid).dataConvert(loadState)
    }
}