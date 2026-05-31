package br.com.koldex.auth.api.domain.entity;

import br.com.koldex.auth.api.domain.entity.pk.UserRoleId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}