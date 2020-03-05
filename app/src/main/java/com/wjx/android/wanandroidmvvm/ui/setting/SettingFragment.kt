package com.wjx.android.wanandroidmvvm.ui.setting

import android.content.SharedPreferences

import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle

import androidx.preference.Preference
import com.afollestad.materialdialogs.MaterialDialog
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.utils.DataCleanManager
import com.wjx.android.wanandroidmvvm.base.utils.Util


/**
 * Created with Android Studio.
 * Description:
*
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 15:04
 */
class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var parentActivity: SettingActivity
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_fragment)
        parentActivity = activity as SettingActivity
        init()
    }


    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }


    private fun init() {
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(parentActivity.packageName, 0).versionName
        findPreference<Preference>("version")?.summary = version
        findPreference<Preference>("clearCache")?.summary = DataCleanManager.getTotalCacheSize(parentActivity)


        // 绑定清理缓存响应事件
        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定清除缓存吗？")
                cornerRadius(8.0f)
                positiveButton(text = "清除") {
                    DataCleanManager.clearAllCache(parentActivity)
                    findPreference<Preference>("clearCache")?.summary = DataCleanManager.getTotalCacheSize(parentActivity)
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        findPreference<Preference>("csdn")?.setOnPreferenceClickListener {
            Util.startWebView(parentActivity,"DLUT_WJX", "https://blog.csdn.net/qq_39424143")
            false
        }

        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            Util.startWebView(parentActivity,"WanAndroid", "https://github.com/wangjianxiandev/WanAndroidMvvm")
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}