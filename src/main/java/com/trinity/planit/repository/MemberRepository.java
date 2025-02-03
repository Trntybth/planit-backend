package com.trinity.planit.repository;

import com.trinity.planit.model.Member;
import com.trinity.planit.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findByName(String name);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
