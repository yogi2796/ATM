package com.zoho;

import java.io.Serializable;

class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accHolder;
	private int accNo;
	private long accBal;
	private int pinNo;

	public Customer() {
		this.accNo = 0;
		this.accHolder = "";
		this.pinNo = 0;
		this.accBal = 0;
	}

	Customer(int accNo, String accHolder, int pinNo, long accBal) {
		this.accNo = accNo;
		this.accHolder = accHolder;
		this.pinNo = pinNo;
		this.accBal = accBal;
	}

	public String getAccHolder() {
		return accHolder;
	}

	public void setAccHolder(String accHolder) {
		this.accHolder = accHolder;
	}

	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public int getPinNo() {
		return pinNo;
	}

	public void setPinNo(int pinNo) {
		this.pinNo = pinNo;
	}

	public long getAccBal() {
		return accBal;
	}

	public void setAccBal(long accBal) {
		this.accBal = accBal;
	}
}
