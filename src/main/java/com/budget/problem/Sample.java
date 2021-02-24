package com.budget.problem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sample {

	public static void main(String[] args) {
		Supplier s1 = new Supplier("S1", Arrays.asList(new Product("P1"), new Product("P2")));
		Supplier s2 = new Supplier("S2", Arrays.asList(new Product("P3"), new Product("P4")));
		java.util.List<Supplier> suppliers = Arrays.asList(s1, s2);

		List<Result> result = suppliers.stream().flatMap(supplier -> supplier.getProducts().stream().map(product -> {
			return new Result(supplier.getName(), product.getId());
		})).collect(Collectors.toList());

		result.stream().forEach(System.out::println);
		Sample sample = new Sample();
		IProduct product = sample.getProductService();
		product.printService(new Product("ID"));
	}

	private IProduct getProductService() {
		return new ProductServiceProxy();
	}

}
