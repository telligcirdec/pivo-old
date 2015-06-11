package com.mmdi.projet.pivo;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mmdi.projet.pivo.webapp.PortalServlet;
import com.mmdi.projet.pivo.webapp.vaadin.CurrentSessionServiceRegistration;

@SpringBootApplication
@EnableWebMvc
public class Application {

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public ServletRegistrationBean pivoServlet() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new PortalServlet(), "/app/*", "/VAADIN/*");

		servletRegistrationBean.addInitParameter("widgetset",
				"com.mmdi.projet.pivo.webapp.vaadin.widgetset.PortalWidgetSet");

		return servletRegistrationBean;
	}

	@Bean(name = "felixProperties")
	public PropertiesFactoryBean mapper() {
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocation(new ClassPathResource("config/felix.properties"));
		return bean;
	}

	@Bean
	@Scope(org.springframework.web.context.WebApplicationContext.SCOPE_SESSION)
	public CurrentSessionServiceRegistration serviceRegistration() {
		return new CurrentSessionServiceRegistration();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}