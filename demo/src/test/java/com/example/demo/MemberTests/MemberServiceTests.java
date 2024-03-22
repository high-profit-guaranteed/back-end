package com.example.demo.MemberTests;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;

@SpringBootTest
@Transactional
public class MemberServiceTests {

  private final MemberService memberService;

  @Autowired
  public MemberServiceTests(MemberRepository memberRepository) {
    this.memberService = new MemberService(memberRepository);
  }

  @Test
  @DisplayName("멤버 서비스 join 테스트")
  public void 회원_서비스_join_테스트() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

    // When
    Long id1 = memberService.join(member);
    Long id2 = memberService.join(member2);
    if (id1 == null || id2 == null) {
      Assertions.fail("멤버 서비스 join 테스트 실패");
      return;
    }

    // Then
    Assertions.assertThat(memberService.findMembers().size()).isEqualTo(2);
    Assertions.assertThat(memberService.findOne(id1)).isEqualTo(member);
    Assertions.assertThat(memberService.findOne(id2)).isEqualTo(member2);
    Assertions.assertThat(id1).isEqualTo(member.getId());
    Assertions.assertThat(id2).isEqualTo(member2.getId());
  }

  @Test
  @DisplayName("멤버 서비스 uid 조회 테스트")
  public void 회원_서비스_uid_조회() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

    // When
    memberService.join(member);
    memberService.join(member2);

    // Then
    Assertions.assertThat(memberService.findByUid("uid")).isEqualTo(member);
    Assertions.assertThat(memberService.findByUid("uid2")).isEqualTo(member2);
  }

  @Test
  @DisplayName("멤버 서비스 uid 중복 테스트")
  public void 회원_서비스_uid_중복_테스트() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid", "email2", "gmail.com", "password2", "name2");

    // When
    memberService.join(member);

    // Then
    Assertions.assertThatThrownBy(() -> memberService.join(member2))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("이미 존재하는 회원입니다.");
  }

  @Test
  @DisplayName("멤버 서비스 email 중복 테스트")
  public void 회원_서비스_email_중복_테스트() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid2", "email", "gmail.com", "password2", "name2");

    // When
    memberService.join(member);

    // Then
    Assertions.assertThatThrownBy(() -> memberService.join(member2))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("이미 존재하는 이메일입니다.");
  }

  @Test
  @DisplayName("멤버 서비스 회원 정보 미입력 테스트")
  public void 회원_서비스_회원_정보_미입력_테스트() {
    // Given
    Member member = new Member(null, "email", "gmail.com", "password", "name");
    Member member2 = new Member("uid2", null, "gmail.com", "password2", "name2");
    Member member3 = new Member("uid3", "email3", null, "password3", "name3");

    // Then
    Assertions.assertThatThrownBy(() -> memberService.join(member))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("회원 정보를 모두 입력해주세요.");
    Assertions.assertThatThrownBy(() -> memberService.join(member2))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("회원 정보를 모두 입력해주세요.");
    Assertions.assertThatThrownBy(() -> memberService.join(member3))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("회원 정보를 모두 입력해주세요.");
  }

  @Test
  @DisplayName("멤버 서비스 회원 정보 이메일 형식 테스트")
  public void 회원_서비스_회원_정보_이메일_형식_테스트() {
    // Given
    Member member = new Member("uid", "email@", "gmail.com", "password", "name");

    // Then
    Assertions.assertThatThrownBy(() -> memberService.join(member))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("이메일 형식을 확인해주세요.");
  }

  @Test
  @DisplayName("멤버 서비스 로그인 테스트")
  public void 회원_서비스_로그인_테스트() {
    // Given
    Member member = new Member("uid", "email", "gmail.com", "password", "name");

    // When
    memberService.join(member);

    // Then
    Assertions.assertThat(memberService.signin("uid", "password")).isEqualTo(member);
  }

}
