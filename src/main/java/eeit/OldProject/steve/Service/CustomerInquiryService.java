package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.CustomerInquiry;

public interface CustomerInquiryService {
    void submitInquiryAndSendEmail(CustomerInquiry inquiry);
}
