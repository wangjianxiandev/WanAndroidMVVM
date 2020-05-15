package com.wjx.android.wanandroidmvvm.module.setting

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.afollestad.materialdialogs.MaterialDialog
import com.pgyersdk.update.DownloadFileListener
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.*
import java.io.File


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
        var nightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
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
            nightMode = boolValue
            var currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            parentActivity.delegate.localNightMode =
                if (currentMode == Configuration.UI_MODE_NIGHT_NO) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            startActivity<SettingActivity>(parentActivity)
            parentActivity.overridePendingTransition(
                R.anim.animo_alph_open,
                R.anim.animo_alph_close
            )
            parentActivity.finish()
            var nightModeChanged: Boolean by SPreference(Constant.NIGHT_MODE, false)
            AppCompatDelegate.setDefaultNightMode(
                if (nightModeChanged) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            if (nightModeChanged) {
                ColorUtil.setLastColor(ColorUtil.getColor(parentActivity))
                ColorUtil.setColor(
                    ContextCompat.getColor(parentActivity, R.color.colorGray666)
                )
            } else {
                ColorUtil.setColor(ColorUtil.getLastColor(parentActivity))
            }
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

        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            initUpdate()
            false
        }

        findPreference<Preference>("csdn")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(parentActivity, "https://blog.csdn.net/qq_39424143", "DLUT_WJX")
            false
        }

        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            CommonUtil.startWebView(
                parentActivity,
                "https://github.com/wangjianxiandev/WanAndroidMvvm",
                "WanAndroid"
            )
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }

    fun initUpdate() {
        val dialog = ProgressDialog(parentActivity)
        PgyUpdateManager.Builder()
            .setForced(false) //设置是否强制提示更新,非自定义回调更新接口此方法有用
            .setUserCanRetry(false) //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
            .setDeleteHistroyApk(false) // 检查更新前是否删除本地历史 Apk， 默认为true
            .setUpdateManagerListener(object : UpdateManagerListener {
                override fun onNoUpdateAvailable() {
                    //没有更新是回调此方法
                    Toast.makeText(parentActivity, R.string.no_version, Toast.LENGTH_SHORT).show()
                }

                override fun onUpdateAvailable(appBean: AppBean) {
                    //有更新回调此方法
                    //调用以下方法，DownloadFileListener 才有效；
                    //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                    AlertDialog.Builder(parentActivity)
                        .setMessage(getString(R.string.new_version) + appBean.versionName)
                        .setNegativeButton(R.string.cancel) { _, _ -> }
                        .setPositiveButton(R.string.done) { _, _ ->
                            PgyUpdateManager.downLoadApk(appBean.downloadURL)
                        }.show()
                }

                override fun checkUpdateFailed(e: Exception) {
                    //更新检测失败回调
                    //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                    Toast.makeText(parentActivity, R.string.check_update_fail, Toast.LENGTH_SHORT)
                        .show()
                }
            }) //注意 ：
            //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
            //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
            //想要使用蒲公英的默认下载进度的UI则不设置此方法
            .setDownloadFileListener(object : DownloadFileListener {
                override fun downloadFailed() {
                    //下载失败
                    Toast.makeText(parentActivity, R.string.down_load_fail, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onProgressUpdate(vararg args: Int?) {
                    dialog.setTitle(getString(R.string.updating))
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                    dialog.progress = if(args.get(0) == null) 0 else args.get(0)!!
                    dialog.show()
                    Log.e("pgyer", "update download apk progress" + args)
                }

                override fun downloadSuccessful(file: File?) {
                    Log.e("pgyer", "download apk success")
                    // 使用蒲公英提供的安装方法提示用户 安装apk
                    dialog.dismiss()
                    PgyUpdateManager.installApk(file)
                }
            })
            .register()
    }
}