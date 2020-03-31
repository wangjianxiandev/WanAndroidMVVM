package com.wjx.android.wanandroidmvvm.Custom.loadingview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.utils.Util


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/31 13:25
 */
class ShapeLoadingView(context: Context?, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs, 0) {
    private lateinit var mShapeView: ShapeView

    private lateinit var mShadowView: View

    private var mDropDistance: Int = 0

    private var isStopAnimation: Boolean = false

    init {
        // 设置下落距离
        mDropDistance = Util.dp2Px(context!!.applicationContext, 100)
        initLoadingView()
    }

    private fun initLoadingView() {
        var rootview = View.inflate(context, R.layout.loading_view, this)
        mShapeView = rootView.findViewById(R.id.shape_view)
        mShadowView = rootView.findViewById(R.id.shadow)
        startDropAnimation()
    }

    private fun startDropAnimation() {
        if (isStopAnimation) {
            return
        }
        //下落的动画
        val translationAnimator =
            ObjectAnimator.ofFloat(mShapeView, "translationY", 0f, mDropDistance.toFloat())
        //阴影缩小的动画
        val scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f)
        val animatorSet = AnimatorSet()
        animatorSet.duration = 200
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.playTogether(translationAnimator, scaleAnimator)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            /**
             * 监听动画结束开启上抛动画并切换形状
             * @param animation
             */
            override fun onAnimationEnd(animation: Animator) {
                // 落地切换形状
                mShapeView.changeShape()
                //开启上抛动画
                startUpThrowAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    /**
     * 上抛动画
     */
    private fun startUpThrowAnimation() {
        if (isStopAnimation) {
            return
        }
        // 上抛距离
        val translationAnimator =
            ObjectAnimator.ofFloat(mShapeView, "translationY", mDropDistance.toFloat(), 0f)
        // 阴影放大的动画
        val scaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f)
        // 创建一个动画集合
        val animatorSet = AnimatorSet()
        // 设置动画执行时长
        animatorSet.duration = 200
        // 设置插值器
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.playTogether(translationAnimator, scaleAnimator)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // 触底旋转
                startRotateAnimation()
            }

            /**
             * 监听动画结束开启下落动画并切换形状
             * @param animation
             */
            override fun onAnimationEnd(animation: Animator) {
                // 开始下落动画
                startDropAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    private fun startRotateAnimation() {
        val rotateAnimation =
            ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, 180f)
        rotateAnimation.duration = 200
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.start()
    }
}