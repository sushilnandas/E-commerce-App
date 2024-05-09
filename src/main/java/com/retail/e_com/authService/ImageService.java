package com.retail.e_com.authService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.retail.e_com.utility.SimpleResponseStructure;

public interface ImageService {
	public ResponseEntity<SimpleResponseStructure> addImage(int productId, String imageType, MultipartFile images) throws IOException;
    public ResponseEntity<byte[]> getImage(String imageId);
}
