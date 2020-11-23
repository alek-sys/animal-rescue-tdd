package rescue.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser(User.withUsername("joanne").password(passwordEncoder.encode("joanne1975")).roles("USER").build())
			.withUser(User.withUsername("martin").password(passwordEncoder.encode("secret-password")).roles("USER").build())
			.withUser(User.withUsername("bob").password(passwordEncoder.encode("test")).roles("USER").build())
			.withUser(User.withUsername("alice").password(passwordEncoder.encode("test")).roles("USER").build());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.formLogin().defaultSuccessUrl("http://localhost:3000/rescue").and()
			.logout().logoutSuccessUrl("http://localhost:3000/rescue").and()
			.authorizeRequests()
				.antMatchers("/animals/*/adoption-requests/*").authenticated()
				.antMatchers("/whoami").authenticated()
				.anyRequest().permitAll();
	}
}
