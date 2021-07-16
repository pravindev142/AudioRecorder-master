

package com.thinkdev.audiorecorder.exception;

public class CantCreateFileException extends AppException {

	@Override
	public int getType() {
		return AppException.CANT_CREATE_FILE;
	}
}
