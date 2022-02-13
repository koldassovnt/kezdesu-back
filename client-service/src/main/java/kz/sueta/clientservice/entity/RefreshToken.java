package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "client_id")
    public String clientId;
    @Column(nullable = false, unique = true)
    public String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
