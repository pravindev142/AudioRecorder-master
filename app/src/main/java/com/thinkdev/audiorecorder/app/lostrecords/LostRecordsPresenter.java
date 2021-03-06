

package com.thinkdev.audiorecorder.app.lostrecords;

import com.thinkdev.audiorecorder.BackgroundQueue;
import com.thinkdev.audiorecorder.Mapper;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.data.Prefs;
import com.thinkdev.audiorecorder.data.database.LocalRepository;
import com.thinkdev.audiorecorder.data.database.OnRecordsLostListener;
import com.thinkdev.audiorecorder.data.database.Record;
import com.thinkdev.audiorecorder.util.AndroidUtils;

import java.util.List;


public class LostRecordsPresenter implements LostRecordsContract.UserActionsListener {

	private LostRecordsContract.View view;
	private final BackgroundQueue loadingTasks;
	private final BackgroundQueue recordingsTasks;
	private final LocalRepository localRepository;
	private final Prefs prefs;

	public LostRecordsPresenter(BackgroundQueue loadingTasks, BackgroundQueue recordingsTasks,
										 LocalRepository localRepository, Prefs prefs) {
		this.loadingTasks = loadingTasks;
		this.recordingsTasks = recordingsTasks;
		this.localRepository = localRepository;
		this.prefs = prefs;
	}

	@Override
	public void bindView(LostRecordsContract.View v) {
		this.view = v;

		localRepository.setOnRecordsLostListener(new OnRecordsLostListener() {
			@Override
			public void onLostRecords(final List<Record> lostRecords) {
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						List<RecordItem> list = Mapper.toRecordItemList(lostRecords);
						if (view != null) {
							if (list.isEmpty()) {
								view.showEmpty();
							} else {
								view.showLostRecords(list);
								view.hideEmpty();
							}
						}
					}
				});
			}
		});
		loadingTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				localRepository.getAllRecords();
			}
		});
	}

	@Override
	public void unbindView() {
		this.localRepository.setOnRecordsLostListener(null);
		this.view = null;
	}

	@Override
	public void clear() {
		unbindView();
	}

	@Override
	public void deleteRecords(final List<RecordItem> list) {
		recordingsTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				for (RecordItem rec : list) {
					localRepository.deleteRecord(rec.getId());
//					fileRepository.deleteRecordFile(rec.getPath());
					if (prefs.getActiveRecord() == rec.getId()) {
						prefs.setActiveRecord(-1);
					}
				}
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						if (view != null) {
							view.showEmpty();
						}
					}
				});
			}
		});
	}

	@Override
	public void onRecordInfo(RecordInfo info) {
		if (view != null) {
			view.showRecordInfo(info);
		}
	}

	@Override
	public void deleteRecord(final RecordItem rec) {
		recordingsTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				localRepository.deleteRecord(rec.getId());
//				fileRepository.deleteRecordFile(rec.getPath());
				if (prefs.getActiveRecord() == rec.getId()) {
					prefs.setActiveRecord(-1);
				}
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						if (view != null) {
							view.onDeletedRecord(rec.getId());
						}
					}
				});
			}
		});
	}

}
