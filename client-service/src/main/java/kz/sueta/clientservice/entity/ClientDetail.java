package kz.sueta.clientservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientDetail {

    @Id
    public Long client;
    @Column(name = "displayname")
    public String displayName;
    public String name;
    public String surname;
    @Column(name = "img_id")
    public String imgId;
    public Timestamp birthdate;
}
