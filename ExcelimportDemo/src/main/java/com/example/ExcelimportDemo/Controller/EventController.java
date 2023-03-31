package com.example.ExcelimportDemo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {
	@GetMapping("/calender")
	public String calender(Model model) {
		return "calender";
	}
}
