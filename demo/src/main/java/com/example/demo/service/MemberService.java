package com.example.demo.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Long join(Member member) {
    validateDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(@NonNull Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다."));
  }

  public Member findByUid(@NonNull String uid) {
    return memberRepository.findByUid(uid)
        .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다." + uid));
  }

  private void validateDuplicateMember(Member member) {
    String uid = member.getUid();
    String emailName = member.getEmailName();
    String emailDomain = member.getEmailDomain();

    if (uid == null || emailName == null || emailDomain == null) {
      throw new IllegalStateException("회원 정보를 모두 입력해주세요.");
    }

    if (validateUidDuplication(uid)) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
    if (validateEmailDuplication(emailName + "@" + emailDomain)) {
      throw new IllegalStateException("이미 존재하는 이메일입니다.");
    }
  }

  private boolean validateUidDuplication(@NonNull String uid) {
    return memberRepository.existsByUid(uid);
  }

  private boolean validateEmailDuplication(@NonNull String email) {
    if (email.split("@").length != 2) {
      throw new IllegalStateException("이메일 형식을 확인해주세요.");
    }

    String emailName = email.split("@")[0];
    String emailDomain = email.split("@")[1];

    if (emailName == null || emailDomain == null) {
      throw new IllegalStateException("이메일 형식을 확인해주세요.");
    }

    return memberRepository.existsByEmailNameAndEmailDomain(emailName, emailDomain);
  }

  // public List<Account> getAccounts(Long memberId) {
  // return null;
  // }

  public Member signin(@NonNull String uid, @NonNull String password) {
    Member member = memberRepository.findByUid(uid)
        .orElse(null);
    if (member != null && !member.getPw().equals(password)) {
      member = null;
    }
    return member;
  }

  public Member signup(@NonNull String uid, @NonNull String password, @NonNull String name, @NonNull String emailName,
      @NonNull String emailDomain) {
    Member member = new Member(uid, emailName, emailDomain, password, name);
    validateDuplicateMember(member);
    return memberRepository.save(member);
  }


  public Member getSigninMember(@NonNull Long id) {
    return memberRepository.findById(id).orElse(null);
  }
}
