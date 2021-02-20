package com.zoho;

import java.io.Serializable;

class ATMMachineMoney implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int denomination;
	private int count;
	private long total;

	public ATMMachineMoney() {
		this.denomination = 0;
		this.count = 0;
		this.total = 0;
	}

	public ATMMachineMoney(int count, long total, int denomination) {
		this.denomination = denomination;
		this.count = count;
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getDenomination() {
		return denomination;
	}

	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}
}