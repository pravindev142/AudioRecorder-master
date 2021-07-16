package com.thinkdev.audiorecorder.data.database;

import java.util.List;

public interface OnRecordsLostListener {
	void onLostRecords(List<Record> list);
}
