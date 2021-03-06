package model;

import java.util.Locale;

import data.Data;

public class Diem implements Data{
	private String maSv, maMh, diem;

	
	public Diem(String maSv, String maMh, String diem) {
		this.maSv = maSv;
		this.maMh = maMh;
		this.diem = diem;
	}


	public String getMaSv() {
		return maSv;
	}


	public void setMaSv(String maSv) {
		this.maSv = maSv;
	}


	public String getMaMh() {
		return maMh;
	}


	public void setMaMh(String maMh) {
		this.maMh = maMh;
	}


	public String getDiem() {
		return diem;
	}


	public void setDiem(String diem) {
		this.diem = diem;
	}


	public void showInfo() {
		System.out.format("[%s - %s - %s]\n",maSv, maMh, diem);
	}


	@Override
	public String getLine() {
		String out = String.format("%s;%s;%s", maSv, maMh, diem);
		return out;
	}
	
	@Override
	public String getColumns() {
		// TODO Auto-generated method stub
		return "# ma sinh vien; ma mon hoc; diem";
	}
	
	
	
}
