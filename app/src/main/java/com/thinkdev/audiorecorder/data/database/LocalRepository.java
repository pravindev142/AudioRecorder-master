

package com.thinkdev.audiorecorder.data.database;

import com.thinkdev.audiorecorder.exception.FailedToRestoreRecord;

import java.io.IOException;
import java.util.List;

public interface LocalRepository {

	void open();

	void close();

	Record getRecord(int id);

	Record findRecordByPath(String path);

	Record getTrashRecord(int id);

	List<Record> getAllRecords();

	List<Integer> getAllItemsIds();

	List<Record> getRecords(int page);

	List<Record> getRecords(int page, int order);

	boolean deleteAllRecords();

	Record getLastRecord();

	Record insertRecord(Record record);

	boolean updateRecord(Record record);

	boolean updateTrashRecord(Record record);

	Record insertEmptyFile(String filePath) throws IOException;

	void deleteRecord(int id);

	void deleteRecordForever(int id);

	List<Long> getRecordsDurations();

	boolean addToBookmarks(int id);

	boolean removeFromBookmarks(int id);

	List<Record> getBookmarks();

	List<Record> getTrashRecords();

	List<Integer> getTrashRecordsIds();

	int getTrashRecordsCount();

	void restoreFromTrash(int id) throws FailedToRestoreRecord;

	boolean removeFromTrash(int id);

	boolean emptyTrash();

	void removeOutdatedTrashRecords();

	void setOnRecordsLostListener(OnRecordsLostListener listener);
}
