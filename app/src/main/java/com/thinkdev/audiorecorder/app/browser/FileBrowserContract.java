

package com.thinkdev.audiorecorder.app.browser;

import android.content.Context;

import com.thinkdev.audiorecorder.Contract;
import com.thinkdev.audiorecorder.app.info.RecordInfo;

import java.util.List;

public interface FileBrowserContract {

	interface View extends Contract.View {
		void showFileItems(List<RecordInfo> items);
		void showSelectedPrivateDir();
		void showSelectedPublicDir();
		void showRecordInfo(RecordInfo info);
		void onDeletedRecord(String path);
		void onImportedRecord(String path);
		void updatePath(String path);

		void showEmpty();
		void hideEmpty();

		void showRecordProcessing();
		void hideRecordProcessing();

		void showImportStart();
		void hideImportProgress();
	}

	interface UserActionsListener extends Contract.UserActionsListener<FileBrowserContract.View> {
		void selectPrivateDir(Context context);
		void selectPublicDir(Context context);
		void loadFiles(Context context);
		void onRecordInfo(RecordInfo info);
		void deleteRecord(RecordInfo record);
		void importAudioFile(Context context, RecordInfo info);
	}
}
