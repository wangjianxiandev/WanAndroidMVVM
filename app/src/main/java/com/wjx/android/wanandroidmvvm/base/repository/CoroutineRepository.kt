//package com.wjx.android.wanandroidmvvm.base.repository
//
//import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
//import kotlinx.coroutines.CoroutineScope
//
///**
// * Created with Android Studio.
// * Description:
// * @author: Wangjianxian
// * @CreateDate: 2020/4/17 23:17
// */
//open class CoroutineRepository {
//    suspend fun <T : Any> apiCall(call: suspend () -> Unit) {
//        call()
//    }
//
//    suspend fun <T : Any> executeResponse(
//        response: BaseResponse<T>,
//        successBlock: (suspend CoroutineScope.() -> Unit)? = null
//    ) {
//        return CoroutineScope{
//            if (response.errorCode == -1) {
//
//            } else {
//                successBlock?.let { it() }
//            }
//        }
//    }
//
//}