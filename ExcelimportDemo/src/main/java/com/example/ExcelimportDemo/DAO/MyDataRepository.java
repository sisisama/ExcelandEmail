package com.example.ExcelimportDemo.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ExcelimportDemo.Entity.MyData;
@Service
public interface MyDataRepository extends JpaRepository<MyData, Long> {
	 Optional<MyData> findByEmployeeEmail( String employeeEmail);
}
