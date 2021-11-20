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
public class ClientDetail {

    @Id
    private Long client;
    private String displayName;
    private String name;
    private String surname;
    private byte[] img;
    private Timestamp birthdate;
}
