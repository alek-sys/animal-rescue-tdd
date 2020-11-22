package rescue.security;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/whoami")
class WhoAmIController {

	@GetMapping
	String whoami(@Nullable Principal principal) {
		if (principal == null) {
			return "";
		}

		return principal.getName();
	}
}
