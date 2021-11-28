package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sms_for_auth")
public class SmsForAuth {

    @Id
    private String phone;
    private String code;
    @Column(name = "expired_at")
    private Timestamp expiredAt;
    @Column(name = "is_validate")
    private Boolean isValidated;
}
