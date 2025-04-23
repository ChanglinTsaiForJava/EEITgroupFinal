package eeit.OldProject.yuni.service;

import eeit.OldProject.yuni.entity.Certificate;

import eeit.OldProject.yuni.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateService{

    @Autowired
    private CertificateRepository certificateRepository;

    public List<Certificate> findAllCertificates() {
        return certificateRepository.findAll();
    }

    public Optional<Certificate> findCertificateById(Integer id) {
        return certificateRepository.findById(id);
    }

    public Certificate saveCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    public void deleteCertificate(Integer id) {
        certificateRepository.deleteById(id);
    }
}
