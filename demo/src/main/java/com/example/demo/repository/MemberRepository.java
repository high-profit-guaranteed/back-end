package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.example.demo.domain.Member;

public interface MemberRepository {

  public Member save(Member member);

  public Optional<Member> findById(@NonNull Long id);

  public Optional<Member> findByUid(@NonNull String uid);

  public Optional<Member> findByEmailNameAndEmailDomain(@NonNull String emailName, @NonNull String emailDomain);

  public List<Member> findAll();

  /*
   * 유효성 검사 - 중복 체크
   * 중복 : true
   * 중복이 아닌 경우 : false
   */
  public boolean existsByUid(@NonNull String uid);

  public boolean existsByEmailNameAndEmailDomain(@NonNull String emailName, @NonNull String emailDomain);
}
