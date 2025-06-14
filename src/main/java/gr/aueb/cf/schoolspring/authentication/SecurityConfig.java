package gr.aueb.cf.schoolspring.authentication;

import gr.aueb.cf.schoolspring.core.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Add default csrf security
                // .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorize -> authorize
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers("/school/users/register").permitAll()
                        .requestMatchers("/admin/panel/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/school/dashboard/teachers/teacher/insert").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/school/dashboard/teachers/teacher/update").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/school/dashboard/teachers/teacher/delete").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/school/dashboard/students/student/insert").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/school/dashboard/students/student/update").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/school/dashboard/students/student/delete").hasAnyAuthority(Role.ADMIN.name(), Role.EDITOR.name())
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/school/dashboard", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults()) //username, password, session cookies
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        return http.build();
    }
}
