package com.limix.demo.drink;

/**
 * 未支付 已经选择商品
 * @author limix
 */
public class NoMoneyProductState implements State {

	private DrinkVending machine;

	public NoMoneyProductState(DrinkVending machine) {
		this.machine = machine;
	}

	@Override
	public void insertMoney(float money) {
		machine.setCurrentState(machine.getMoneyProductState());
		machine.addMoney(money);
		machine.delivery();
	}

	@Override
	public void backMoney() {
		System.out.println("未进行任何支付");
	}

	@Override
	public void selectProduct(String productId) {
		System.out.println(String.format("已重新选择商品%s", productId));
	}

	@Override
	public void scanPay() {
		machine.setCurrentState(machine.getSoldState());
		machine.delivery();
	}

	@Override
	public void delivery() {
		System.out.println("请投币或者扫码支付");
	}

}
