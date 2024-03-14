package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Member;

public interface MemberRepository {
  public Member addMember(Member member);
  public Optional<Member> findById(Long id);
  public Optional<Member> findByUid(String uid);
  public Optional<Member> findByEmail(String email);
  public List<Member> findAll();
}
