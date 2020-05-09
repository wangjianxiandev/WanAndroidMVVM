package com.wjx.android.wanandroidmvvm.module.search.data.db

import org.litepal.crud.LitePalSupport

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:35
 */
class SearchHistory(var id : Long = -1, var name : String = "") : LitePalSupport() {
    companion object {
        const val MAX_HISTORY = 10
    }
}