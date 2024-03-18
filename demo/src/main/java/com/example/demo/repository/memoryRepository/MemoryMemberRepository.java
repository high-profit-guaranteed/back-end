package com.example.demo.repository.memoryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

@Repository
public class MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> store = new HashMap<>();
  private static long sequence = 0L;

  @Override
  public Member addMember(Member member) {
    if (existsByUid(member.getUid())) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
    if (existsByEmail(member.getEmail())) {
      throw new IllegalStateException("이미 존재하는 이메일입니다.");
    }
    member.setId(++sequence);
    store.put(member.getId(), member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Optional<Member> findByUid(String uid) {
    return store.values().stream()
        .filter(member -> member.getUid().equals(uid))
        .findAny();
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    return store.values().stream()
        .filter(member -> member.getEmail().equals(email))
        .findAny();
  }

  @Override
  public List<Member> findAll() {
    return List.copyOf(store.values());
  }

  @Override
  public void clearStore() {
    store.clear();
    sequence = 0L;
  }

  @Override
  public boolean existsByUid(String uid) {
    return store.values().stream()
        .anyMatch(member -> member.getUid().equals(uid));
  }

  @Override
  public boolean existsByEmail(String email) {
    return store.values().stream()
        .anyMatch(member -> member.getEmail().equals(email));
  }
}
