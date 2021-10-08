package in.nareshit.raghu.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AppointmentNotFoundException.class)
	public String AppointmentNotFoundExceptionHandler() {
		return "ErrorPage";
	}

	@ExceptionHandler(DoctorNotFoundException.class)
	public String doctorNotFoundExceptionHandler() {
		return "ErrorPage";
	}

	@ExceptionHandler(PatientNotFoundException.class)
	public String patientNotFoundExceptionHandler() {
		return "ErrorPage";
	}

	@ExceptionHandler(ApplicationError.class)
	public String ApplicationErrorHandler() {
		return "ErrorPage";
	}

}
