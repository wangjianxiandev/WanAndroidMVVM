package com.wjx.android.wanandroidmvvm.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration

import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import com.afollestad.materialdialogs.MaterialDialog
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.*


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 15:04
 */
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
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
        var nightMode = ColorUtil.getNightMode(parentActivity)
        val version = "当前版本 " + parentActivity.packageManager.getPackageInfo(
            parentActivity.packageName,
            0
        ).versionName
        findPreference<SwitchPreference>("night")?.isChecked = nightMode
        findPreference<Preference>("version")?.summary = version
        findPreference<Preference>("clearCache")?.summary =
            DataCleanManager.getTotalCacheSize(parentActivity)

        findPreference<SwitchPreference>("night")?.setOnPreferenceChangeListener { preference, newValue ->
            val boolValue = newValue as Boolean
            findPreference<SwitchPreference>("night")?.isChecked = !boolValue
            ColorUtil.setNightMode(parentActivity, boolValue)
            nightMode = boolValue
            var currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            parentActivity.delegate.localNightMode =
                if (currentMode == Configuration.UI_MODE_NIGHT_NO) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            startActivity((Intent(parentActivity, SettingActivity::class.java)))
            parentActivity.overridePendingTransition(
                R.anim.animo_alph_open,
                R.anim.animo_alph_close
            )
            parentActivity.finish()
            var nightModeChanged = ColorUtil.getNightMode(parentActivity)
            AppCompatDelegate.setDefaultNightMode(
                if (nightModeChanged) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
            if (nightModeChanged) {
                ColorUtil.setLastColor(parentActivity, ColorUtil.getColor(parentActivity))
                ColorUtil.setColor(
                    parentActivity,
                    ContextCompat.getColor(parentActivity, R.color.colorGray666)
                )
            } else {
                ColorUtil.setColor(parentActivity, ColorUtil.getLastColor(parentActivity))
            }
//            RecreateEvent().post()
            true
        }

        // 绑定清理缓存响应事件
        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            MaterialDialog(parentActivity).show {
                title(R.string.title)
                message(text = "确定清除缓存吗？")
                cornerRadius(8.0f)
                positiveButton(text = "清除") {
                    DataCleanManager.clearAllCache(parentActivity)
                    findPreference<Preference>("clearCache")?.summary =
                        DataCleanManager.getTotalCacheSize(parentActivity)
                }
                negativeButton(R.string.cancel)
            }
            false
        }

        findPreference<Preference>("csdn")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(parentActivity, "DLUT_WJX", "https://blog.csdn.net/qq_39424143")
            false
        }

        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(
                parentActivity,
                "WanAndroid",
                "https://github.com/wangjianxiandev/WanAndroidMvvm"
            )
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}