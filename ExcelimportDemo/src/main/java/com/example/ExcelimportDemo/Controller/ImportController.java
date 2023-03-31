package com.example.ExcelimportDemo.Controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImportController {
	 @Autowired
	    private ExcelReader excelImportService;
	 	@GetMapping("/")
	 	public String home() {
	 		return "excel";
	 	}
	    @PostMapping("/import")
	    public String importExcel(@RequestParam("file") MultipartFile file) throws IOException, MessagingException {
	        excelImportService.importExcel(file);
	        return "redirect:/";
	    }
	    
}
