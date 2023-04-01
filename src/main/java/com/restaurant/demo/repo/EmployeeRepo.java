package com.restaurant.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.demo.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	List<Employee> findByStoreId(long storeId);
}
