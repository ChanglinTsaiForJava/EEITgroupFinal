package eeit.OldProject.allen.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	// 靜態資源對應設定（上傳圖片）
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 把 /uploads/** 映射到本地的 uploads 資料夾
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
	}

	// CORS 設定（允許前端跨來源存取）
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 所有 API
				.allowedOrigins("http://localhost:5173") // 前端開發網址
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH").allowedHeaders("*").allowCredentials(true); // 若有登入驗證可保留
																														// cookie
	}

}
