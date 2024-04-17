// package com.example.demo.MemberTests;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.demo.domain.Member;
// import com.example.demo.repository.MemberRepository;

// @SpringBootTest
// @Transactional
// public class MemberRepositoryTests {

//   @Autowired
//   MemberRepository memberRepository;

//   @Test
//   @DisplayName("멤버 Repository 테스트")
//   public void 회원_멤버_추가() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");
//     Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

//     // When
//     memberRepository.save(member1);
//     memberRepository.save(member2);

//     // Then
//     Assertions.assertThat(memberRepository.findByUid("uid").get().getEmailName()).isEqualTo("email");
//     Assertions.assertThat(memberRepository.findByUid("uid2").get().getEmailName()).isEqualTo("email2");
//   }

//   @Test
//   @DisplayName("멤버 전체 조회 테스트")
//   public void 회원_전체_조회() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");
//     Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

//     // When
//     memberRepository.save(member1);
//     memberRepository.save(member2);

//     // Then
//     Assertions.assertThat(memberRepository.findAll().size()).isEqualTo(2);
//   }

//   @Test
//   @DisplayName("멤버 uid 조회 테스트")
//   public void 회원_uid_조회() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");
//     Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

//     // When
//     memberRepository.save(member1);
//     memberRepository.save(member2);

//     // Then
//     Assertions.assertThat(memberRepository.findByUid("uid").get().getEmailName()).isEqualTo("email");
//     Assertions.assertThat(memberRepository.findByUid("uid2").get().getEmailName()).isEqualTo("email2");
//   }

//   @Test
//   @DisplayName("멤버 email 조회 테스트")
//   public void 회원_email_조회() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");
//     Member member2 = new Member("uid2", "email2", "gmail.com", "password2", "name2");

//     // When
//     memberRepository.save(member1);
//     memberRepository.save(member2);

//     // Then
//     Assertions.assertThat(memberRepository.findByEmailNameAndEmailDomain("email", "gmail.com").get().getUid()).isEqualTo("uid");
//     Assertions.assertThat(memberRepository.findByEmailNameAndEmailDomain("email2", "gmail.com").get().getUid()).isEqualTo("uid2");
//   }

//   @Test
//   @DisplayName("멤버 uid 중복 검사 테스트")
//   public void 회원_uid_중복_검사() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");

//     // When
//     memberRepository.save(member1);

//     // Then
//     Assertions.assertThat(memberRepository.existsByUid("uid")).isTrue();
//     Assertions.assertThat(memberRepository.existsByUid("uid2")).isFalse();
//   }

//   @Test
//   @DisplayName("멤버 email 중복 검사 테스트")
//   public void 회원_email_중복_검사() {
//     // Given
//     Member member1 = new Member("uid", "email", "gmail.com", "password", "name");

//     // When
//     memberRepository.save(member1);

//     // Then
//     Assertions.assertThat(memberRepository.existsByEmailNameAndEmailDomain("email", "gmail.com")).isTrue();
//     Assertions.assertThat(memberRepository.existsByEmailNameAndEmailDomain("email2", "gmail.com")).isFalse();
//   }
// }
