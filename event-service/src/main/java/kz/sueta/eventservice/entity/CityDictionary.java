package kz.sueta.eventservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long city;
    @Column(name = "city_id")
    public String cityId;
    @Column(name = "city_label")
    public String cityLabel;
    public Double latitude;
    public Double longitude;
    public Boolean actual;
}
