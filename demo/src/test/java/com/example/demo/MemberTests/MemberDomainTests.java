// package com.example.demo.MemberTests;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.demo.domain.Member;

// @SpringBootTest
// @Transactional
// public class MemberDomainTests {

//   @Test
//   @DisplayName("회원 도메인 생성 테스트")
//   public void 회원_도메인_생성() {
//     // Given
//     Member member = new Member("uid", "email", "gmail.com", "password", "name");

//     // When
//     // Then
//     Assertions.assertThat(member.getUid()).isEqualTo("uid");
//     Assertions.assertThat(member.getEmailName()).isEqualTo("email");
//     Assertions.assertThat(member.getEmailDomain()).isEqualTo("gmail.com");
//     Assertions.assertThat(member.getPw()).isEqualTo("password");
//     Assertions.assertThat(member.getMemberName()).isEqualTo("name");
//   }
// }