package com.limix.demo.drink;

public class MoneyNoProductState implements State {
	
	private DrinkVending machine;

	public MoneyNoProductState(DrinkVending machine) {
		this.machine = machine;
	}

	@Override
	public void insertMoney(float money) {
		// LIMIX_TODO Auto-generated method stub
	}

	@Override
	public void backMoney() {
		// LIMIX_TODO Auto-generated method stub
	}

	@Override
	public void selectProduct(String productId) {
		// LIMIX_TODO Auto-generated method stub
		machine.setProductId(productId);
		machine.setCurrentState(machine.getMoneyProductState());
		machine.delivery();
	}

	@Override
	public void scanPay() {
		// LIMIX_TODO Auto-generated method stub
	}

	@Override
	public void delivery() {
		// LIMIX_TODO Auto-generated method stub
	}

}
