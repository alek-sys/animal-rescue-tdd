package rescue.animals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class AnimalsControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	JdbcOperations jdbcOperations;

	@MockBean
	Animals animals;

	@Test
	void shouldReturnOkResponse() {
		this.webTestClient.get().uri("/animals")
			.exchange()
			.expectStatus().is2xxSuccessful();
	}

	@Test
	void shouldDisplayAnimalsDetroitStyle() {
		// Detroit / Chicago school of TDD - classicists

		// Given
		this.jdbcOperations.update("insert into animals (name) values ('Paprica')");

		// When
		WebTestClient.ResponseSpec spec = this.webTestClient.get().uri("/animals")
			.exchange()
			.expectStatus().is2xxSuccessful();

		// Then
		spec.expectBodyList(Animal.class)
			.contains(new Animal(1, "Paprica"));
	}

	@Test
	void shouldDisplayAnimalsLondonStyle() {
		// London school of TDD - mockists

		// Given
		when(this.animals.getAll())
			.thenReturn(Collections.singletonList(new Animal(1, "Paprica")));

		// When
		WebTestClient.ResponseSpec spec = this.webTestClient.get().uri("/animals")
			.exchange()
			.expectStatus().is2xxSuccessful();

		// Then
		spec.expectBodyList(Animal.class)
			.contains(new Animal(1, "Paprica"));
	}
}