

package com.thinkdev.audiorecorder.app.setup;

import com.thinkdev.audiorecorder.Contract;

public class SetupContract {

	interface View extends Contract.View {

		void showRecordingBitrate(int bitrate);

		void showSampleRate(int rate);

		void showChannelCount(int count);

		void showNamingFormat(String namingKey);

		void showRecordingFormat(String formatKey);

		void showBitrateSelector();

		void hideBitrateSelector();

		void showInformation(int infoResId);

		void showSizePerMin(String size);

		void updateRecordingInfo(String format);
	}

	public interface UserActionsListener extends Contract.UserActionsListener<SetupContract.View> {

		void loadSettings();

		void setSettingRecordingBitrate(int bitrate);

		void setSettingSampleRate(int rate);

		void setSettingChannelCount(int count);

		void setSettingThemeColor(String colorKey);

		void setSettingNamingFormat(String namingKey);

		void setSettingRecordingFormat(String formatKey);

		void executeFirstRun();

		void resetSettings();
	}
}
