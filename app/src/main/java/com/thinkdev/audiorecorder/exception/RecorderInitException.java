

package com.thinkdev.audiorecorder.exception;

public class RecorderInitException extends AppException {
	@Override
	public int getType() {
		return AppException.RECORDER_INIT_EXCEPTION;
	}
}
