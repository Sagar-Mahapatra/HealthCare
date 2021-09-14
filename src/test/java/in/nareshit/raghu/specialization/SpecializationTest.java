package in.nareshit.raghu.specialization;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.repository.SpecializationRepository;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class SpecializationTest {

	private static Specialization spec;

	@Autowired
	private SpecializationRepository repo;

	@BeforeAll
	public static void init() {
		spec = new Specialization(null, "4", "Family Physicians",
				"They care for the whole family, including children, adults, and the elderly.");
	}

	@Test
	@Order(1)
	public void testSpecCreate() {
		assertNotNull(spec, "spec is not initialized");
		Long id = repo.save(spec).getId();
		assertNotNull(id, "spec is not saved to DB");
	}

	@Test
	@Order(2)
	public void testSpecFetchAll() {
		List<Specialization> list = repo.findAll();
		assertNotNull(list);
		if (list.isEmpty()) {
			fail("no data exist in DB");
		}
	}

	@AfterAll
	public static void clean() {
		spec = null;

	}

}
