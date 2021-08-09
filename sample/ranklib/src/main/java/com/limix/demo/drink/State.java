package com.limix.demo.drink;

/**
 * 行为特征
 * @author limix
 *
 */
public interface State {

	// 用户行为:塞入硬币
    void insertMoney(float money);

	void backMoney();
	
	void selectProduct(String productId);
	
	void scanPay();
	
	void delivery();
}
