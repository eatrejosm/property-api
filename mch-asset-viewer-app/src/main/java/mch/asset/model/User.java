package mch.asset.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Immutable;


@Table(name="users", schema = "mch_user")
@Entity
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @Column(name = "doc_id")
    private Long docId;

    @NotBlank
    @Size(max = 300)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 150)
    @Column(name = "password")
    private String password;

    @Column(name = "group_id")
    private Integer securityGroupId;

    @Column(name = "mid")
    private Integer organizationId;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private Integer active;
}