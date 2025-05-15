package eeit.OldProject.yuuhou.Service;

import eeit.OldProject.yuuhou.Repository.CaregiversRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private CaregiversRepository caregiversRepository;

    public Map<String, Integer> getTopCities() {
        return caregiversRepository.findTopCities().stream()
                .filter(obj -> obj[0] != null) // 過濾掉 null Key
                .limit(3) // 只取前三名
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],   // City 名稱
                        obj -> ((Long) obj[1]).intValue(), // 計數值
                        (v1, v2) -> v1, // 合併重複值（理論上不會有重複）
                        LinkedHashMap::new // 保持插入順序
                ));
    }

    public Map<String, Integer> getTopDistricts() {
        return caregiversRepository.findTopDistricts().stream()
                .filter(obj -> obj[0] != null) // 過濾掉 null Key
                .limit(3) // 只取前三名
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],   // District 名稱
                        obj -> ((Long) obj[1]).intValue(), // 計數值
                        (v1, v2) -> v1, // 合併重複值（理論上不會有重複）
                        LinkedHashMap::new // 保持插入順序
                ));
    }
}
