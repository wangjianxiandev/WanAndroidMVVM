package com.wjx.android.wanandroidmvvm.ui.wechat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 15:35
 */

class WeChatTabAdapter(
    fragmentManager: FragmentManager,
    val tabs: List<String>,
    val fragments: List<Fragment>
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? = tabs[position]
}