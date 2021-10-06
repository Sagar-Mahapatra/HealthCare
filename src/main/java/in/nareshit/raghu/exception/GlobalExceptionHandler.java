package in.nareshit.raghu.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AppointmentNotFoundException.class)
	public String AppointmentNotFoundExceptionHandler(Model model) {
		model.addAttribute("message", "APPOINTMENT NOT FOUND WITH THIS ID");
		return "ErrorPage";
	}

}
