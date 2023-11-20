package mch.asset.entity;

import com.amazonaws.annotation.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users", schema = "mch_user")
@Immutable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @Column(name="doc_id")
    private Long docId;

    private String username;

    private String email;

    private String password;

    @Column(name="group_id")
    private int groupId;

    @Column(name="mid")
    private int organizationId;

    private Integer active;

}
