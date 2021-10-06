package in.nareshit.raghu.controller;

import java.util.List;

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
import in.nareshit.raghu.service.IAppointmentService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private IAppointmentService service;

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
