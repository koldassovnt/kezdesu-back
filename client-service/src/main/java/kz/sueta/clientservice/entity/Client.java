package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long client;
    @Column(name = "client_id")
    public String clientId;
    public String phone;
    public String email;
    public String password;
    public Timestamp createdAt;
    public Boolean actual;
    public Boolean blocked;
    @Column(name = "blocked_reason")
    public String blockedReason;
}
