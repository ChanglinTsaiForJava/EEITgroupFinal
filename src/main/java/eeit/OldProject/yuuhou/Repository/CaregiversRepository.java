package eeit.OldProject.yuuhou.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.yuuhou.Entity.Caregiver;

public interface CaregiversRepository extends JpaRepository<Caregiver, Long> {

    Optional<Caregiver> findByEmail(String email); // 👉 根據 email 找照顧者

    boolean existsByEmail(String email); // 👉 判斷 email 是否已存在

    List<Caregiver> findByServiceCityContainingAndServiceDistrictContaining(String serviceCity, String serviceDistrict); // 👉 根據服務地區搜尋
    List<Caregiver> findByServiceCityContaining(String serviceCity); // 當不指定區域時使用

}