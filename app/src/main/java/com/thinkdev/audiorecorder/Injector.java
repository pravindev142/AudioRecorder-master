

package com.thinkdev.audiorecorder;

import android.content.Context;

import com.thinkdev.audiorecorder.app.AppRecorder;
import com.thinkdev.audiorecorder.app.AppRecorderImpl;
import com.thinkdev.audiorecorder.app.browser.FileBrowserContract;
import com.thinkdev.audiorecorder.app.browser.FileBrowserPresenter;
import com.thinkdev.audiorecorder.app.lostrecords.LostRecordsContract;
import com.thinkdev.audiorecorder.app.lostrecords.LostRecordsPresenter;
import com.thinkdev.audiorecorder.app.settings.SettingsMapper;
import com.thinkdev.audiorecorder.app.setup.SetupContract;
import com.thinkdev.audiorecorder.app.setup.SetupPresenter;
import com.thinkdev.audiorecorder.app.trash.TrashContract;
import com.thinkdev.audiorecorder.app.trash.TrashPresenter;
import com.thinkdev.audiorecorder.audio.player.AudioPlayer;
import com.thinkdev.audiorecorder.audio.player.PlayerContract;
import com.thinkdev.audiorecorder.audio.recorder.AudioRecorder;
import com.thinkdev.audiorecorder.audio.recorder.ThreeGpRecorder;
import com.thinkdev.audiorecorder.audio.recorder.RecorderContract;
import com.thinkdev.audiorecorder.audio.recorder.WavRecorder;
import com.thinkdev.audiorecorder.data.FileRepository;
import com.thinkdev.audiorecorder.data.FileRepositoryImpl;
import com.thinkdev.audiorecorder.data.Prefs;
import com.thinkdev.audiorecorder.data.PrefsImpl;
import com.thinkdev.audiorecorder.data.database.LocalRepository;
import com.thinkdev.audiorecorder.data.database.LocalRepositoryImpl;
import com.thinkdev.audiorecorder.data.database.RecordsDataSource;
import com.thinkdev.audiorecorder.app.main.MainContract;
import com.thinkdev.audiorecorder.app.main.MainPresenter;
import com.thinkdev.audiorecorder.app.records.RecordsContract;
import com.thinkdev.audiorecorder.app.records.RecordsPresenter;
import com.thinkdev.audiorecorder.app.settings.SettingsContract;
import com.thinkdev.audiorecorder.app.settings.SettingsPresenter;
import com.thinkdev.audiorecorder.data.database.TrashDataSource;

public class Injector {

	private Context context;

	private BackgroundQueue loadingTasks;
	private BackgroundQueue recordingTasks;
	private BackgroundQueue importTasks;
	private BackgroundQueue processingTasks;
	private BackgroundQueue copyTasks;

	private MainContract.UserActionsListener mainPresenter;
	private RecordsContract.UserActionsListener recordsPresenter;
	private SettingsContract.UserActionsListener settingsPresenter;
	private LostRecordsContract.UserActionsListener lostRecordsPresenter;
	private FileBrowserContract.UserActionsListener fileBrowserPresenter;
	private TrashContract.UserActionsListener trashPresenter;
	private SetupContract.UserActionsListener setupPresenter;

	public Injector(Context context) {
		this.context = context;
	}

	public Prefs providePrefs() {
		return PrefsImpl.getInstance(context);
	}

	public RecordsDataSource provideRecordsDataSource() {
		return RecordsDataSource.getInstance(context);
	}

	public TrashDataSource provideTrashDataSource() {
		return TrashDataSource.getInstance(context);
	}

	public FileRepository provideFileRepository() {
		return FileRepositoryImpl.getInstance(context, providePrefs());
	}

	public LocalRepository provideLocalRepository() {
		return LocalRepositoryImpl.getInstance(provideRecordsDataSource(), provideTrashDataSource(), provideFileRepository(), providePrefs());
	}

	public AppRecorder provideAppRecorder() {
		return AppRecorderImpl.getInstance(provideAudioRecorder(), provideLocalRepository(),
				provideLoadingTasksQueue(), provideProcessingTasksQueue(), providePrefs());
	}

	public BackgroundQueue provideLoadingTasksQueue() {
		if (loadingTasks == null) {
			loadingTasks = new BackgroundQueue("LoadingTasks");
		}
		return loadingTasks;
	}

	public BackgroundQueue provideRecordingTasksQueue() {
		if (recordingTasks == null) {
			recordingTasks = new BackgroundQueue("RecordingTasks");
		}
		return recordingTasks;
	}

	public BackgroundQueue provideImportTasksQueue() {
		if (importTasks == null) {
			importTasks = new BackgroundQueue("ImportTasks");
		}
		return importTasks;
	}

	public BackgroundQueue provideProcessingTasksQueue() {
		if (processingTasks == null) {
			processingTasks = new BackgroundQueue("ProcessingTasks");
		}
		return processingTasks;
	}

