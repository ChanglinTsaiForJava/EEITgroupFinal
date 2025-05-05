package eeit.OldProject.yuuhou.Service;

import java.util.List;
import java.util.Optional;

import eeit.OldProject.yuuhou.Entity.Caregiver;

public interface CaregiversService {


    List<Caregiver> findAll();

    Optional<Caregiver> findById(Long id);

    Caregiver save(Caregiver caregiver);

    void deleteById(Long id);

    Optional<Caregiver> findByEmail(String email);
    
    List<Caregiver> searchByServiceArea(String serviceCity, String serviceDistrict);
    
}