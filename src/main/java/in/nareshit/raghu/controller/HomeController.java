package in.nareshit.raghu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//ctrl+shift+O
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.utils.ListToMapConverter;

@Controller
public class HomeController {
	@Autowired
	private IDoctorService doctorService;

	@GetMapping("/index")
	public String landingPage(@RequestParam(required = false) String msg, Model model) {
		Map<Long, String> doctors = ListToMapConverter.convertToMap(doctorService.getAllDoctors());
		model.addAttribute("doctors", doctors);
		model.addAttribute("msg", msg);
		return "index";
	}
}