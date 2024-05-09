package com.retail.e_com.requestdto;



import com.retail.e_com.enums.Priority;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ContactRequest {


	@NotNull
	private String name;
	private long contactNumber;
	private Priority priority;
	@Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b\n",message = "Invalid format")
	private String email;

}
