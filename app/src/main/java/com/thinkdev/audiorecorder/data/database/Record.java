
package com.thinkdev.audiorecorder.data.database;

import com.thinkdev.audiorecorder.AppConstants;

import java.util.Arrays;

import androidx.annotation.NonNull;

public class Record {

	public static final int NO_ID = -1;
//	private static final String DELIMITER = ",";

	private int id;
	private String name;
	private long duration;
	private long created;
	private long added;
	private long removed;
	private String path;
	private String format;
	private long size;
	private int sampleRate;
	private int channelCount;
	private int bitrate;
	private boolean bookmark;
	private boolean waveformProcessed;
	private int[] amps;
	private byte[] data;
	//TODO: Remove not needed data clusters.

	public Record(int id, String name, long duration, long created, long added, long removed, String path,
					  String format, long size, int sampleRate, int channelCount, int bitrate,
					  boolean bookmark, boolean waveformProcessed, int[] amps) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.created = created;
		this.added= added;
		this.removed = removed;
		this.path = path;
		this.format = format;
		this.size = size;
		this.sampleRate = sampleRate;
		this.channelCount = channelCount;
		this.bitrate = bitrate;
		this.bookmark = bookmark;
		this.waveformProcessed = waveformProcessed;
		this.amps = amps;
		this.data = int2byte(amps);
//		this.data = AndroidUtils.int2byte(amps);
	}

	public Record(int id, String name, long duration, long created, long added, long removed, String path,
					  String format, long size, int sampleRate, int channelCount, int bitrate,
					  boolean bookmark, boolean waveformProcessed, byte[] amps) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.created = created;
		this.added = added;
		this.removed = removed;
		this.path = path;
		this.format = format;
		this.size = size;
		this.sampleRate = sampleRate;
		this.channelCount = channelCount;
		this.bitrate = bitrate;
		this.bookmark = bookmark;
		this.waveformProcessed = waveformProcessed;
		this.amps = byte2int(amps);
//		this.amps = AndroidUtils.byte2int(amps);
		this.data = amps;
	}

	public byte[] int2byte(int[] amps) {
		byte[] bytes = new byte[amps.length];
		for (int i = 0; i < amps.length; i++) {
			if (amps[i] >= 255) {
				bytes[i] = 127;
			} else if (amps[i] < 0) {
				bytes[i] = 0;
			} else {
				bytes[i] = (byte)(amps[i]-128);
			}
		}
		return bytes;
	}

	public int[] byte2int(byte[] amps) {
		int[] ints = new int[amps.length];
		for (int i = 0; i < amps.length; i++) {
			ints[i] = amps[i]+128;
		}
		return ints;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNameWithExtension() {
		return name + AppConstants.EXTENSION_SEPARATOR + format;
	}

	public long getCreated() {
		return created;
	}

	public long getAdded() {
		return added;
	}

	public long getRemoved() {
		return removed;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFormat() {
		return format;
	}

	public long getSize() {
		return size;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getChannelCount() {
		return channelCount;
	}

	public int getBitrate() {
		return bitrate;
	}

	public int[] getAmps() {
		return amps;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public byte[] getData() {
		return data;
	}

	public boolean isBookmarked() {
		return bookmark;
	}

	public boolean isWaveformProcessed() {
		return waveformProcessed;
	}

	public void setBookmark(boolean b) {
		this.bookmark = b;
	}

//	public static int[] stringToArray(String groups) {
//		if (groups != null && !groups.isEmpty()) {
//			String[] grStr = groups.split(DELIMITER);
//			int[] grInt = new int[grStr.length];
//			for (int i = 0; i < grStr.length; i++) {
//				try {
//					grInt[i] = Integer.parseInt(grStr[i]);
//				} catch (NumberFormatException e) {
//					Timber.e(e);
//				}
//			}
//			return grInt;
//		}
//		return new int[0];
//	}
//
//	public static String arrayToString(int[] tokens) {
//		StringBuilder sb = new StringBuilder();
//		boolean firstTime = true;
//		for (Object token: tokens) {
//			if (firstTime) {
//				firstTime = false;
//			} else {
//				sb.append(DELIMITER);
//			}
//			sb.append(token);
//		}
//		return sb.toString();
//	}

	@NonNull
	@Override
	public String toString() {
		return "Record{" +
				"id=" + id +
				", name='" + name + '\'' +
				", duration=" + duration +
				", created=" + created +
				", added=" + added +
				", removed=" + removed +
				", path='" + path + '\'' +
				", format='" + format + '\'' +
				", size=" + size +
				", sampleRate=" + sampleRate +
				", channelCount=" + channelCount +
				", bitrate=" + bitrate +
				", bookmark=" + bookmark +
				", waveformProcessed=" + waveformProcessed +
				", amps=" + Arrays.toString(amps) +
				", data=" + Arrays.toString(data) +
				'}';
	}
}
