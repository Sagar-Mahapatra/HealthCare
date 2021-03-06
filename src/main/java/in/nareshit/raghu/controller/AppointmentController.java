package in.nareshit.raghu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.exception.AppointmentNotFoundException;
import in.nareshit.raghu.service.IAppointmentService;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.ISpecializationService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private IAppointmentService service;

	@Autowired
	private ISpecializationService specializationService;

	@Autowired
	private IDoctorService doctorService;

	private void commonUi(Model model) {
		model.addAttribute("doctors", doctorService.getDoctorIdAndNames());
	}

	@GetMapping("/register")
	public String registerAppointment(Model model) {
		model.addAttribute("appointment", new Appointment());
		commonUi(model);
		return "AppointmentRegister";
	}

	@PostMapping("/save")
	public String createAppointment(@ModelAttribute Appointment appointment, RedirectAttributes attr) {
		Long id = service.saveAppointment(appointment);
		if (id != null) {
			attr.addAttribute("msg", "APPOINTMENT SAVED SUCCESSFULLY...");
		} else {
			attr.addAttribute("msg", "SOMETHING WENT WRONG!!!");
		}
		return "redirect:/index";

	}

	@GetMapping("/viewSlot")
	public String showSlots(@RequestParam Long id, Model model) {
		// fetch apps based on doctor id
		List<Object[]> list = service.getAppoinmentsByDoctor(id);
		model.addAttribute("list", list);
		Doctor doc = doctorService.getOneDoctor(id);
		model.addAttribute("message", "RESULTS SHOWING FOR : " + doc.getFirstName() + " " + doc.getLastName());
		return "AppointmentSlots";
	}

	// .. view appointments page..
	@GetMapping("/view")
	public String viewSlots(@RequestParam(required = false, defaultValue = "0") Long specId, Model model) {
		// fetch data for Spec DropDown
		Map<Long, String> specMap = specializationService.getSpecIdAndName();
		model.addAttribute("specializations", specMap);

		List<Doctor> docList = null;
		String message = null;
		if (specId <= 0) { // if they did not select any spec
			docList = doctorService.getAllDoctors();
			message = "Result : All Doctors";
		} else {
			docList = doctorService.findDoctorBySpecId(specId);
			message = "Result : " + specializationService.getOneSpecialization(specId).getSpecName() + " Doctors";
		}
		model.addAttribute("docList", docList);

		model.addAttribute("message", message);

		return "AppointmentSearch";
	}

	@GetMapping("/all")
	public String allAppointments(@RequestParam(required = false) String message, Model model) {
		List<Appointment> appointments = service.getAllAppointments();
		model.addAttribute("appointments", appointments);
		model.addAttribute("message", message);
		return "AppointmentData";
	}

	@GetMapping("/delete")
	public String deleteAppointment(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.deleteAppointment(id);
			attributes.addAttribute("message", "Appointment deleted with Id:" + id);
		} catch (AppointmentNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}

	@GetMapping("/edit")
	public String editAppointment(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page = null;
		try {
			Appointment ob = service.getOneAppointment(id);
			model.addAttribute("appointment", ob);
			commonUi(model);
			page = "AppointmentEdit";
		} catch (AppointmentNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateAppointment(@ModelAttribute Appointment appointment, RedirectAttributes attributes) {
		service.updateAppointment(appointment);
		attributes.addAttribute("message", "Appointment updated");
		return "redirect:all";
	}
	

}
