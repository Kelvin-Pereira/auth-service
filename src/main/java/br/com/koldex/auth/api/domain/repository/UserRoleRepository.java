package br.com.koldex.auth.api.domain.repository;

import br.com.koldex.auth.api.domain.entity.UserRole;
import br.com.koldex.auth.api.domain.entity.pk.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query("""
        SELECT ur
        FROM UserRole ur
        JOIN FETCH ur.role r
        JOIN FETCH r.context c
        LEFT JOIN FETCH r.roleActions ra
        LEFT JOIN FETCH ra.action a
        WHERE ur.user.id = :userId
          AND c.name = :context
          AND r.indAtivo = 'S'
    """)
    List<UserRole> findRolesByUserAndContext(String userId, String context);
}
