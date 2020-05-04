package com.wjx.android.wanandroidmvvm.ui.scan

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.startActivity
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity


class ScanAnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_analysis)
        /**
         * 执行扫面Fragment的初始化操作
         */
        val captureFragment = CaptureFragment()
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan_camera)
        captureFragment.analyzeCallback = analyzeCallback
        /**
         * 替换我们的扫描控件
         */
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_my_container, captureFragment).commit()
    }

    var analyzeCallback: AnalyzeCallback = object : AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            Log.e("WJX", result)
            Toast.makeText(this@ScanAnalysisActivity, result.toString(), Toast.LENGTH_SHORT).show()
            startActivity<ArticleDetailActivity>(this@ScanAnalysisActivity) {
                putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
                putExtra("url", result)
            }

        }

        override fun onAnalyzeFailed() {
//            Toast.makeText(this@ScanAnalysisActivity, result.toString(), Toast.LENGTH_SHORT).show()
            startActivity<ArticleDetailActivity>(this@ScanAnalysisActivity) {
                putExtra(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
                putExtra(CodeUtils.RESULT_STRING, "")
            }
        }
    }
}
