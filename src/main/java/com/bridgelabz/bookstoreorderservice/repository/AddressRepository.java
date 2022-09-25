package com.bridgelabz.bookstoreorderservice.repository;

import com.bridgelabz.bookstoreorderservice.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long>{

}
