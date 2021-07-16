

package com.thinkdev.audiorecorder.exception;

public class PlayerDataSourceException extends AppException {
	@Override
	public int getType() {
		return AppException.PLAYER_DATA_SOURCE_EXCEPTION;
	}
}
