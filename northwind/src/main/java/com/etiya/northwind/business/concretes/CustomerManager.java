package com.etiya.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.etiya.northwind.business.abstracts.CustomerService;
import com.etiya.northwind.business.requests.customers.CreateCustomerRequest;
import com.etiya.northwind.business.requests.customers.DeleteCustomerRequest;
import com.etiya.northwind.business.requests.customers.UpdateCustomerRequest;
import com.etiya.northwind.business.responses.customers.CustomerGetResponse;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.core.utilities.mapping.ModelMapperService;
import com.etiya.northwind.dataAccess.abstracts.CustomerRepository;
import com.etiya.northwind.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	private CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;

    public CustomerManager(CustomerRepository customerRepository, ModelMapperService modelMapperService) {
        this.customerRepository = customerRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public void add(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
        this.customerRepository.save(customer);
    }

    @Override
    public void delete(DeleteCustomerRequest deleteCustomerRequest) {
        this.customerRepository.deleteById(deleteCustomerRequest.getCustomerId());
    }

    @Override
    public void update(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);
        this.customerRepository.save(customer);
    }

    @Override
    public CustomerGetResponse getById(String customerId) {
        Customer customer = this.customerRepository.findById(customerId).get();
        CustomerGetResponse response = this.modelMapperService.forRequest().map(customer, CustomerGetResponse.class);
        return response;
    }

    @Override
    public List<CustomerListResponse> getAll() {
        List<Customer> result = this.customerRepository.findAll();
        List<CustomerListResponse> responses = result.stream().map(customer -> this.modelMapperService.forResponse()
                .map(customer, CustomerListResponse.class)).collect(Collectors.toList());

        return responses;
    }

	@Override
	public List<CustomerListResponse> getByPageNumber(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<Customer> result = this.customerRepository.findAll(pageable).getContent();
		List<CustomerListResponse> response = result.stream().map(customer -> this.modelMapperService.forResponse()
				.map(customer, CustomerListResponse.class)).collect(Collectors.toList());
		
		return response;
	}

	@Override
	public List<CustomerListResponse> getAllSortedByDesc(String field) {
		Sort sort = Sort.by(Sort.Order.desc(field));
		List<Customer> result = this.customerRepository.findAll(sort);
        List<CustomerListResponse> responses = result.stream().map(customer -> this.modelMapperService.forResponse()
                .map(customer, CustomerListResponse.class)).collect(Collectors.toList());

        return responses;
	}

	@Override
	public List<CustomerListResponse> getAllSortedByAsc(String field) {
		Sort sort = Sort.by(Sort.Order.asc(field));
		List<Customer> result = this.customerRepository.findAll(sort);
        List<CustomerListResponse> responses = result.stream().map(customer -> this.modelMapperService.forResponse()
                .map(customer, CustomerListResponse.class)).collect(Collectors.toList());

        return responses;
	}
}
