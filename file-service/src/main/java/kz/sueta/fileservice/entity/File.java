package kz.sueta.fileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long file;
    @Column(name = "file_id")
    public String fileId;
    public String label;
    @Column(name = "mime_type")
    public String mimeType;
    public String content;
    @Column(name = "created_at")
    public Timestamp createdAt;
}
