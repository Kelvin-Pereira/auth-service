package br.com.koldex.auth.api.domain.entity.pk;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleActionId implements Serializable {

    private String role;
    private String action;
}