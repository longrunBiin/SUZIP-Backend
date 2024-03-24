package Fo.Suzip.repository;

import Fo.Suzip.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    Member findByUserName(String name);

    boolean existsByEmail(String email);
}
