package mch.asset.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Immutable
@Table(name="property", schema="mch_asset")
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
    private String prop_value;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    private Date modifiedAt;


}
