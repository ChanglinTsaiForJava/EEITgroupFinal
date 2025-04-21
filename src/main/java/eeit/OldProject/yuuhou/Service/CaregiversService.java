package eeit.OldProject.yuuhou.Service;

import java.util.List;
import java.util.Optional;

import eeit.OldProject.yuuhou.Entity.CaregiversEntity;
public interface CaregiversService {


    List<CaregiversEntity> findAll();

    Optional<CaregiversEntity> findById(Long id);

    CaregiversEntity save(CaregiversEntity caregiversEntity);

    void deleteById(Long id);

    Optional<CaregiversEntity> findByEmail(String email);
    
}