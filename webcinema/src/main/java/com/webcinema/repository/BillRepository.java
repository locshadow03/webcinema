package com.webcinema.repository;

import com.webcinema.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("select b from Bill b where b.customer.customer_id = :customer_id")
    List<Bill> findAllBillByCustomer_id(@Param("customer_id") Long customer_id);
}
