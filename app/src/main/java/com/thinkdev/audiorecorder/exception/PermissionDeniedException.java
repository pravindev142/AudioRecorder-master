

package com.thinkdev.audiorecorder.exception;

public class PermissionDeniedException extends AppException {
	@Override
	public int getType() {
		return AppException.READ_PERMISSION_DENIED;
	}
}
