package com.example.demo.MemberTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;

public class MemberServiceTests {

  private final MemberService memberService;
  public MemberServiceTests(MemberRepository memberRepository) {
    this.memberService = new MemberService(memberRepository);
  }

  @AfterEach
  public void afterEach() {
    // memberService.clearStore();
  }

  @Test
  @DisplayName("멤버 서비스 join 테스트")
  public void 회원_서비스_join_테스트() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

    // When
    memberService.join(member);
    memberService.join(member2);

    // Then
    Assertions.assertThat(memberService.findMembers().size()).isEqualTo(2);
  }
}
