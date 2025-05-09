package eeit.OldProject.yuuhou.RequestDTO;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class RegisterRequest{
    private String caregiverName;
    private String gender;
    private Date birthday;
    private String email;
    private String password;
    private String phone;
    private String nationality;
    private String languages;
    private Integer yearOfExperience;
    private String serviceCity;
    private String serviceDistrict; 
    private String description;
    private BigDecimal hourlyRate;
    private BigDecimal halfDayRate;
    private BigDecimal fullDayRate;
    private String base64Photo;

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }
}
