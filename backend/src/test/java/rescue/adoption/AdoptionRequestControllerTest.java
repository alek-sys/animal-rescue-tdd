package rescue.adoption;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import rescue.security.SecurityConfiguration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdoptionRequestController.class)
@Import(SecurityConfiguration.class)
class AdoptionRequestControllerTest {

	private final Integer TEST_ANIMAL_ID = 1;
	private final Integer TEST_REQUEST_ID = 2;
	private final String TEST_USER_NAME = "test-user";

	@MockBean
	AdoptionRequests adoptionRequests;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		reset(this.adoptionRequests);
	}

	@Nested
	class AddAdoptionRequest {

		@Test
		void shouldOnlyAllowAuthenticatedUsers() throws Exception {
			mockMvc.perform(post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", Matchers.endsWith("/login")));
		}

		@Test
		void shouldReturnCreatedStatus() throws Exception {
			sendAdoptionRequest()
				.andExpect(status().isCreated());
		}

		@Test
		void shouldPassAnimalId() throws Exception {
			sendAdoptionRequest();

			verify(adoptionRequests).adopt(eq(TEST_ANIMAL_ID), anyString(), anyString(), anyString());
		}

		@Test
		void shouldPassEmailAndNotes() throws Exception {
			sendAdoptionRequest();

			verify(adoptionRequests).adopt(any(), anyString(), eq("test@example.com"), eq("Test note"));
		}

		@Test
		void shouldRequireEmail() throws Exception {
			mockMvc.perform(
				post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID)
					.with(user("test-user"))
					.content("{ \"notes\": \"test\" }")
					.header("Content-Type", "application/json"))
				.andExpect(status().isBadRequest());

			verifyNoInteractions(adoptionRequests);
		}

		@Test
		void shouldPassUsername() throws Exception {
			sendAdoptionRequest();

			verify(adoptionRequests).adopt(anyInt(), eq(TEST_USER_NAME), anyString(), anyString());
		}
	}

	@Nested
	class EditAdoptionRequest {

		@Test
		void shouldAllowEditAdoptionRequest() throws Exception {
			// In Detroit you would do:
			// sendAdoptionRequest();
			// send edit request
			// get animals from /animals
			// check request is updated

			// And that's probably about it, that would be the only test. However mocking-style (London school) helps you to define API step by step.
			// Also it helps to define implementation step by step as well.

			sendEditRequest()
				.andExpect(status().isOk());
		}

		@Test
		void shouldPassRequestId() throws Exception {
			sendEditRequest();

			verify(adoptionRequests).editRequest(eq(TEST_REQUEST_ID), anyString(), anyString(), anyString());
		}

		@Test
		void shouldPassNotesAndEmail() throws Exception {
			sendEditRequest();

			verify(adoptionRequests).editRequest(eq(TEST_REQUEST_ID), anyString(), eq("test@example.com"), eq("Test note"));
		}

		@Test
		void shouldPassAdopterName() throws Exception {
			sendEditRequest();

			verify(adoptionRequests).editRequest(any(), eq(TEST_USER_NAME), anyString(), anyString());
		}

		@Test
		void shouldReturnNotFoundIfRequestNotFound() throws Exception {
			doThrow(RequestNotFoundException.class)
				.when(adoptionRequests)
				.editRequest(anyInt(), anyString(), anyString(), anyString());

			sendEditRequest()
				.andExpect(status().isNotFound());
		}
	}

	private ResultActions sendEditRequest() throws Exception {
		return mockMvc.perform(
			put("/animals/{id}/adoption-requests/{requestId}", TEST_ANIMAL_ID, TEST_REQUEST_ID)
				.content(testRequestJson())
				.header("Content-Type", "application/json")
				.with(user(TEST_USER_NAME)));
	}

	private ResultActions sendAdoptionRequest() throws Exception {
		return this.mockMvc.perform(
			post("/animals/{id}/adoption-requests/", TEST_ANIMAL_ID)
				.with(user(TEST_USER_NAME))
				.content(testRequestJson())
				.header("Content-Type", "application/json"));
	}

	private String testRequestJson() {
		//language=JSON
		return "{ \"email\": \"test@example.com\", \"notes\": \"Test note\" }";
	}
}