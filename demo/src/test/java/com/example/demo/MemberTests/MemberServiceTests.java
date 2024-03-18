package com.example.demo.MemberTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.Member;
import com.example.demo.repository.memoryRepository.MemoryMemberRepository;
import com.example.demo.service.MemberService;

public class MemberServiceTests {

  MemberService memberService = new MemberService(new MemoryMemberRepository());

  @AfterEach
  public void afterEach() {
    memberService.clearStore();
  }

  @Test
  @DisplayName("멤버 서비스 join 테스트")
  public void 회원_서비스_join_테스트() {
    // Given
    Member member = new Member("uid", "email", "password", "name");
    Member member2 = new Member("uid2", "email2", "password2", "name2");

    // When
    memberService.join(member);
    memberService.join(member2);

    // Then
    Assertions.assertThat(memberService.findMembers().size()).isEqualTo(2);
  }
}
