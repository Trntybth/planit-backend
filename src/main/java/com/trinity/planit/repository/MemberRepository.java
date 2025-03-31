package com.trinity.planit.repository;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findByName(String name);


    void deleteByUsername(String username);

    Member findByUsername(String username);


    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Member> findByEmail(String email);

}
