package com.retail.e_com.serviceImpl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.retail.e_com.authService.ImageService;
import com.retail.e_com.entity.Image;
import com.retail.e_com.enums.ImageType;
import com.retail.e_com.exception.ImageNotFoundByIdException;
import com.retail.e_com.exception.ImageTypeMismatchException;
import com.retail.e_com.exception.InvalidCridentialException;
import com.retail.e_com.exception.ProductNotFoundByIdException;
import com.retail.e_com.exception.ProductNotFoundException;
import com.retail.e_com.repository.ImageRepository;
import com.retail.e_com.repository.ProductRepository;
import com.retail.e_com.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
	
	private ProductRepository productRepository;
	private SimpleResponseStructure simpleResponseStructure;
	private ImageRepository imageRepository;
	

	@Override
	public ResponseEntity<SimpleResponseStructure> addImage(int productId, String imageType, MultipartFile images) throws IOException {
	
		if(!productRepository.existsById(productId))
			throw new ProductNotFoundByIdException("Product not found By Id");

		
		ImageType type = null;
		try {
			type = ImageType.valueOf(imageType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidCridentialException("Invalid image type");
		}		
		if(type.equals(ImageType.COVER))
		imageRepository.findByProductIdAndImageType(productId,ImageType.COVER).ifPresent(imageData->{
			imageData.setImageType(ImageType.NORMAL);
			imageRepository.save(imageData);
			
		});		
		
			imageRepository.save(Image.builder()
					.imageBytes(images.getBytes())
					.productId(productId)
					.imageType(type)
					
					.contentType(images.getContentType())
					.build());		
		return ResponseEntity.ok(new SimpleResponseStructure()
				.setMessage("image added")
				.setStatus(HttpStatus.OK.value())); 
		
	}


	@Override
	public ResponseEntity<byte[]> getImage(String imageId) {
		// TODO Auto-generated method stub
		return imageRepository.findById(imageId).map(image->{
			return ResponseEntity
					.ok()
					.contentLength(image.getImageBytes().length)
					.contentType(MediaType.valueOf(image.getContentType()))
					.body(image.getImageBytes());
		}).orElseThrow(()-> new ImageNotFoundByIdException("Image not found for the following ID"));
	}
	
	
	
	
//	private Image mapToImage(Image image,int productId, String imageType, MultipartFile images) throws IOException{
//		
//		try {
//			image.setImageType(ImageType.valueOf(imageType.toUpperCase()));
//		}
//		catch(IllegalArgumentException e) {
//		throw new ImageTypeMismatchException("image type mentioned is not correct");
//			
//		}
//		imageRepository.save()
//
//		image.setImageBytes(images.getBytes());
//		image.setProductId(productId);
//		return image;
//		
//		
	//}
	
	

}
