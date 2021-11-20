package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsForAuth {

    @Id
    private String phone;
    private String code;
    private Timestamp expiredAt;
    private Boolean isValidated;
}
