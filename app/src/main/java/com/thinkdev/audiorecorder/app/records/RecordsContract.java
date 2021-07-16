

package com.thinkdev.audiorecorder.app.records;

import com.thinkdev.audiorecorder.Contract;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.data.database.Record;

import java.util.List;

public interface RecordsContract {

	interface View extends Contract.View {

		void showPlayStart();
		void showPlayPause();
		void showPlayStop();
		void onPlayProgress(long mills, int px, int percent);

		void showNextRecord();
		void showPrevRecord();

		void showTrashBtn();
		void hideTrashBtn();

		void showPlayerPanel();

		void startPlaybackService();

		void showWaveForm(int[] waveForm, long duration);
		void showDuration(String duration);

		void showRecords(List<ListItem> records, int order);
		void addRecords(List<ListItem> records, int order);

		void showEmptyList();
		void showEmptyBookmarksList();

		void showPanelProgress();
		void hidePanelProgress();

		void showRecordName(String name);

		void showRename(Record record);

		void onDeleteRecord(long id);

		void hidePlayPanel();

		void addedToBookmarks(int id, boolean isActive);
		void removedFromBookmarks(int id, boolean isActive);

		void showSortType(int type);

		void showActiveRecord(int id);

		void bookmarksSelected();
		void bookmarksUnselected();

		void showRecordInfo(RecordInfo info);

		void showRecordsLostMessage(List<Record> list);
	}

	interface UserActionsListener extends Contract.UserActionsListener<RecordsContract.View> {

		void onResumeView();

		void startPlayback();

		void pausePlayback();

		void seekPlayback(int px);

		void stopPlayback();

		void playNext();

		void playPrev();

		void deleteActiveRecord();

		void deleteRecord(long id, String path);

		void renameRecord(long id, String name, String extension);

		void loadRecords();

		void updateRecordsOrder(int order);

		void loadRecordsPage(int page);

		void applyBookmarksFilter();
		void checkBookmarkActiveRecord();

		void addToBookmark(int id);
		void removeFromBookmarks(int id);

		void setActiveRecord(long id, Callback callback);

		void onRenameClick();

		long getActiveRecordId();

		String getActiveRecordPath();

		String getRecordName();

		void onRecordInfo(RecordInfo info);

		void disablePlaybackProgressListener();

		void enablePlaybackProgressListener();
	}

	interface Callback {
		void onSuccess();
		void onError(Exception e);
	}
}
