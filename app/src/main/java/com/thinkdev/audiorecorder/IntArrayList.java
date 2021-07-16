

package com.thinkdev.audiorecorder;

public class IntArrayList {

	private int[] data = new int[100];
	private int size = 0;

	public void add(int val) {
		if (data.length == size) {
			grow();
			add(val);
		}
		data[size] = val;
		size++;
	}

	public int get(int index) {
		return data[index];
	}

	public int[] getData() {
		int [] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = data[i];
		}
		return arr;
	}

	public void clear() {
		data = new int[100];
		size = 0;
	}

	public int size() {
		return size;
	}

	private void grow() {
		int[] backup = data;
		data = new int[size*2];
		for (int i = 0; i < backup.length; i++) {
			data[i] = backup[i];
		}
	}
}
