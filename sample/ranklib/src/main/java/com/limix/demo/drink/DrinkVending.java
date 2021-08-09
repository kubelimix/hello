package com.limix.demo.drink;

import java.util.HashMap;
import java.util.Map;

/**
 * 售卖机
 * 
 * @author limix
 */
public class DrinkVending {

	private Map<String, Product> productMap = new HashMap<String, Product>();

	private float money = 0;
	private String productId = "";

	// 未投币 未选择产品状态
	private State noMoneyNoProductState;
	// 已投币 为选择产品状态
	private State moneyNoProductState;
	// 未投币 已选择产品状态
	private State noMoneyProductState;
	// 已投币 已选择产品中间状态
	private State moneyProductState;
	// 已销售产品状态
	private State soldState;

	private State currentState;

	public static void main(String[] args) {
		DrinkVending mainInstance = new DrinkVending();
		// 初始化产品以及产品状态
		mainInstance.initProductsInfo();
		// 显示商品信息
		mainInstance.displayProducts();
		mainInstance.insertMoney(1);
		mainInstance.selectProduct("1");
	}

	public DrinkVending() {
		noMoneyNoProductState = new NoMoneyNoProductState(this);
		currentState = noMoneyNoProductState;
	}

	public void addMoney(float money) {
		this.money += money;
	}

	public float getMoney() {
		return money;
	}
	
	public void setMoney(float money){
		this.money = money;
	}

	public String getProductId() {
		return productId;
	}
	
	public Product getProduct() {
		return this.productMap.get(this.getProductId());
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public Map<String, Product> initProductsInfo() {
		Product p1 = new Product("product1","产品1",3f, 100);
		Product p2 = new Product("product2","产品2",3f, 100);
		Product p3 = new Product("product3","产品3",3f, 100);
		productMap.put(p1.getProductId(), p1);
		productMap.put(p2.getProductId(), p2);
		productMap.put(p3.getProductId(), p3);
		return productMap;
	}

	public Map<String, Product> displayProducts() {
		for (Product product : productMap.values()) {
			System.out.println(String.format("产品:%s, 售价:%s, 剩余:%s", product.getName(), product.getPrice(), product.getTotal()));
		}
		return productMap;
	}

	/**
	 * 是否还有商品
	 * 
	 * @param productId
	 * @return
	 */
	public boolean existsProduct(String productId) {
        return productMap.containsKey(productId) && productMap.get(productId).getTotal() > 0;
	}

	public void insertMoney(float money) {
		synchronized (currentState){
			currentState.insertMoney(money);
		}
	}

	public void backMoney() {
		synchronized (currentState){
			currentState.backMoney();
		}
	}

	public void selectProduct(String productId) {
		synchronized (currentState){
			currentState.selectProduct(productId);
		}
	}

	public void scanPay() {
		synchronized (currentState){
			currentState.scanPay();
		}
	}

	public void delivery() {
		synchronized (currentState){
			currentState.scanPay();
		}
	}

	public State getNoMoneyNoProductState() {
		return noMoneyNoProductState;
	}

	public void setNoMoneyNoProductState(State noMoneyNoProductState) {
		this.noMoneyNoProductState = noMoneyNoProductState;
	}

	public State getMoneyNoProductState() {
		return moneyNoProductState;
	}

	public void setMoneyNoProductState(State moneyNoProductState) {
		this.moneyNoProductState = moneyNoProductState;
	}

	public State getNoMoneyProductState() {
		return noMoneyProductState;
	}

	public void setNoMoneyProductState(State noMoneyProductState) {
		this.noMoneyProductState = noMoneyProductState;
	}

	public State getMoneyProductState() {
		return moneyProductState;
	}

	public void setMoneyProductState(State moneyProductState) {
		this.moneyProductState = moneyProductState;
	}

	public State getSoldState() {
		return soldState;
	}

	public void setSoldState(State soldState) {
		this.soldState = soldState;
	}

	public String toString() {
		return String.format("已选产品:%s, 已支付:%s", productId, money);
	}
}
