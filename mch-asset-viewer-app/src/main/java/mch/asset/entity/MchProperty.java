package mch.asset.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Immutable
@Table(name="properties", schema="config")
@Data
@SuppressWarnings("S1948")
public class MchProperty implements Serializable {
    @Id
    private Long id;
    private String application;
    private String label;
    private String profile;

    @Column(name = "prop_key")
    private String propKey;

    @Column(name = "prop_value")
    private String propValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime  modifiedAt;


}
