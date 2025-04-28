package eeit.OldProject.allen.Config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 把 /uploads/** 映射到本地的 uploads 資料夾
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:uploads/");
	}

}
