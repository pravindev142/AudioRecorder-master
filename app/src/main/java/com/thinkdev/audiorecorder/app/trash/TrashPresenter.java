

package com.thinkdev.audiorecorder.app.trash;

import com.thinkdev.audiorecorder.BackgroundQueue;
import com.thinkdev.audiorecorder.Mapper;
import com.thinkdev.audiorecorder.R;
import com.thinkdev.audiorecorder.app.info.RecordInfo;
import com.thinkdev.audiorecorder.app.lostrecords.RecordItem;
import com.thinkdev.audiorecorder.data.FileRepository;
import com.thinkdev.audiorecorder.data.database.LocalRepository;
import com.thinkdev.audiorecorder.data.database.Record;
import com.thinkdev.audiorecorder.exception.ErrorParser;
import com.thinkdev.audiorecorder.exception.FailedToRestoreRecord;
import com.thinkdev.audiorecorder.util.AndroidUtils;

import java.util.List;

public class TrashPresenter implements TrashContract.UserActionsListener {

	private TrashContract.View view;
	private final BackgroundQueue loadingTasks;
	private final BackgroundQueue recordingsTasks;
	private final FileRepository fileRepository;
	private final LocalRepository localRepository;

	public TrashPresenter(BackgroundQueue loadingTasks, BackgroundQueue recordingsTasks,
								 FileRepository fileRepository, LocalRepository localRepository) {
		this.loadingTasks = loadingTasks;
		this.recordingsTasks = recordingsTasks;
		this.fileRepository = fileRepository;
		this.localRepository = localRepository;
	}

	@Override
	public void bindView(final TrashContract.View v) {
		this.view = v;

		loadingTasks.postRunnable(new Runnable() {
			@Override public void run() {
				final List<RecordItem> list = Mapper.toRecordItemList(localRepository.getTrashRecords());
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						if (view != null) {
							if (list.isEmpty()) {
								view.showEmpty();
							} else {
								view.showRecords(list);
								view.hideEmpty();
							}
						}
					}
				});
			}
		});
	}

	@Override
	public void unbindView() {
		this.view = null;
	}

	@Override
	public void clear() {
		unbindView();
	}

	@Override
	public void onRecordInfo(RecordInfo info) {
		if (view != null) {
			view.showRecordInfo(info);
		}
	}

	@Override
	public void deleteRecordFromTrash(final int id, final String path) {
		recordingsTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				if (fileRepository.deleteRecordFile(path)) {
					removeFromTrash(id);
				} else if (fileRepository.deleteRecordFile(path)) { //Try to delete again.
					removeFromTrash(id);
				} else {
					AndroidUtils.runOnUIThread(new Runnable() {
						@Override
						public void run() {
							if (view != null) {
								view.showMessage(R.string.error_failed_to_delete);
							}
						}
					});
				}
			}
		});
	}

	private void removeFromTrash(final int id) {
		localRepository.removeFromTrash(id);
		AndroidUtils.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				if (view != null) {
					view.showMessage(R.string.record_deleted_successfully);
					view.recordDeleted(id);
				}
			}
		});
	}

	@Override
	public void deleteAllRecordsFromTrash() {
		recordingsTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				List<Record> records  = localRepository.getTrashRecords();
				for (int i = 0; i < records.size(); i++) {
					fileRepository.deleteRecordFile(records.get(i).getPath());
				}
				localRepository.emptyTrash();
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						if (view != null) {
							view.showMessage(R.string.all_records_deleted_successfully);
							view.allRecordsRemoved();
						}
					}
				});
			}
		});
	}

	@Override
	public void restoreRecordFromTrash(final int id) {
		recordingsTasks.postRunnable(new Runnable() {
			@Override
			public void run() {
				try {
					localRepository.restoreFromTrash(id);
				} catch (final FailedToRestoreRecord e) {
					AndroidUtils.runOnUIThread(new Runnable() {
						@Override
						public void run() {
							if (view != null) {
								view.showMessage(ErrorParser.parseException(e));
							}
						}
					});
				}
				AndroidUtils.runOnUIThread(new Runnable() {
					@Override
					public void run() {
						if (view != null) {
							view.showMessage(R.string.record_restored_successfully);
							view.recordRestored(id);
						}
					}
				});
			}
		});
	}
}
