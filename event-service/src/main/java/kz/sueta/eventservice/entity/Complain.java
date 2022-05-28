package kz.sueta.eventservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long complain;
    @Column(name = "complain_id")
    public String complainId;
    @Column(name = "client_id")
    public String clientId;
    @Column(name = "complain_text")
    public String complainText;
}
