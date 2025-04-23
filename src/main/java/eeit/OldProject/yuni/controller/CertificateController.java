package eeit.OldProject.yuni.controller;

import eeit.OldProject.yuni.entity.Certificate;
import eeit.OldProject.yuni.repository.CertificateRepository;
import eeit.OldProject.yuni.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public List<Certificate> getAllCertificates() {
        return certificateService.findAllCertificates();
    }

    @GetMapping("/{id}")
    public Optional<Certificate> getCertificateById(@PathVariable Integer id) {
        return certificateService.findCertificateById(id);
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.saveCertificate(certificate);
    }

//    @PutMapping("/{id}")
//    public Certificate updateCertificate(@PathVariable Integer id, @RequestBody Certificate certificate) {
//        certificate.setCertificateId(id);
//        return certificateService.saveCertificate(certificate);
//    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable Integer id) {
        certificateService.deleteCertificate(id);
    }
}
