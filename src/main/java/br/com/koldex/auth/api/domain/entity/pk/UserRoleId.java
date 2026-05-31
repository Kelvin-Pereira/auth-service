package br.com.koldex.auth.api.domain.entity.pk;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {

    private String user;
    private String role;
}