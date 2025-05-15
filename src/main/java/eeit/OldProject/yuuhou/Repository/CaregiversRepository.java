package eeit.OldProject.yuuhou.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eeit.OldProject.yuuhou.Entity.Caregiver;

public interface CaregiversRepository extends JpaRepository<Caregiver, Long> {

    Optional<Caregiver> findByEmail(String email); // 👉 根據 email 找照顧者
 
    boolean existsByEmail(String email); // 👉 判斷 email 是否已存在

    List<Caregiver> findByServiceCityContainingAndServiceDistrictContaining(String serviceCity, String serviceDistrict); // 👉 根據服務地區搜尋
    List<Caregiver> findByServiceCityContaining(String serviceCity); // 當不指定區域時使用
    
//    @Query("SELECT c FROM Caregiver c LEFT JOIN FETCH c.caregiverLicenses WHERE c.caregiverId = :id")
//    Optional<Caregiver> findByIdWithLicenses(@Param("id") Long id); //Rita新增

    

 // ✅ 查詢前三名 ServiceCity
    @Query("SELECT c.serviceCity, COUNT(c.serviceCity) AS count FROM Caregiver c GROUP BY c.serviceCity ORDER BY count DESC")
    List<Object[]> findTopCities();

    // ✅ 查詢前三名 ServiceDistrict
    @Query("SELECT c.serviceDistrict, COUNT(c.serviceDistrict) AS count FROM Caregiver c GROUP BY c.serviceDistrict ORDER BY count DESC")
    List<Object[]> findTopDistricts();
}