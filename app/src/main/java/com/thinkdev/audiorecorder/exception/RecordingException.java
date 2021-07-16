

package com.thinkdev.audiorecorder.exception;

public class RecordingException extends AppException {
	@Override
	public int getType() {
		return AppException.RECORDING_ERROR;
	}
}
