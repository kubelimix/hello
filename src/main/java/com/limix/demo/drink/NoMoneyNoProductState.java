package com.limix.demo.drink;

/**
 * 未支付-未选择产品状态
 * 
 * @author limix
 *
 */
public class NoMoneyNoProductState implements State {

	private DrinkVending machine;

	public NoMoneyNoProductState(DrinkVending machine) {
		this.machine = machine;
	}

	@Override
	public void insertMoney(float money) {
		System.out.println("投币成功");
		machine.addMoney(money);
		System.out.println(machine);
		machine.setCurrentState(machine.getMoneyNoProductState());
	}

	@Override
	public void backMoney() {
		System.out.println("您未投币,不需要退钱");
	}

	@Override
	public void selectProduct(String productId) {
		if (machine.existsProduct(productId)){
			machine.setProductId(productId);
			System.out.println(String.format("已选择商品:%s ", productId));
			machine.setCurrentState(machine.getNoMoneyProductState());
			machine.delivery();
		} else {
			System.out.println(String.format("商品:%s 已售罄,请选择其他商品", productId));
		} 
	}

	@Override
	public void scanPay() {
		System.out.println("请先您想购买的商品");
	}

	@Override
	public void delivery() {
		System.out.println("请先您想购买的商品,并完成支付");
	}
}