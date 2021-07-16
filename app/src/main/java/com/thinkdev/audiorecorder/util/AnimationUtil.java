
package com.thinkdev.audiorecorder.util;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.view.View;
import android.view.animation.AnimationUtils;

public class AnimationUtil {

	private AnimationUtil() {}

	@TargetApi(21)
	public static void viewElevationAnimation(final View view, float val, Animator.AnimatorListener listener) {
		view.animate()
				.translationZ(val)
				.setDuration(250L)
				.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(),
						android.R.interpolator.accelerate_decelerate))
				.setListener(listener)
				.start();
	}

	@TargetApi(21)
	public static void viewAnimationX(final View view, float val, Animator.AnimatorListener listener) {
		view.animate()
				.translationX(val)
				.setDuration(250L)
				.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(),
						android.R.interpolator.accelerate_decelerate))
				.setListener(listener)
				.start();
	}

	@TargetApi(21)
	public static void viewAnimationY(final View view, float val, Animator.AnimatorListener listener) {
		view.animate()
				.translationY(val)
				.setDuration(250L)
				.setInterpolator(AnimationUtils.loadInterpolator(view.getContext(),
						android.R.interpolator.accelerate_decelerate))
				.setListener(listener)
				.start();
	}
}
