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
public class Client {

    @Id
    private Long client;
    private String phone;
    private String email;
    private String password;
    private Timestamp createdAt;
    private Boolean actual;
}
