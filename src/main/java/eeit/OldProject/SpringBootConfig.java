package eeit.OldProject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 全部後端路徑都允許跨域
				//允許來自這些網域的請求
				.allowedOrigins( "http://localhost:5173",  // dev
						"http://localhost:4173",  // preview 預設
						//add required ip
						"http://192.168.66.185:4173"
//						"http://192.168.66.54:4173",
//						"http://192.168.66.54:4174",
//                        "http://192.168.36.156:4173",
//                        "http://192.168.36.156:4174",// 有時 Vite 會自動往上找沒被占用的 port test
//						"http://192.168.50.122:4173",
                        // nginx
//                        "http://localhost:6173",
//                        "http://192.168.36.156:6173",
//						"http://localhost:4175","http://192.168.36.96:4173",
						
//						Rita
//						"http://192.168.66.77:4173","http://localhost:5173","http://192.168.66.77:5173"
						) // 前端 localhost:5173
				//自行加入部署網域
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") //
				.allowedHeaders("*")
				.exposedHeaders("Authorization", "Content-Type")
				//接受有 cookie 的跨域請求
				.allowCredentials(true);
	}
}