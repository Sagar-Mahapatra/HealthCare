package in.nareshit.raghu.controller;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.utils.MyMailUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	private Logger logger = LoggerFactory.getLogger(DoctorController.class);
	@Autowired
	private IDoctorService service;
	@Autowired
	private ISpecializationService specService;
	@Autowired
	private MyMailUtil mailUtil;

	// 1. show Register page
	@GetMapping("/register")
	public String showReg(@RequestParam(value = "message", required = false) String message, Model model) {
		model.addAttribute("message", message);
		model.addAttribute("specializations", specService.getAllSpecializations());
		return "DoctorRegister";
	}

	// 2. save on submit
	@PostMapping("/save")
	public String save(@ModelAttribute Doctor doctor, RedirectAttributes attributes) {
		Long id = service.saveDoctor(doctor);
		if (id != null) {
			attributes.addAttribute("message", "Doctor (" + id + ") is created");
			new Thread(() -> {
				try {
					mailUtil.send(doctor.getEmail(), "Registration Success", "Doctor (" + id + ") is created");
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}).start();
			logger.info("Doctor id: (" + id + ") is created & mail sent successfully");
		} else {
			attributes.addAttribute("message", "Doctor Registration Failed");
			logger.error("Doctor Registration Failed");
		}
		return "redirect:register";
	}

	// 3. display data
	@GetMapping("/all")
	public String display(@RequestParam(value = "message", required = false) String message, Model model) {

		model.addAttribute("doctors", service.getAllDoctors());
		model.addAttribute("message", message);
		return "DoctorData";
	}

	// 4. delete by id
	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, RedirectAttributes attributes) {
		service.removeDoctor(id);
		attributes.addAttribute("message", "Doctor removed");
		return "redirect:all";
	}

	// 5. show edit
	@GetMapping("/edit")
	public String edit(@RequestParam("id") Long id, Model model, RedirectAttributes attributes) {
		Doctor doc = service.getOneDoctor(id);
		model.addAttribute("doctor", doc);
		return "DoctorEdit";
	}

	// 6. do update
	@PostMapping("/update")
	public String doUpdate(@ModelAttribute Doctor doctor, RedirectAttributes attributes) {
		service.updateDoctor(doctor);
		attributes.addAttribute("message", doctor.getId() + ", updated!");
		return "redirect:all";
	}

	// 7. email and mobile duplicate validations (AJAX)

	// 8. excel export

}