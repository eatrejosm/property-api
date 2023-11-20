package mch.asset.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import java.io.Serializable;
import java.util.List;

@Entity
@Immutable
@Table(name="property", schema="mch_asset")
@Data
@SuppressWarnings("S1948")
public class MchProperty implements Serializable {
    @Id
    private Long id;

}