	public BackgroundQueue provideCopyTasksQueue() {
		if (copyTasks == null) {
			copyTasks = new BackgroundQueue("CopyTasks");
		}
		return copyTasks;
	}

	public ColorMap provideColorMap() {
		return ColorMap.getInstance(providePrefs());
	}

	public SettingsMapper provideSettingsMapper() {
		return SettingsMapper.getInstance(context);
	}

	public PlayerContract.Player provideAudioPlayer() {
		return AudioPlayer.getInstance();
	}

	public RecorderContract.Recorder provideAudioRecorder() {
		switch (providePrefs().getSettingRecordingFormat()) {
			default:
			case AppConstants.FORMAT_M4A:
				return AudioRecorder.getInstance();
			case AppConstants.FORMAT_WAV:
				return WavRecorder.getInstance();
			case AppConstants.FORMAT_3GP:
				return ThreeGpRecorder.getInstance();
		}
	}

	public MainContract.UserActionsListener provideMainPresenter() {
		if (mainPresenter == null) {
			mainPresenter = new MainPresenter(providePrefs(), provideFileRepository(),
					provideLocalRepository(), provideAudioPlayer(), provideAppRecorder(),
					provideLoadingTasksQueue(), provideRecordingTasksQueue(), provideProcessingTasksQueue(),
					provideImportTasksQueue(), provideSettingsMapper());
		}
		return mainPresenter;
	}

	public RecordsContract.UserActionsListener provideRecordsPresenter() {
		if (recordsPresenter == null) {
			recordsPresenter = new RecordsPresenter(provideLocalRepository(), provideFileRepository(),
					provideLoadingTasksQueue(), provideRecordingTasksQueue(),
					provideAudioPlayer(), provideAppRecorder(), providePrefs());
		}
		return recordsPresenter;
	}

	public SettingsContract.UserActionsListener provideSettingsPresenter() {
		if (settingsPresenter == null) {
			settingsPresenter = new SettingsPresenter(provideLocalRepository(), provideFileRepository(),
					provideRecordingTasksQueue(), provideLoadingTasksQueue(), providePrefs(),
					provideSettingsMapper(), provideAppRecorder());
		}
		return settingsPresenter;
	}

	public TrashContract.UserActionsListener provideTrashPresenter() {
		if (trashPresenter == null) {
			trashPresenter = new TrashPresenter(provideLoadingTasksQueue(), provideRecordingTasksQueue(),
					provideFileRepository(), provideLocalRepository());
		}
		return trashPresenter;
	}

	public SetupContract.UserActionsListener provideSetupPresenter() {
		if (setupPresenter == null) {
			setupPresenter = new SetupPresenter(providePrefs());
		}
		return setupPresenter;
	}

	public LostRecordsContract.UserActionsListener provideLostRecordsPresenter() {
		if (lostRecordsPresenter == null) {
			lostRecordsPresenter = new LostRecordsPresenter(provideLoadingTasksQueue(), provideRecordingTasksQueue(),
					provideLocalRepository(), providePrefs());
		}
		return lostRecordsPresenter;
	}

	public FileBrowserContract.UserActionsListener provideFileBrowserPresenter() {
		if (fileBrowserPresenter == null) {
			fileBrowserPresenter = new FileBrowserPresenter(providePrefs(), provideAppRecorder(), provideImportTasksQueue(),
					provideLoadingTasksQueue(), provideRecordingTasksQueue(),
					provideLocalRepository(), provideFileRepository());
		}
		return fileBrowserPresenter;
	}

	public void releaseTrashPresenter() {
		if (trashPresenter != null) {
			trashPresenter.clear();
			trashPresenter = null;
		}
	}

	public void releaseLostRecordsPresenter() {
		if (lostRecordsPresenter != null) {
			lostRecordsPresenter.clear();
			lostRecordsPresenter = null;
		}
	}

	public void releaseFileBrowserPresenter() {
		if (fileBrowserPresenter != null) {
			fileBrowserPresenter.clear();
			fileBrowserPresenter = null;
		}
	}

	public void releaseRecordsPresenter() {
		if (recordsPresenter != null) {
			recordsPresenter.clear();
			recordsPresenter = null;
		}
	}

	public void releaseMainPresenter() {
		if (mainPresenter != null) {
			mainPresenter.clear();
			mainPresenter = null;
		}
	}

	public void releaseSettingsPresenter() {
		if (settingsPresenter != null) {
			settingsPresenter.clear();
			settingsPresenter = null;
		}
	}

	public void releaseSetupPresenter() {
		if (setupPresenter != null) {
			setupPresenter.clear();
			setupPresenter = null;
		}
	}

	public void closeTasks() {
		loadingTasks.cleanupQueue();
		loadingTasks.close();
		importTasks.cleanupQueue();
		importTasks.close();
		processingTasks.cleanupQueue();
		processingTasks.close();
		recordingTasks.cleanupQueue();
		recordingTasks.close();
	}
}
