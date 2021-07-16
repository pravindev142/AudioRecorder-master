

package com.thinkdev.audiorecorder.app.main;

import android.content.Context;
import android.net.Uri;
import com.thinkdev.audiorecorder.Contract;
import com.thinkdev.audiorecorder.IntArrayList;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.audio.recorder.RecorderContract;
import com.thinkdev.audiorecorder.data.database.Record;

import java.io.File;
import java.util.List;

public interface MainContract {

	interface View extends Contract.View {

		void keepScreenOn(boolean on);
		void showRecordingStart();
		void showRecordingStop();
		void showRecordingPause();
		void onRecordingProgress(long mills, int amp);
		void startWelcomeScreen();

		void askRecordingNewName(long id, File file,  boolean showCheckbox, final boolean needDecode);

		void startRecordingService();
		void stopRecordingService();

		void startPlaybackService(String name);

		void showPlayStart(boolean animate);
		void showPlayPause();
		void showPlayStop();
		void onPlayProgress(long mills, int px, int percent);

		void showImportStart();
		void hideImportProgress();

		void showOptionsMenu();
		void hideOptionsMenu();

		void showRecordProcessing();
		void hideRecordProcessing();

		void showWaveForm(int[] waveForm, long duration);
		void waveFormToStart();
		void showDuration(String duration);
		void showRecordingProgress(String progress);
		void showName(String name);
		void showInformation(String info);

		void askDeleteRecord(String name);

		void askDeleteRecordForever();

		void showRecordInfo(RecordInfo info);

		void updateRecordingView(IntArrayList data);

		void showRecordsLostMessage(List<Record> list);

		void shareRecord(Record record);

		void openFile(Record record);

		void downloadRecord(Record record);
	}

	interface UserActionsListener extends Contract.UserActionsListener<MainContract.View> {

		void checkFirstRun();

		void setAudioRecorder(RecorderContract.Recorder recorder);

		void startRecording(Context context);
		void stopRecording(boolean deleteRecord);
		void cancelRecording();

		void startPlayback();
		void pausePlayback();
		void seekPlayback(int px);
		void stopPlayback();

		void renameRecord(long id, String name, String extension, boolean needDecode);

		void decodeRecord(long id);

		void loadActiveRecord();

		void dontAskRename();

		void importAudioFile(Context context, Uri uri);

		void updateRecordingDir(Context context);

		void setStoragePrivate(Context context);

		void onShareRecordClick();

		void onRenameRecordClick();

		void onOpenFileClick();

		void onDownloadClick();

		void onDeleteClick();

		//TODO: Remove this getters
		boolean isStorePublic();

		String getActiveRecordPath();

		void deleteActiveRecord(boolean forever);

		void onRecordInfo();

		void disablePlaybackProgressListener();

		void enablePlaybackProgressListener();
	}
}
