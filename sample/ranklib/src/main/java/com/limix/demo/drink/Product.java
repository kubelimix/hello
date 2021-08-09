package com.limix.demo.drink;

public class Product {

	// 产品id
	private String productId;

	// 产品名称
	private String name;

	// 产品价格
	private float price;

	// 产品数量
	private int total;
	
	public Product(String productId, String name, float price, int total){
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.total = total;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}