package eeit.OldProject.yuuhou.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import eeit.OldProject.yuuhou.Entity.Caregiver;

public interface CaregiversRepository extends JpaRepository<Caregiver, Long> {

    Optional<Caregiver> findByEmail(String email); // 👉 根據 email 找照顧者

    boolean existsByEmail(String email); // 👉 判斷 email 是否已存在
}