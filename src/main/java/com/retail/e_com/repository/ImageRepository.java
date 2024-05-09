package com.retail.e_com.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.Image;
import com.retail.e_com.enums.ImageType;

@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

	Optional<Image> findByProductIdAndImageType(int productId, ImageType cover);

}
