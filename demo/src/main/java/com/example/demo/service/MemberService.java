package com.example.demo.service;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Account;
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
    String email = member.getEmailName();

    if (uid == null || email == null) {
      throw new IllegalStateException("uid 또는 email이 null입니다.");
    }

    if(validateUidDuplication(uid)) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
    if(validateEmailDuplication(email)) {
      throw new IllegalStateException("이미 존재하는 이메일입니다.");
    }
  }

  public boolean validateUidDuplication(@NonNull String uid) {
    boolean uidDuplicate = memberRepository.existsByUid(uid);
    return uidDuplicate;
  }

  public boolean validateEmailDuplication(@NonNull String email) {
    boolean emailDuplicate = memberRepository.existsByEmailName(email);
    return emailDuplicate;
  }

  public List<Account> getAccounts(Long memberId) {
    // TODO: Account 가져오기
    return null;
  }

  public Member signin(@NonNull String uid, @NonNull String password) {
    Member member = memberRepository.findByUid(uid)
        .orElse(null);
    if (member != null && !member.getPw().equals(password)) {
      member = null;
    }
    return member;
  }

  public Member getSigninMember(@NonNull Long id) {
    return memberRepository.findById(id).orElse(null);
  }

  // public void clearStore() {
  //   memberRepository.clearStore();
  // }

}
