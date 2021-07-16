

package com.thinkdev.audiorecorder.app.welcome;

import com.thinkdev.audiorecorder.Contract;


public interface WelcomeContract {

	interface View extends Contract.View {
	}

	interface UserActionsListener extends Contract.UserActionsListener<WelcomeContract.View> {
	}
}
