package com.etiya.northwind.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.northwind.business.abstracts.SupplierService;
import com.etiya.northwind.business.requests.suppliers.CreateSupplierRequest;
import com.etiya.northwind.business.requests.suppliers.DeleteSupplierRequest;
import com.etiya.northwind.business.requests.suppliers.UpdateSupplierRequest;
import com.etiya.northwind.business.responses.suppliers.SupplierGetResponse;
import com.etiya.northwind.business.responses.suppliers.SupplierListResponse;

@RestController
@RequestMapping("/api/suppliers")
public class SuppliersController {
	private SupplierService supplierService;

	public SuppliersController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateSupplierRequest createSupplierRequest) {
		this.supplierService.add(createSupplierRequest);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestBody DeleteSupplierRequest deleteSupplierRequest) {
		this.supplierService.delete(deleteSupplierRequest);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateSupplierRequest updateSupplierRequest) {
		this.supplierService.update(updateSupplierRequest);
	}
	
	@GetMapping("/getbyid")
	public SupplierGetResponse getById(@RequestParam int id) {
		return this.supplierService.getById(id);
	}
	
	@GetMapping("/getall")
	public List<SupplierListResponse> getAll(){
		return this.supplierService.getAll();
	}
	
	@GetMapping("/getbypageno")
	public List<SupplierListResponse> getByPageNo(int pageNo, int pageSize){
		return this.supplierService.getByPageNumber(pageNo, pageSize);
	}
	
	@GetMapping("/getallsortedbydesc")
	public List<SupplierListResponse> getAllSortedByDesc(@RequestParam String field){
		return this.supplierService.getAllSortedByDesc(field);
	}
	
	@GetMapping("/getallsortedbyasc")
	public List<SupplierListResponse> getAllSortedByAsc(@RequestParam String field){
		return this.supplierService.getAllSortedByAsc(field);
	}
}
