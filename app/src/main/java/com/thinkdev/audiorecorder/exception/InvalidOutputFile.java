

package com.thinkdev.audiorecorder.exception;

public class InvalidOutputFile extends AppException {

	@Override
	public int getType() {
		return AppException.INVALID_OUTPUT_FILE;
	}
}
