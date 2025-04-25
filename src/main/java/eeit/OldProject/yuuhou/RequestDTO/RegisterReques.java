package eeit.OldProject.yuuhou.RequestDTO;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class RegisterReques{
    private String caregiverName;
    private String gender;
    private Date birthday;
    private String email;
    private String password;
    private String phone;
    private String nationality;
    private String languages;
    private Integer yearOfExperience;
    private String serviceArea;
    private String description;
    private String photoPath;
    private BigDecimal hourlyRate;
    private BigDecimal halfDayRate;
    private BigDecimal fullDayRate;
}
