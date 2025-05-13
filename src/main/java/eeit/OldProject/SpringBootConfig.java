package eeit.OldProject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 全部後端路徑都允許跨域
				.allowedOrigins( "http://localhost:5173",  // dev
						"http://localhost:4173",  // preview 預設
						"http://localhost:4174",  // 有時 Vite 會自動往上找沒被占用的 port test
						"http://localhost:4175"   ) // 前端 localhost:5173
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") //
				.allowedHeaders("*")
				.exposedHeaders("Authorization", "Content-Type")
				.allowCredentials(true);
	}
}