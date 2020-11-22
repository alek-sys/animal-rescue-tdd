package rescue.animals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rescue.adoption.AdoptionRequests;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalsControllerDetroitTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	JdbcOperations jdbcOperations;

	@MockBean
	AdoptionRequests adoptionRequests;

	@Test
	void shouldReturnOkResponse() throws Exception {
		this.mockMvc.perform(get("/animals"))
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldDisplayAnimals() throws Exception {
		// Detroit / Chicago school of TDD - classicists

		// Given - could be a POST request, if supported
		this.jdbcOperations.update("insert into ANIMALS (id, name, avatar_url) values (999, 'Paprica', 'https://example.com/avatar.jpg')");

		// When
		ResultActions result = this.mockMvc.perform(get("/animals"));

		// Then
		result.andExpect(jsonPath("$.[-1].id").value(equalTo(999)))
			.andExpect(jsonPath("$.[-1].name").value(equalTo("Paprica")))
			.andExpect(jsonPath("$.[-1].avatarUrl").value(not(blankOrNullString())))
			.andExpect(jsonPath("$.[-1].description").exists())
			.andExpect(jsonPath("$.[-1].rescueDate").exists());
	}
}