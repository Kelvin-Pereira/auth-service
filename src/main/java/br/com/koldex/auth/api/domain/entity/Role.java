package br.com.koldex.auth.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_role_context_name",
                        columnNames = {"context_id", "name"}
                )
        }
)
public class Role {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "context_id", nullable = false)
    private Context context;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "ind_ativo", nullable = false)
    private String indAtivo;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "role")
    @Builder.Default
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "role")
    @Builder.Default
    private List<RoleAction> roleActions = new ArrayList<>();
}
