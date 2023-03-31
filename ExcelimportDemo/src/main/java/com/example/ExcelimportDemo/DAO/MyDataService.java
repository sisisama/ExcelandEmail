package com.example.ExcelimportDemo.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExcelimportDemo.Entity.MyData;
@Service
public class MyDataService {
	@Autowired
    private MyDataRepository myDataRepository;
	 public void saveData(MyData data) {
	        myDataRepository.save(data);
	    }
}
