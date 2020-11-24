package rescue;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = AnimalRescueBackendApplication.class)
@AutoConfigureMockMvc
public class BaseClass {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
}
