

package com.thinkdev.audiorecorder.exception;

public abstract class AppException extends Exception {

	public static final int CANT_CREATE_FILE = 1;
	public static final int INVALID_OUTPUT_FILE = 2;
	public static final int RECORDER_INIT_EXCEPTION = 3;
	public static final int PLAYER_INIT_EXCEPTION = 4;
	public static final int PLAYER_DATA_SOURCE_EXCEPTION = 5;
	public static final int CANT_PROCESS_RECORD = 6;
	public static final int READ_PERMISSION_DENIED = 7;
	public static final int NO_SPACE_AVAILABLE = 8;
	public static final int RECORDING_ERROR = 9;
	public static final int FAILED_TO_RESTORE = 10;

	public abstract int getType();
}
