package org.kamogelofoundations.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

/**
 * Spring Security Configuration
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
 * Switches off Spring Boot automatic security configuration
 *
 * @author Tshepo Maema
 */
/*
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;

    final DataSource dataSource;
    @Value("${app.secret}")
    private String applicationSecret;

    @Value("${spring.admin.username}")
    private String adminUsername;

    @Value("${spring.admin.username}")
    private String adminPassword;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    public SpringSecurityConfig(AccessDeniedHandler accessDeniedHandler, DataSource dataSource) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.dataSource = dataSource;
    }*/

    /**
     * HTTPSecurity configurer - roles ADMIN allow to access /admin/** - roles
     * USER allow to access /user/** and /newPost/** - anybody can visit /,
     * /home, /registration, /error, /blog/**, /post/**, /h2-console/** - every
     * other page needs authentication - custom 403 access denied handler
     *
     * @param http
     * @throws java.lang.Exception
     */

/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/request", "/users/register", "/error", "/post/**", "/h2-console/**").permitAll()
                .antMatchers("/", "/user/reset-password-change", "/user/activation-send", "/user/reset-password", "/faq", "/about", "/reset-password", "/password", "/privacy").permitAll()
                .antMatchers("/new/**").hasAnyRole("USER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/category/all")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .rememberMe().key(applicationSecret)
                .tokenValiditySeconds(31536000);
        http.authorizeRequests().antMatchers("/h2/*").access("hasRole('ROLE_ADMIN')");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // Fix for H2 console

    }*/

    /**
     * Authentication details
     *
     * @param auth
     * @throws java.lang.Exception
     */

/*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Database authentication
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());

        // In memory authentication
        auth.inMemoryAuthentication()
                .withUser(adminUsername).password(adminPassword).roles("ADMIN");
    }
    */

    /**
     *
     * @param web
     * @throws Exception Allowing Spring security to scan static folder
     */
  /*  @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "user/**", "/static/**", "/css/**", "/js/**", "/img/**");
    }

    /**
     * Configure and return BCrypt password encoder
     *
     * @return
     
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
*/