package com.limix.demo.drink;

/**
 * 行为特征
 * @author limix
 *
 */
public interface State {

	// 用户行为:塞入硬币
	public void insertMoney(float money);

	public void backMoney();
	
	public void selectProduct(String productId);
	
	public void scanPay();
	
	public void delivery();
}
