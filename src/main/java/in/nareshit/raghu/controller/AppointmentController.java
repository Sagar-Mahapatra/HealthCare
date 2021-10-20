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
			docList = doctorService.findDoctorBySpecName(specId);
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
	public String delete(@RequestParam Long id, RedirectAttributes attr) {
		service.deleteAppointment(id);
		attr.addAttribute("message", "Successfully Deleted");
		return "redirect:all";
	}

}
