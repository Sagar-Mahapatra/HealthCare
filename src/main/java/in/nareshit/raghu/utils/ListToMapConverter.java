package in.nareshit.raghu.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import in.nareshit.raghu.entity.Doctor;

public class ListToMapConverter {

	public static Map<Long, String> convertToMap(List<Doctor> list) {
		return list.stream().collect(Collectors.toMap(Doctor::getId, Doctor::getFirstName));
	}

	public static Map<Long, String> convertListOfObjectArrayToMap(List<Object[]> list) {
		// Java 8 Stream API
		Map<Long, String> map = list.stream()
				.collect(Collectors.toMap(ob -> Long.valueOf(ob[0].toString()), ob -> ob[1].toString()));
		return map;
	}

	public static Map<Long, String> convertToMapIndex(List<Object[]> list) {
		// Java 8 Stream API
		Map<Long, String> map = list.stream().collect(Collectors.toMap(ob -> Long.valueOf(ob[0].toString()),
				ob -> ob[1].toString() + " " + ob[2].toString()));
		return map;
	}

}
