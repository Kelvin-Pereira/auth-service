package br.com.koldex.auth.api.domain.entity;

import br.com.koldex.auth.api.domain.entity.pk.RoleActionId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_action")
@IdClass(RoleActionId.class)
public class RoleAction {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private Action action;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}