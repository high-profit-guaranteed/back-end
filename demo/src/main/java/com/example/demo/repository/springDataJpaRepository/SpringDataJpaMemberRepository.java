package com.example.demo.repository.springDataJpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{
  @Override
  public @NonNull Optional<Member> findByUid(@NonNull String uid);

  @Override
  public @NonNull Optional<Member> findByEmailName(@NonNull String email);

  @Override
  public boolean existsByUid(@NonNull String uid);

  @Override
  public boolean existsByEmailName(@NonNull String email);
}
