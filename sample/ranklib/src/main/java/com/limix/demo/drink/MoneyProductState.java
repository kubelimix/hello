package com.limix.demo.drink;

/**
 * 已投币 已选择 产品的中间状态
 * @author limix
 */
public class MoneyProductState implements State {
	
	private DrinkVending machine;

	public MoneyProductState(DrinkVending machine) {
		this.machine = machine;
	}

	@Override
	public void insertMoney(float money) {
		machine.addMoney(money);
		machine.delivery();
	}

	@Override
	public void backMoney() {
		System.out.println("正在退币");
		machine.setMoney(0);
		machine.setProductId(null);
		machine.setCurrentState(machine.getNoMoneyNoProductState());
	}

	@Override
	public void selectProduct(String productId) {
		// LIMIX_TODO Auto-generated method stub
		if (!machine.existsProduct(productId)){
			System.out.println("该产品已经售罄,请重新选择产品");
			machine.setCurrentState(machine.getMoneyNoProductState());
		} else {
			System.out.println("重新选择产品");
			machine.delivery();
		}
	}

	@Override
	public void scanPay() {
		// 直接支付
	}

	@Override
	public void delivery() {
		// LIMIX_TODO Auto-generated method stub
		if (machine.existsProduct(machine.getProductId())){
			// 交付
		}
	}

}
