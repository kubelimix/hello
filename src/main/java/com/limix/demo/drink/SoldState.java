package com.limix.demo.drink;

public class SoldState implements State {

	private DrinkVending machine;

	public SoldState(DrinkVending machine) {
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
	}

	@Override
	public void scanPay() {
		// LIMIX_TODO Auto-generated method stub
	}

	@Override
	public void delivery() {
		if (machine.getMoney() >= machine.getProduct().getPrice()) {
			machine.setMoney(machine.getMoney() - machine.getProduct().getPrice());
			System.out.print("正在发货");
			if (machine.getMoney() > 0){
				machine.setCurrentState(machine.getMoneyNoProductState());
			} else if (machine.getMoney() == 0){
				machine.setCurrentState(machine.getNoMoneyNoProductState());
			}
		} else {
			System.out.println("异常处理");
		}
	}

}
