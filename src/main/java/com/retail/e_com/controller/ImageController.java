package com.retail.e_com.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.retail.e_com.authService.ImageService;
import com.retail.e_com.utility.SimpleResponseStructure;


import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:5173/")
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ImageController {

	private ImageService imageService;


	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("products/{productId}/imageType/{imageType}/images")
	public ResponseEntity<SimpleResponseStructure> addImage(@PathVariable int productId, String imageType, MultipartFile images)throws IOException{

		return imageService.addImage(productId, imageType, images);


	}


	@GetMapping("/image/{imageId}")
	public ResponseEntity<byte[]> getImage( @PathVariable String imageId)
	{
		return imageService.getImage(imageId);
	}


}
