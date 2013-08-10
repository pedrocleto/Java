package com.pcm.deadlock;

public class ThreadSafeBankAccount {
	private double balance;
	private int number;

	public ThreadSafeBankAccount(int num, double initialBalance) {
		balance = initialBalance;
		number = num;
	}

	public int getNumber() {
		return number;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		synchronized (this) {
			double prevBalance = balance;
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			balance = prevBalance + amount;
		}
	}

	public void withdraw(double amount) {
		synchronized (this) {
			double prevBalance = balance;
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			balance = prevBalance - amount;
		}
	}
}