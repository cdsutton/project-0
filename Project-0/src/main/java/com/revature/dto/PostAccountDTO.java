package com.revature.dto;

public class PostAccountDTO {

	private String accountName;
	private int amount;
	
	public PostAccountDTO() {
		super();
	}
	
	public PostAccountDTO(String accountName, int amount) {
		this.accountName = accountName;
		this.amount = amount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + amount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostAccountDTO other = (PostAccountDTO) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (amount != other.amount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PostAccountDTO [accountName=" + accountName + ", amount=" + amount + "]";
	}

}