package cn.bysj;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class Config extends WebMvcConfigurerAdapter {

	/****
	 * øÁ”Ú≈‰÷√
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
        /*.allowedOrigins("http://www.lxd666.cn")*/
		.allowedOrigins("http://localhost:8081")
        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
        .maxAge(3600)
        .allowCredentials(true);
	}
}