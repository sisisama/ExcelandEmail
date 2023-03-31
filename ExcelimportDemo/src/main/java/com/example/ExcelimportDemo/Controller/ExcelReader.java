package com.example.ExcelimportDemo.Controller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExcelimportDemo.DAO.MyDataRepository;
import com.example.ExcelimportDemo.Entity.MyData;
@Service
public class ExcelReader {
	 @Autowired
	    private MyDataRepository myDataRepository;
	 
	    public void importExcel(MultipartFile file) throws IOException, MessagingException {
	    	 	InputStream inputStream = file.getInputStream();
	    	    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	    	    XSSFSheet sheet = workbook.getSheetAt(0);
	    	    Iterator<Row> rowIterator = sheet.iterator();

	    	    // Skip the first row, which contains the column names
	    	    if (rowIterator.hasNext()) {
	    	        rowIterator.next();
	    	    }

	    	    while (rowIterator.hasNext()) {
	    	        Row row = rowIterator.next();
	    	        if(row.getCell(0) != null && row.getCell(1) != null) {
	    	        String name = row.getCell(0).getStringCellValue();
	    	        String email = row.getCell(1).getStringCellValue();
	    	        String password = PasswordGenerator.generateTokenPassword(7);
	    	        // Check if the data already exists in the database before inserting
	    	        Optional<MyData> existingData = myDataRepository.findByEmployeeEmail( email);
	    	        if (!existingData.isPresent()) {
	    	        	
	    	            MyData newData = new MyData();
	    	            newData.setEmployeeName(name);
	    	            newData.setEmployeeEmail(email);
	    	            newData.setEmployeePassword(password);
	    	          
	    	            myDataRepository.save(newData);
	    	            sendEmail(email, password);
	    	        }
	    	    
	    	    }
	    	    workbook.close(); 
	    	    inputStream.close();
	    } 
	    	    
	    }
	    private void sendEmail(String to, String password) throws MessagingException {
	        // Create a JavaMail session
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("sithuramyo@gmail.com", "imzukzjsnsefosjj");
	            }
	        });

	        // Create a message
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("sithuramyo@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	        message.setSubject("Your new password");
	        message.setText("Dear user,\n\nYour default password is: " + password + "\n\nBest regards,\nYour application");

	        // Send the message
	        Transport.send(message);
	    }
}
