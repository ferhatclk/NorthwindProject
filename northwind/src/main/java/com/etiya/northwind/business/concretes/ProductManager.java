package com.etiya.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.northwind.business.abstracts.ProductService;
import com.etiya.northwind.business.requests.products.CreateProductRequest;
import com.etiya.northwind.business.requests.products.DeleteProductRequest;
import com.etiya.northwind.business.requests.products.UpdateProductRequest;
import com.etiya.northwind.business.responses.products.ProductGetResponse;
import com.etiya.northwind.business.responses.products.ProductListResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.dataAccess.abstracts.ProductRepository;
import com.etiya.northwind.entities.concretes.Product;

@Service
public class ProductManager implements ProductService{
	private ProductRepository productRepository;
	private ModelMapperService modelMapperService;
	
	@Autowired // git somutunu bul enjecte et
	public ProductManager(ProductRepository productRepository,ModelMapperService modelMapperService) {

		this.productRepository = productRepository;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public List<ProductListResponse> getAll() {
		List<Product> result = this.productRepository.findAll();
		List<ProductListResponse> response = result.stream()
				.map(product -> this.modelMapperService.forResponse()
						.map(product, ProductListResponse.class))
				.collect(Collectors.toList());
	
		return response;
	}


	@Override
	public void add(CreateProductRequest createProductRequest) {
		Product product = this.modelMapperService.forRequest()
				.map(createProductRequest, Product.class);
		
		this.productRepository.save(product);
	}


	@Override
	public void delete(DeleteProductRequest deleteProductRequest) {
		this.productRepository.deleteById(deleteProductRequest.getProductId());
		
	}


	@Override
	public void update(UpdateProductRequest updateProductRequest) {
		Product product = this.modelMapperService.forRequest()
				.map(updateProductRequest, Product.class);
		
		this.productRepository.save(product);
	}


	@Override
	public ProductGetResponse getById(int id) {
		Product product = this.productRepository.findById(id).get();
		ProductGetResponse productResponse = this.modelMapperService.forResponse()
				.map(product, ProductGetResponse.class);
		return productResponse;
	}

}
