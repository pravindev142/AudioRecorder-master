

package com.thinkdev.audiorecorder.data;

public interface Prefs {

	boolean isFirstRun();
	void firstRunExecuted();

	boolean isStoreDirPublic();
	void setStoreDirPublic(boolean b);

	boolean isAskToRenameAfterStopRecording();
	boolean hasAskToRenameAfterStopRecordingSetting();
	void setAskToRenameAfterStopRecording(boolean b);

	long getActiveRecord();
	void setActiveRecord(long id);

	long getRecordCounter();
	void incrementRecordCounter();

	void setKeepScreenOn(boolean on);
	boolean isKeepScreenOn();

	void setRecordOrder(int order);
	int getRecordsOrder();

	boolean isMigratedSettings();
	void migrateSettings();

	boolean isMigratedDb3();
	void migrateDb3Finished();

	void setSettingThemeColor(String colorKey);
	String getSettingThemeColor();

	void setSettingNamingFormat(String nameKay);
	String getSettingNamingFormat();

	void setSettingRecordingFormat(String formatKey);
	String getSettingRecordingFormat();

	void setSettingSampleRate(int sampleRate);
	int getSettingSampleRate();

	void setSettingBitrate(int rate);
	int getSettingBitrate();

	void setSettingChannelCount(int count);
	int getSettingChannelCount();

	void resetSettings();
}
