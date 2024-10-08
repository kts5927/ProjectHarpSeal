package com.projectharpseal.TestPractice.repository;


import com.projectharpseal.TestPractice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
}
