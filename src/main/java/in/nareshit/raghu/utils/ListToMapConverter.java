package in.nareshit.raghu.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.nareshit.raghu.entity.Doctor;

public class ListToMapConverter {

	public static Map<Long, String> convertToMap(List<Doctor> list) {
		return list.stream().collect(Collectors.toMap(Doctor::getId, Doctor::getFirstName));
	}

}
