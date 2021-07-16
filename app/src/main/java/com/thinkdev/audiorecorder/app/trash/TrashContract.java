
package com.thinkdev.audiorecorder.app.trash;

import com.thinkdev.audiorecorder.Contract;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.app.lostrecords.RecordItem;

import java.util.List;

public interface TrashContract {

	interface View extends Contract.View {
		void showRecords(List<RecordItem> items);
		void showRecordInfo(RecordInfo info);
		void recordDeleted(int resId);
		void recordRestored(int resId);
		void allRecordsRemoved();
		void showEmpty();
		void hideEmpty();
	}

	interface UserActionsListener extends Contract.UserActionsListener<TrashContract.View> {
		void onRecordInfo(RecordInfo info);
		void deleteRecordFromTrash(final int id, final String path);
		void deleteAllRecordsFromTrash();
		void restoreRecordFromTrash(final int id);
	}
}
