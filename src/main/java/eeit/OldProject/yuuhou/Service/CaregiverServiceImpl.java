package eeit.OldProject.yuuhou.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuuhou.Entity.CaregiversEntity;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;

@Service
public class CaregiverServiceImpl implements CaregiversService {

    @Autowired
    private CaregiversRepository repository;

    
    @Override
    public List<CaregiversEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CaregiversEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public CaregiversEntity save(CaregiversEntity caregiversEntity) {
        return repository.save(caregiversEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<CaregiversEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

