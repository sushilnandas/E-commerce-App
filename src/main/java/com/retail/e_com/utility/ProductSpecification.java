package com.retail.e_com.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.retail.e_com.entity.Product;
import com.retail.e_com.requestdto.SearchFilter;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductSpecification {
	
	private SearchFilter searchFilters;
    
	public Specification<Product> buildSpecification(){
		return (root,query,criteriaBuilder)->{
			List<Predicate>predicates = new ArrayList<>();
			
			if(searchFilters.getMinprice()>0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"),
						searchFilters.getMinprice()));
			}
			if(searchFilters.getMaxPrice()>0) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("productPrice"),searchFilters.getMaxPrice()));
				
			}
			if(searchFilters.getCategory()!=null) {
				predicates.add(criteriaBuilder.equal(root.get("category"),searchFilters.getCategory()));
			}
			if(searchFilters.getDiscount()>0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("productPrice"), searchFilters.getDiscount()));
			}
			if(searchFilters.getRating()>0) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), searchFilters.getRating()));
			}
			return criteriaBuilder.and((Predicate[])predicates.toArray(new Predicate[0]));
			
		
		};
	}
}
