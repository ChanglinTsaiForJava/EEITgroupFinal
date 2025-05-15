package eeit.OldProject.yuuhou.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

	  @Autowired
	    private StatisticsService statisticsService;

	    @GetMapping("/top-cities")
	    public ResponseEntity<Map<String, Integer>> getTopCities() {
	        Map<String, Integer> cities = statisticsService.getTopCities();
	        System.out.println("✅ 傳回城市資料: " + cities);
	        return ResponseEntity.ok(cities);
	    }

	    @GetMapping("/top-districts")
	    public ResponseEntity<Map<String, Integer>> getTopDistricts() {
	        Map<String, Integer> districts = statisticsService.getTopDistricts();
	        System.out.println("✅ 傳回區域資料: " + districts);
	        return ResponseEntity.ok(districts);
}
}
