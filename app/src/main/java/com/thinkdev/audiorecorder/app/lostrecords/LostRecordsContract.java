
package com.thinkdev.audiorecorder.app.lostrecords;

import com.thinkdev.audiorecorder.Contract;
import com.thinkdev.audiorecorder.app.info.RecordInfo;

import java.util.List;

public interface LostRecordsContract {

	interface View extends Contract.View {
		void showLostRecords(List<RecordItem> items);
		void showRecordInfo(RecordInfo info);
		void onDeletedRecord(int id);
		void showEmpty();
		void hideEmpty();
	}

	interface UserActionsListener extends Contract.UserActionsListener<LostRecordsContract.View> {
		void onRecordInfo(RecordInfo info);
		void deleteRecord(RecordItem record);
		void deleteRecords(List<RecordItem> list);
	}
}
