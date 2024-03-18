package com.example.demo.service;

import java.util.List;

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
    memberRepository.addMember(member);
    return member.getId();
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다."));
  }

  public Member findByUid(String uid) {
    return memberRepository.findByUid(uid)
        .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다." + uid));
  }

  private void validateDuplicateMember(Member member) {
    if(validateUidDuplication(member.getUid())) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
    if(validateEmailDuplication(member.getEmail())) {
      throw new IllegalStateException("이미 존재하는 이메일입니다.");
    }
  }

  public boolean validateUidDuplication(String uid) {
    boolean uidDuplicate = memberRepository.existsByUid(uid);
    return uidDuplicate;
  }

  public boolean validateEmailDuplication(String email) {
    boolean emailDuplicate = memberRepository.existsByEmail(email);
    return emailDuplicate;
  }

  public List<Account> getAccounts(Long memberId) {
    // TODO: Account 가져오기
    return null;
  }

  public Member signin(String uid, String password) {
    Member member = memberRepository.findByUid(uid)
        .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다." + uid));
    if (!member.getPassword().equals(password)) {
      throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }
    return member;
  }

  public void clearStore() {
    memberRepository.clearStore();
  }

}
