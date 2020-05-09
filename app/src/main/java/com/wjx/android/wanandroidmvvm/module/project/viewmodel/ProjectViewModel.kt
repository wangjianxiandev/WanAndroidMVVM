package com.wjx.android.wanandroidmvvm.module.project.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.module.project.data.ProjectResponse
import com.wjx.android.wanandroidmvvm.module.project.data.ProjectTabResponse
import com.wjx.android.wanandroidmvvm.module.project.repository.ProjectRespository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:48
 */
class ProjectViewModel(application: Application) :
    ArticleViewModel<ProjectRespository>(application) {
//    val mProjectTabData : MutableLiveData<BaseResponse<List<ProjectTabResponse>>> = MutableLiveData()
//    val mProjectArticleData : MutableLiveData<BaseResponse<ProjectResponse>> = MutableLiveData()
//
//    fun loadProjectTab() {
//        mRepository.loadProjectTab(mProjectTabData)
//    }
//
//    fun loadProjectArticle(pageNum : Int, cid : Int) {
//        mRepository.loadProjectArticle(pageNum, cid, mProjectArticleData)
//    }

    val mProjectTabData: MutableLiveData<List<ProjectTabResponse>> = MutableLiveData()
    val mProjectArticleData: MutableLiveData<ProjectResponse> = MutableLiveData()

    fun loadProjectTab() {
        initiateRequest({ mProjectTabData.value = mRepository.loadProjectTabCo() }, loadState)
    }

    fun loadProjectArticle(pageNum: Int, cid: Int) {
        initiateRequest({
            mProjectArticleData.value = mRepository.loadProjectArticleCo(pageNum, cid)
        }, loadState)
    }
}