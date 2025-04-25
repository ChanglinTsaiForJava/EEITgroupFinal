package eeit.OldProject.yuuhou.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Repository.CaregiversRepository;

@Service
public class CaregiverServiceImpl implements CaregiversService {

    @Autowired
    private CaregiversRepository repository;

    
    @Override
    public List<Caregiver> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Caregiver> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Caregiver save(Caregiver caregiver) {
        return repository.save(caregiver);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Caregiver> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}

