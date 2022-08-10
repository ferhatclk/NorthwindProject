package com.etiya.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etiya.northwind.business.abstracts.OrderService;
import com.etiya.northwind.business.requests.orders.CreateOrderRequest;
import com.etiya.northwind.business.requests.orders.DeleteOrderRequest;
import com.etiya.northwind.business.requests.orders.UpdateOrderRequest;
import com.etiya.northwind.business.responses.orders.OrderGetResponse;
import com.etiya.northwind.business.responses.orders.OrderListResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.dataAccess.abstracts.OrderRepository;
import com.etiya.northwind.entities.concretes.Order;


@Service
public class OrderManager implements OrderService{
	private OrderRepository orderRepository;
	private ModelMapperService modelMapperService;

	
	public OrderManager(OrderRepository orderRepository,ModelMapperService modelMapperService) {
		this.orderRepository = orderRepository;
		this.modelMapperService = modelMapperService;

	}

	@Override
	public void add(CreateOrderRequest createOrderRequest) {
		Order order = this.modelMapperService.forRequest().map(createOrderRequest, Order.class);

		this.orderRepository.save(order);
        
	}

	@Override
	public void delete(DeleteOrderRequest deleteOrderRequest) {
		this.orderRepository.deleteById(deleteOrderRequest.getOrderId());
		
	}

	@Override
	public void update(UpdateOrderRequest updateOrderRequest) {
		Order order = this.modelMapperService.forRequest().map(updateOrderRequest, Order.class);
        this.orderRepository.save(order);
		
	}

	@Override
	public OrderGetResponse getById(int id) {
		Order order = this.orderRepository.findById(id).get();
        OrderGetResponse response = this.modelMapperService.forResponse().map(order, OrderGetResponse.class);
        return response;
	}
	
	@Override
	public List<OrderListResponse> getAll() {
		List<Order> result = this.orderRepository.findAll();
		List<OrderListResponse> response = result.stream().map(order -> this.modelMapperService.forResponse()
				.map(order, OrderListResponse.class)).collect(Collectors.toList());
	
		for(int i=0;i<response.size();i++) {
			response.get(i).setEmployeeFullName(result.get(i).getEmployee().getFirstName()+" "+result.get(i).getEmployee().getLastName());
		}
		
		return response;
	}

	@Override
	public List<OrderListResponse> getByPageNumber(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<Order> result = this.orderRepository.findAll(pageable).getContent();
		List<OrderListResponse> response = result.stream().map(order-> this.modelMapperService.forResponse()
				.map(order, OrderListResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	@Override
	public List<OrderListResponse> getAllSortedByDesc(String field) {
		Sort sort = Sort.by(Sort.Order.desc(field));
		
		List<Order> result = this.orderRepository.findAll(sort);
		List<OrderListResponse> response = result.stream().map(order-> this.modelMapperService.forResponse()
				.map(order, OrderListResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	@Override
	public List<OrderListResponse> getAllSortedByAsc(String field) {
		Sort sort = Sort.by(Sort.Order.asc(field));
		List<Order> result = this.orderRepository.findAll(sort);
		List<OrderListResponse> response = result.stream().map(order-> this.modelMapperService.forResponse()
				.map(order, OrderListResponse.class)).collect(Collectors.toList());
		
		return response;
	}

}
