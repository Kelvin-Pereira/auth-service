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
@Table(name = "context")
public class Context {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "context")
    @Builder.Default
    private List<Role> roles = new ArrayList<>();
}