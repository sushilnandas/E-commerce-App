package com.retail.e_com.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.http.MediaType;

import com.retail.e_com.enums.ImageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Document(collection = "image")
@Getter
@Setter
@Builder
@AllArgsConstructor

public class Image {
	
	@MongoId
	private String imageId;
	private ImageType imageType;
	private byte[] imageBytes;
	private int productId;
	private String contentType;
	
	

}
