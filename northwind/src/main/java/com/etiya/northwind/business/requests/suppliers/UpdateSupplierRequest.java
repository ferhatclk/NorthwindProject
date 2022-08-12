package com.etiya.northwind.business.requests.suppliers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSupplierRequest {
	@NotNull
    @Positive
	private int supplierId;
	
    @NotNull
    @NotBlank
	private String companyName;
    
    @NotNull
    @NotBlank
	private String contactName;
    
    
	private String contactTitle;
	
	
	private String address;
}
