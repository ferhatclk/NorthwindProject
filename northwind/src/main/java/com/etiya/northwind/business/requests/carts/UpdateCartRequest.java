package com.etiya.northwind.business.requests.carts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {
	@NotNull
	@Positive
	private int cartId;
	
	@NotNull
	@NotBlank
	private String customerId;
}
