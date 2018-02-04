package br.com.testeBack2017;

public class Customer {

	private int customerId;
	private String customerCPFCNPJ;
	private String customerName;
	private boolean customerIsActive;
	private double customerBalance;
	
	public Customer() {
	}
	public Customer(int customerId, String customerCPF, String customerName, boolean customerIsActive,
			double customerBalance) {
		this.customerId = customerId;
		this.customerCPFCNPJ = customerCPF;
		this.customerName = customerName;
		this.customerIsActive = customerIsActive;
		this.customerBalance = customerBalance;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerCPFCNPJ() {
		return customerCPFCNPJ;
	}
	public void setCustomerCPFCNPJ(String customerCPF) {
		this.customerCPFCNPJ = customerCPF;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isCustomerActive() {
		return customerIsActive;
	}
	public void setCustomerActive(boolean customerIsActive) {
		this.customerIsActive = customerIsActive;
	}
	public double getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(double customerBalance) {
		this.customerBalance = customerBalance;
	}
	
}
