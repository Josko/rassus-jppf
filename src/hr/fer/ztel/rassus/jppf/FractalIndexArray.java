package hr.fer.ztel.rassus.jppf;

import java.io.Serializable;

public class FractalIndexArray implements Serializable {
	private static final long serialVersionUID = 4283271437921586768L;
	
	private final int yMin;
	private final int yMax;
	private final short[] data;
	
	public FractalIndexArray(int reMin, int reMax, short[] data) {
		this.yMin = reMin;
		this.yMax = reMax;
		this.data = data;
	}
	
	public int getYMin() {
		return this.yMin;
	}
	
	public int getYMax() {
		return this.yMax;
	}
	
	public short[] getData() {
		return this.data;
	}
	
	public short getDataAt(int index) {
		return this.data[index];
	}
	
	public int getDataSize() {
		return this.data.length;
	}
 }
