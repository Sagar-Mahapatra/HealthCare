package in.nareshit.raghu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.exception.SpecNotFoundException;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.utils.ViewExcel;

@Controller
@RequestMapping("/spec")
public class SpecializationController {

	@Autowired
	private ISpecializationService service;

	@GetMapping("/register")
	public String displayRegister(Model model) {
		model.addAttribute("spec", new Specialization());
		return "specializationRegister";

	}

	@PostMapping("/save")
	public String saveForm(@ModelAttribute Specialization specialization, Model model) {
		Long id = service.saveSpecialization(specialization);
		String message = "Record (" + id + ") is created";
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}

	@GetMapping("/all")
	public String viewAll(Model model, @RequestParam(value = "message", required = false) String message) {

		model.addAttribute("list", service.getSpecializationsList());
		model.addAttribute("message", message);
		return "SpecializationData";
	}

	@GetMapping("/delete")
	public String deleteData(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);
			attributes.addAttribute("message", "Record (" + id + ") is removed");
		} catch (SpecNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}

		return "redirect:all";
	}

	@GetMapping("/edit")
	public String showEditPage(@RequestParam Long id, Model model, RedirectAttributes attr) {
		try {
			Specialization spec = service.getOneSpecialization(id);
			model.addAttribute("specialization", spec);
			return "SpecializationEdit";
		} catch (Exception e) {
			e.printStackTrace();
			attr.addAttribute("message", e.getMessage());
			return "redirect:all";
		}

	}

	@PostMapping("/update")
	public String updateData(@ModelAttribute Specialization specialization, RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		attributes.addAttribute("message", "Record (" + specialization.getId() + ") is updated");
		return "redirect:all";
	}

	@ResponseBody
	@GetMapping("/checkName")
	public String checkName(@RequestParam String name, @RequestParam Long id) {
		if (id == 0) {
			boolean nameUnique = service.isNameUnique(name);
			return (nameUnique) ? "" : "Name Already Exists";
		} else {
			boolean nameUniqueForEdit = service.isNameUniqueForEdit(name, id);
			return (nameUniqueForEdit) ? "" : "Name Already Exists";
		}

	}

	@ResponseBody
	@GetMapping("/checkCode")
	public String checkCode(@RequestParam String code, @RequestParam Long id) {
		if (id == 0) {
			boolean codeUnique = service.isCodeUnique(code);
			return (codeUnique) ? "" : "Code Already Exists";
		} else {
			boolean codeUniqueForEdit = service.isCodeUniqueForEdit(code, id);
			return (codeUniqueForEdit) ? "" : "Code Already Exists";
		}

	}

	@GetMapping("/excel")
	public ModelAndView viewExcel() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", service.getAllSpecializations());
		modelAndView.setView(new ViewExcel());
		return modelAndView;

	}

}
