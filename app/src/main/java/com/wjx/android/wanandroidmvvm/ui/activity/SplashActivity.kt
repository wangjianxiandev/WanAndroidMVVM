package com.wjx.android.wanandroidmvvm.ui.activity

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.wjx.android.wanandroidmvvm.R


class SplashActivity : AppCompatActivity() {

    private var mLottieAnimationView: LottieAnimationView? = null

    private var mSplashContainer: ViewGroup? = null

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = getApplicationContext()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
        mSplashContainer = findViewById<ViewGroup>(R.id.splash_container)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 初始化进场动画
     */
    private fun initView() {
        mSplashContainer!!.setBackgroundColor(getColor(R.color.black))
        mLottieAnimationView = findViewById<LottieAnimationView>(R.id.splash_animation)
        mLottieAnimationView!!.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
