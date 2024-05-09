package com.retail.e_com.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class MessageModel {
	
	private String to;
	private String subject;
	private String text;

}
