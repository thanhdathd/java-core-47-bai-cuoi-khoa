package model;
import java.util.Locale;

public class MonHoc {
	int code;
	static int currentCode = 1;
	private String name;
	private float hs;
	public MonHoc(String name, float hs) {
		this.code = currentCode;
		this.name = name;
		this.hs = hs;
		currentCode++;
	}
	
	public MonHoc(int code, String name, float hs) {
		this.code = code;
		this.name = name;
		this.hs = hs;
		if(code > currentCode) currentCode = code;
	}
	
	
	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHs() {
		return hs;
	}

	public void setHs(float hs) {
		this.hs = hs;
	}

	@Override
	public String toString() {
		Locale us = Locale.US;
		String out = String.format(us,"%03d;%s;%.1f", code, name, hs);
		return out;
	}

	public void showInfo() {
		System.out.format("[%03d  %s  %-3.1f]\n", code, name, hs);
	}

	public String getStringCode() {
		String out = String.format("%03d", code);
		return out;
	}

	public String getInfo() {
		String out = String.format(" %03d  %25s    %3.1f", code, name, hs);
		return out;
	}
	
	
	
}
