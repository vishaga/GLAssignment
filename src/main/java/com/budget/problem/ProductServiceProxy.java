package com.budget.problem;

public class ProductServiceProxy extends IProduct {

	private ProductService productService;

	private void validate() {
		if (productService == null)
			productService = new ProductService();
	}

	@Override
	public void printService(Product p) {
		validate();
		System.out.println("Logger start");
		productService.printService(p);
		System.out.println("Logger end");

	}

}
