package com.example.demo.MemberTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.memoryRepository.MemoryMemberRepository;

public class MemberRepositoryTests {
  MemberRepository memberRepository = new MemoryMemberRepository();

  @AfterEach
  public void afterEach() {
    memberRepository.clearStore();
  }

  @Test
  @DisplayName("멤버 Repository 테스트")
  public void 회원_멤버_추가() {
    // Given
    Member member1 = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid2", "email2", "password2", "name2");

    // When
    memberRepository.addMember(member1);
    memberRepository.addMember(member2);

    // Then
    Assertions.assertThat(memberRepository.findByUid("uid").get().getEmail()).isEqualTo("email");
    Assertions.assertThat(memberRepository.findByUid("uid2").get().getEmail()).isEqualTo("email2");
  }

  @Test
  @DisplayName("멤버 uid 중복 검사 테스트")
  public void 회원_uid_중복_검사() {
    // Given
    Member member1 = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid", "email2", "password2", "name2");

    // When
    memberRepository.addMember(member1);
    try {
      memberRepository.addMember(member2);
      Assertions.fail("예외가 발생해야 합니다.");
    } catch (IllegalStateException e) {
      // Then
      Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
  }

  @Test
  @DisplayName("멤버 email 중복 검사 테스트")
  public void 회원_email_중복_검사() {
    // Given
    Member member1 = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid2", "email", "password2", "name2");

    // When
    memberRepository.addMember(member1);
    try {
      memberRepository.addMember(member2);
      Assertions.fail("예외가 발생해야 합니다.");
    } catch (IllegalStateException e) {
      // Then
      Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }
  }

  @Test
  @DisplayName("멤버 전체 조회 테스트")
  public void 회원_전체_조회() {
    // Given
    Member member1 = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid2", "email2", "password2", "name2");

    // When
    memberRepository.addMember(member1);
    memberRepository.addMember(member2);

    // Then
    Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
  }

  @Test
  @DisplayName("멤버 단일 조회 테스트")
  public void 회원_단일_조회() {
    // Given
    Member member1 = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid2", "email2", "password2", "name2");

    // When
    memberRepository.addMember(member1);
    memberRepository.addMember(member2);

    // Then
    Assertions.assertThat(memberRepository.findById(1L).get().getUid()).isEqualTo("uid");
    Assertions.assertThat(memberRepository.findById(2L).get().getUid()).isEqualTo("uid2");
    Assertions.assertThat(memberRepository.findByUid("uid").get().getId()).isEqualTo(1L);
    Assertions.assertThat(memberRepository.findByUid("uid2").get().getId()).isEqualTo(2L);
    Assertions.assertThat(memberRepository.findByEmail("email").get().getId()).isEqualTo(1L);
    Assertions.assertThat(memberRepository.findByEmail("email2").get().getId()).isEqualTo(2L);
  }
}
