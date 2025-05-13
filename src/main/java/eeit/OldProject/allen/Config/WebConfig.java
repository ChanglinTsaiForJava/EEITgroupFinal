package eeit.OldProject.allen.Config;

import org.springframework.context.annotation.Configuration;
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

	
}
