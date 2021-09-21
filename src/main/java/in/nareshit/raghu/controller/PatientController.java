package in.nareshit.raghu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.service.IpatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private IpatientService service;

	// 1. show Register page
	@GetMapping("/register")
	public String showReg(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		return "PatientRegister";
	}

	@PostMapping("/save")
	public String save(@RequestBody Patient patient, Model model) {
		Long id = service.savePatient(patient);
		String msg = "";
		if (id != null) {
			msg = "Patient Successfully Saved with Id: " + id;
			// model.addAttribute("msg", msg);
		} else {
			msg = "Something Went Wrong";
			// model.addAttribute("msg", msg);
		}
		// return "redirect:register";
		return msg;
	}

	@GetMapping("/all")
	public List<Patient> display(@RequestParam(required = false) String msg, Model model) {
		List<Patient> patients = service.getAllPatients();
		model.addAttribute("patients", patients);
		model.addAttribute("msg", msg);
		// return "PatientData";
		return patients;
	}

	@GetMapping("/delete")
	public String delete(@RequestParam Long id, RedirectAttributes attr) {
		String msg = "";
		try {
			service.deletePatient(id);
			msg = "Patient with Id: " + id + " deleted successfully...";
			// attr.addAttribute("msg", msg);
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			msg = e.getMessage();
			attr.addAttribute("msg", msg);
		}
		// return "redirect:all";
		return msg;
	}

}
