package kz.sueta.eventservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long category;
    @Column(name = "category_id")
    public String categoryId;
    @Column(name = "category_label")
    public String categoryLabel;
    public Boolean actual;
    public String img;
    public String color;
}
