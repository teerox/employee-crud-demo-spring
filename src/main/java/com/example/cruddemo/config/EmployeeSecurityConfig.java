package com.example.cruddemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class EmployeeSecurityConfig {


    // this is to process in a memory user
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        UserDetails user = User.builder()
//                .username("john")
//                .password("{noop}1234")
//                .roles("EMPLOYEE")
//                .build();
//
//        UserDetails manager = User.builder()
//                .username("susan")
//                .password("{noop}1234")
//                .roles("MANAGER", "EMPLOYEE")
//                .build();
//        UserDetails admin = User.builder()
//                .username("mary")
//                .password("{noop}1234")
//                .roles("ADMIN", "EMPLOYEE", "MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(user, manager, admin);
//    }

     //secure rest api
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "api/employee").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "api/employee/**").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.POST, "api/employee").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "api/employee").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "api/employee").hasRole("ADMIN")
                                .requestMatchers("employees/").hasRole("EMPLOYEE")
                                .requestMatchers("employees/leaders/**").hasRole("MANAGER")
                                .requestMatchers("employees/systems/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/employees/access-denied")
                )
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        // use basic authentication
        //http.httpBasic(Customizer.withDefaults());


        // disable csrf
        // in general not required for stateless rest apis
        http.csrf( csrf-> csrf.disable());
        return http.build();
    }

//   //add support for jbdc
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//
//    }

 //    for custom queries
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id = ?");
        System.out.println("userDetailsManager: " + userDetailsManager.getUsersByUsernameQuery());
        return userDetailsManager;
    }


    // NoOpPasswordEncoder is deprecated. This is just for testing purposes.
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    // recommended password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.setPasswordEncoder(passwordEncoder());
//        return userDetailsManager;
//    }

//    @Bean
//    DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }
//
//    @Bean
//    UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//                .roles("USER", "ADMIN")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        users.createUser(admin);
//        return users;
//    }
}
