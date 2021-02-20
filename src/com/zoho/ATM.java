package com.zoho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class ATM {
	public static String PATH_ATM_BAL = "ATMBalance.txt";
	public static String PATH_CUSTOMER = "Customer.txt";
	public static NumberFormat myFormat = NumberFormat.getInstance();

	// Entry point
	public static void main(String a[]) throws NumberFormatException, IOException {
		ATM atm = new ATM();
		Customer cust[] = new Customer[5];
		cust = atm.customerInitalize(cust);
		atm.storeObject(PATH_CUSTOMER, null, cust);

		Scanner n = new Scanner(System.in);
		boolean flag = true;

		while (flag) {
			System.out.println(
					"\n 1. Load Cash to ATM \n 2. Show Customer Details \n 3. Show ATM Operations \n 4. Exit ");
			System.out.print("Select Option: ");
			int choice = n.nextInt();
			switch (choice) {
			case 1:
				atm.loadCashToATM(atm, n);
				break;
			case 2:
				atm.showCustDetails(atm, cust);
				break;
			case 3:
				atm.showATMOperation(atm, n, cust);
				break;
			case 4:
				System.out.print("bye");
				return;
			default:
				System.out.println("Please choose correct option");
				break;
			}
		}
		n.close();
	}

	// Storing objects for both ATM Machine Balance and Customer
	public void storeObject(String filePath, HashMap<Integer, ATMMachineMoney> map, Customer cust[]) {
		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ops = new FileOutputStream(filePath);
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(map != null ? map : cust);
			objOps.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOps != null)
					objOps.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	// Display Table of ATM Machine Balance
	@SuppressWarnings("unchecked")
	public void displayObjects() {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		try {
			fileIs = new FileInputStream(PATH_ATM_BAL);
			objIs = new ObjectInputStream(fileIs);
			HashMap<Integer, ATMMachineMoney> map = (HashMap<Integer, ATMMachineMoney>) objIs.readObject();
			ATMMachineMoney atm[] = new ATMMachineMoney[3];
			int j = 0;
			for (Integer key : map.keySet()) {
				atm[j] = map.get(key);
				j++;
			}

			System.out.println("-------------------------------------");
			System.out.println("| Denomination |" + " Number |" + " Value     |");
			System.out.println("-------------------------------------");
			for (int i = 0; i < atm.length; i++) {
				System.out.format("| %-12d | %-6d | %-9d |", atm[i].getDenomination(), atm[i].getCount(),
						atm[i].getTotal());
				System.out.println();
			}
			System.out.println("-------------------------------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null)
					objIs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	// Display Table of Customer
	public void displayObjects(Customer cust[]) {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		try {
			fileIs = new FileInputStream(PATH_CUSTOMER);
			objIs = new ObjectInputStream(fileIs);
			cust = (Customer[]) objIs.readObject();
			System.out.println("------------------------------------------------------------");
			System.out.println("| Acc No |" + " Account Holder |" + " Pin Number |" + " Account Balance  |");
			System.out.println("------------------------------------------------------------");
			for (int i = 0; i < cust.length; i++) {
				System.out.format(Locale.ENGLISH, "| %-6d | %-14s | %-10d | %-16s₹ |", cust[i].getAccNo(),
						cust[i].getAccHolder(), cust[i].getPinNo(), myFormat.format(cust[i].getAccBal()));
				System.out.println();
			}
			System.out.println("-----------------------------------------------------------");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null)
					objIs.close();
			} catch (Exception ex) {

			}
		}

	}

	// Create a file called Customer if it not exists
	public Customer[] customerInitalize(Customer cust[]) {
		File f = new File("Customer.txt");
		if (f.exists()) {
			Customer oldCust[] = getcustomerDetails();
			return oldCust;
		} else {
			cust[0] = new Customer(101, "Suresh", 2343, 25234);
			cust[1] = new Customer(102, "Ganesh", 5432, 34123);
			cust[2] = new Customer(103, "Magesh", 7854, 26100);
			cust[3] = new Customer(104, "Naresh", 2345, 80000);
			cust[4] = new Customer(105, "Harish", 1907, 103400);
			return cust;
		}

	}

	// Load Cash to ATM
	@SuppressWarnings("unchecked")
	private void loadCashToATM(ATM atm, Scanner in) {
		File f = new File(PATH_ATM_BAL);
		HashMap<Integer, ATMMachineMoney> newMap = new HashMap<Integer, ATMMachineMoney>();
		HashMap<Integer, ATMMachineMoney> map = new HashMap<Integer, ATMMachineMoney>();
		if (f.exists()) {
			InputStream fileIs = null;
			ObjectInputStream objIs = null;
			try {
				fileIs = new FileInputStream(PATH_ATM_BAL);
				objIs = new ObjectInputStream(fileIs);
				map = (HashMap<Integer, ATMMachineMoney>) objIs.readObject();
				newMap = atm.addMoney(in);
				ATMMachineMoney oldAtm[] = new ATMMachineMoney[3];
				ATMMachineMoney newAtm[] = new ATMMachineMoney[3];
				int j = 0;
				for (Integer key : map.keySet()) {
					oldAtm[j] = new ATMMachineMoney();
					oldAtm[j] = map.get(key);
					j++;
				}
				int i = 0;
				for (Integer key : newMap.keySet()) {
					newAtm[i] = new ATMMachineMoney();
					newAtm[i] = newMap.get(key);
					i++;
				}
				for (int c = 0; c < newAtm.length; c++) {
					newAtm[c].setCount(oldAtm[c].getCount() + newAtm[c].getCount());
					newAtm[c].setTotal(oldAtm[c].getTotal() + newAtm[c].getTotal());
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (objIs != null)
						objIs.close();
				} catch (Exception ex) {

				}
			}
		} else {
			newMap = atm.addMoney(in);
		}
		atm.storeObject(PATH_ATM_BAL, newMap, null);
		System.out.println();
		atm.displayObjects();
	}

	// Add money for load cash to ATM
	public HashMap<Integer, ATMMachineMoney> addMoney(Scanner in) {
		HashMap<Integer, ATMMachineMoney> map = new HashMap<Integer, ATMMachineMoney>();
		ATMMachineMoney atmMoney[] = new ATMMachineMoney[3];
		int count = 0;
		long total = 0;

		atmMoney[0] = new ATMMachineMoney();
		System.out.println("Feed money into the machine");
		System.out.print("2000 X ");
		atmMoney[0].setDenomination(2000);
		count = in.nextInt();
		atmMoney[0].setCount(count);
		total = 2000 * count;
		atmMoney[0].setTotal(total);
		map.put(2000, atmMoney[0]);

		atmMoney[1] = new ATMMachineMoney();
		System.out.println("Feed money into the machine");
		System.out.print("500 X ");
		atmMoney[1].setDenomination(500);
		count = in.nextInt();
		atmMoney[1].setCount(count);
		total = 500 * count;
		atmMoney[1].setTotal(total);
		map.put(500, atmMoney[1]);

		atmMoney[2] = new ATMMachineMoney();
		System.out.println("Feed money into the machine");
		System.out.print("100 X ");
		atmMoney[2].setDenomination(100);
		count = in.nextInt();
		atmMoney[2].setCount(count);
		total = 100 * count;
		atmMoney[2].setTotal(total);
		map.put(100, atmMoney[2]);

		return map;
	}

	// Showing Customer Details
	private void showCustDetails(ATM atm, Customer cust[]) {
		atm.displayObjects(cust);
	}

	// Showing ATM Operation Menu
	private void showATMOperation(ATM atm, Scanner in, Customer cust[]) {
		int accNo, pwd;
		System.out.print("Enter Account Number: ");
		accNo = in.nextInt();
		System.out.print("Enter Pin: ");
		pwd = in.nextInt();
		Customer cus = atm.isAuthenticate(accNo, pwd);
		boolean isfirstWithdraw = true;
		if (cus != null) {
			System.out.println("welcome " + cus.getAccHolder());
			boolean session = true;
			while (session) {
				System.out.println(
						"\n 1. Check Balance \n 2. Withdraw Money \n 3. Transfer Money \n 4. Check ATM Balance \n 5. Close Session");
				System.out.print("Select Option: ");
				int choiceSubMenu = in.nextInt();
				switch (choiceSubMenu) {
				case 1:
					atm.checkBalance(cust, cus);
					break;
				case 2:
					if (isfirstWithdraw) {
						atm.withDraw(atm, cust, cus, isfirstWithdraw, in);
						isfirstWithdraw = false;
					} else {
						atm.withDraw(atm, cust, cus, isfirstWithdraw, in);
					}
					break;
				case 3:
					atm.transferMoney(atm, cus, in, cust);
					break;
				case 4:
					atm.checkATMBalance(atm);
					break;
				case 5:
					System.out.println("Session Closed");
					session = false;
					break;
				default:
					System.out.println("Please choose correct option");
					break;
				}
			}

		} else {
			System.out.print("Wrong Credentials");
		}

	}

	// Check Balance for selected customer
	private void checkBalance(Customer cust[], Customer cus) {
		for (int i = 0; i < cust.length; i++) {
			if (cus.getAccNo() == cust[i].getAccNo()) {
				System.out.println("Your account balance is: " + myFormat.format(cust[i].getAccBal()) + " ₹");
			}
		}
	}

	// Withdraw money Main
	private void withDraw(ATM atm, Customer cust[], Customer cus, boolean isFirstWithdraw, Scanner in) {
		if (isFirstWithdraw) {
			atm.withdrawMoney(atm, cust, cus, in);
		} else {
			System.out.print("Enter Pin: ");
			int pwd = in.nextInt();
			if (cus.getPinNo() == pwd) {
				atm.withdrawMoney(atm, cust, cus, in);
			} else {
				System.out.println("Wrong Pin");
			}
		}
	}

	// Withdraw Money Sub
	@SuppressWarnings("unchecked")
	public void withdrawMoney(ATM atm, Customer cust[], Customer cus, Scanner in) {
		System.out.print("Enter amount to be withdraw: ");
		long amount = in.nextLong();
		long checkAmount = amount;
		long rem;
		if (amount >= 100 && amount <= 10000) {
			for (int i = 0; i < cust.length; i++) {
				if (cus.getAccNo() == cust[i].getAccNo()) {
					if (amount < cust[i].getAccBal()) {
						System.out.println("Your account balance is: " + myFormat.format(cust[i].getAccBal()) + " ₹");
						InputStream fileIs = null;
						ObjectInputStream objIs = null;
						try {
							fileIs = new FileInputStream(PATH_ATM_BAL);
							objIs = new ObjectInputStream(fileIs);
							HashMap<Integer, ATMMachineMoney> map = (HashMap<Integer, ATMMachineMoney>) objIs
									.readObject();
							ATMMachineMoney newAtm[] = new ATMMachineMoney[3];
							int noteCounter[] = new int[] { 0, 0, 0 };

							int j = 0;
							long atmTotal = 0;
							for (Integer key : map.keySet()) {
								newAtm[j] = map.get(key);
								atmTotal = atmTotal + newAtm[j].getTotal();
								j++;
							}
							if (amount <= atmTotal) {
								newAtm[2] = map.get(100);
								if (amount <= 5000 && amount > 1500) {
									if (newAtm[2].getCount() >= 15) {
										noteCounter[2] = 15;
										amount -= 1500;
									} else if (newAtm[2].getCount() >= 10) {
										noteCounter[2] = 10;
										amount -= 1000;
									}else {
										System.out.println("Insufficient Notes");
										return;
									}
								} else if (amount <= 10000 && amount > 5000) {
									rem = amount % 1000;
									int c = (int) rem / 100;
									noteCounter[2] = c;
									amount -= c * 100;
//									noteCounter[2] = 10;
//									amount -= 1000;
								} else {
								}

								j = 0;
								for (Integer key : map.keySet()) {
									newAtm[j] = map.get(key);
									if (amount >= key && newAtm[j].getCount() > 0) {
										noteCounter[j] = noteCounter[j] + (int) amount / key;
										amount = amount - noteCounter[j] * key;
									}
									if (newAtm[j].getCount() < noteCounter[j]) {
										System.out.println("Insufficient Notes");
										return;
									}
									newAtm[j] = map.get(key);
									j++;

								}
								if (noteCounter[2] > 15) {
									noteCounter[2] -= 5;
									noteCounter[1] = 1;
								}
								j = 0;
								for (Integer key : map.keySet()) {
									newAtm[j] = map.get(key);
									if (noteCounter[j] != 0) {
										int count = newAtm[j].getCount() - noteCounter[j];
										long total = newAtm[j].getTotal() - key * noteCounter[j];
										newAtm[j].setCount(count);
										newAtm[j].setTotal(total);
										map.put(key, newAtm[j]);
										System.out.println("Currency Note: " + key + " - " + "count:" + noteCounter[j]);
									}
									j++;
								}

								atm.storeObject(PATH_ATM_BAL, map, null);
								j = 0;

								for (j = 0; j < cust.length; j++) {
									if (cust[j].getAccNo() == cus.getAccNo()) {
										long bal = cust[j].getAccBal() - checkAmount;
										cust[j].setAccBal(bal);
									}
								}
								System.out.println(checkAmount + " Rupees withdraw successfully");
								atm.storeObject(PATH_CUSTOMER, null, cust);
								System.out.println("Check Balance YES - 1:");
								int d = in.nextInt();
								if (d == 1) {
									atm.checkBalance(cust, cus);
								}

							} else {
								System.out.println("ATM has lesser than your amount");
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} finally {
							try {
								if (objIs != null)
									objIs.close();
							} catch (Exception ex) {

							}
						}
					} else {
						System.out.print("Account balance is lower than entered withdrawal amount.");
					}
				}
			}
		} else {
			System.out.println("Max withdrawal limit for a single transaction is10,000₹ and minimum is100₹");
		}
	}

	// Transfer Money
	private void transferMoney(ATM atm, Customer cus, Scanner in, Customer cust[]) {
		System.out.println("Transfer Account From: " + cus.getAccNo() + " - " + cus.getAccHolder());
		System.out.print("Enter Account number to which transfer money: ");
		int receiverAccNo = in.nextInt();
		Customer customer[] = getcustomerDetails();
		Customer receiverCustomer = new Customer();
		receiverCustomer = null;
		for (int i = 0; i < customer.length; i++) {
			if (customer[i].getAccNo() == receiverAccNo) {
				receiverCustomer = customer[i];
			}
		}

		if (receiverCustomer != null) {
			System.out.print("Enter amount to be Tranfer: ");
			long amount = in.nextLong();
			if (amount < cus.getAccBal()) {
				if (amount >= 1000 && amount <= 10000) {
					for (int i = 0; i < customer.length; i++) {
						if (customer[i].getAccNo() == receiverAccNo) {
							long amnt = customer[i].getAccBal() + amount;
							customer[i].setAccBal(amnt);
						}
						if (customer[i].getAccNo() == cus.getAccNo()) {
							long amnt = customer[i].getAccBal() - amount;
							customer[i].setAccBal(amnt);
						}
					}

					for (int i = 0; i < customer.length; i++) {
						cust[i] = customer[i];
					}
					
					System.out.println("Amount Transfered Successfully ");
				} else {
					System.out.println(
							"Max transfer limit per transaction cannot exceed 10,000 ₹ and should be more than 1000 ₹");
				}
			} else {
				System.out.println("Insufficient money");
			}
		} else {
			System.out.println("Account Not found");
		}
		atm.storeObject(PATH_CUSTOMER, null, cust);
	}

	// Check ATM Balance
	private void checkATMBalance(ATM atm) {
		atm.displayObjects();
	}

	// Get Customer Details
	public static Customer[] getcustomerDetails() {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		try {
			fileIs = new FileInputStream(PATH_CUSTOMER);
			objIs = new ObjectInputStream(fileIs);
			Customer cust[] = (Customer[]) objIs.readObject();
			return cust;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null)
					objIs.close();
			} catch (Exception ex) {

			}
		}
		return null;
	}

	// check customer if exist or not
	public Customer isAuthenticate(int accNo, int pwd) {
		Customer cust[] = getcustomerDetails();
		for (Customer customer : cust) {
			if (customer.getAccNo() == accNo) {
				if (customer.getPinNo() == pwd) {
					return customer;
				}
			}
		}
		return null;
	}
}
