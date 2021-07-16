

package com.thinkdev.audiorecorder.exception;

public class CantProcessRecord extends AppException {
	@Override
	public int getType() {
		return AppException.CANT_PROCESS_RECORD;
	}
}
