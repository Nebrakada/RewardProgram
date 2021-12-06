package com.reward.program.application.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  @Query("SELECT t FROM Transaction t WHERE t.customer.id = :customerId")
  List<Transaction> findByCustomerId(@Param("customerId") Long customerId);

}
