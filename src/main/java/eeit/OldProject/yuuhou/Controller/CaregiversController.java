package eeit.OldProject.yuuhou.Controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eeit.OldProject.yuuhou.Entity.Caregiver;
import eeit.OldProject.yuuhou.Service.CaregiversService;

@RestController
@RequestMapping("/api/caregivers") // API 前綴
public class CaregiversController {


    @Autowired
    private CaregiversService caregiversService;

    
    
    @GetMapping
    public List<Caregiver> getAll() {
        return caregiversService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Caregiver> getById(@PathVariable Long id) {
        return caregiversService.findById(id);
    }

    @PostMapping
    public Caregiver create(@RequestBody Caregiver caregiversEntity) {
        return caregiversService.save(caregiversEntity);
    }

    @PutMapping("/{id}")
    public Caregiver update(@PathVariable Long id, @RequestBody Caregiver updatedCaregiver) {
        Optional<Caregiver> existing = caregiversService.findById(id);
        if (existing.isPresent()) {
            updatedCaregiver.setCaregiverId(id); // 👈 確保使用舊 ID
            return caregiversService.save(updatedCaregiver);
        } else {
            throw new RuntimeException("Caregiver not found");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        caregiversService.deleteById(id);
    }


}
