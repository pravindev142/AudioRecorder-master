

package com.thinkdev.audiorecorder.app;

import com.thinkdev.audiorecorder.IntArrayList;
import com.thinkdev.audiorecorder.audio.recorder.RecorderContract;
import com.thinkdev.audiorecorder.data.database.Record;

public interface AppRecorder {

	void addRecordingCallback(AppRecorderCallback recorderCallback);
	void removeRecordingCallback(AppRecorderCallback recorderCallback);
	void setRecorder(RecorderContract.Recorder recorder);
	void startRecording(String filePath, int channelCount, int sampleRate, int bitrate);
	void pauseRecording();
	void resumeRecording();
	void stopRecording();
	void decodeRecordWaveform(final Record decRec);
	IntArrayList getRecordingData();
	long getRecordingDuration();
	boolean isRecording();
	boolean isPaused();
	void release();
	boolean isProcessing();
}
