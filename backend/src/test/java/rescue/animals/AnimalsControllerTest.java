package rescue.animals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rescue.adoption.AdoptionRequests;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	Animals animals;

	@MockBean
	AdoptionRequests adoptionRequests;

	@Test
	void shouldReturnOkResponse() throws Exception {
		this.mockMvc.perform(get("/animals"))
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldDisplayAnimals() throws Exception {
		// London school of TDD - mockists

		// Given
		when(this.animals.getAll())
			.thenReturn(Collections.singletonList(new Animal(1, "Paprica", "https://example.com/avatar.jpg")));

		// When
		ResultActions result = this.mockMvc.perform(get("/animals"));

		// Then
		result
			.andExpect(jsonPath("$.length()").value(1))
			.andExpect(jsonPath("$.[0].id").value(equalTo(1)))
			.andExpect(jsonPath("$.[0].name").value(equalTo("Paprica")))
			.andExpect(jsonPath("$.[0].avatarUrl").value(not(blankOrNullString())))
			.andExpect(jsonPath("$.[0].description").exists())
			.andExpect(jsonPath("$.[0].rescueDate").exists());
	}
}