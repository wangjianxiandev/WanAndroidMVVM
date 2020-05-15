package com.wjx.android.wanandroidmvvm.module.update

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/14 22:59
 */

data class ApkInfoResponse(
    val apkData: ApkData,
    val outputType: OutputType,
    val path: String,
    val properties: Properties
)

data class ApkData(
    val baseName: String,
    val enabled: Boolean,
    val fullName: String,
    val outputFile: String,
    val splits: List<Any>,
    val type: String,
    val versionCode: Int,
    val versionName: String
)

data class OutputType(
    val type: String
)

class Properties(
)