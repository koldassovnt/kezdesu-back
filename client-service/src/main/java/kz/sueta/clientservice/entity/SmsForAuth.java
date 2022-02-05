package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sms_for_auth")
public class SmsForAuth {

    @Id
    public String phone;
    public String code;
    @Column(name = "expired_at")
    public Timestamp expiredAt;
    @Column(name = "is_validate")
    public Boolean isValidated;
}
