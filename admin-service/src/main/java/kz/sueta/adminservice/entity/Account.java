package kz.sueta.adminservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long account;
    @Column(name = "account_id")
    public String accountId;
    @Column(name = "display_name")
    public String displayName;
    public String phone;
    public String email;
    public String password;
    public Timestamp createdAt;
    public Boolean actual;
}
