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
    memberRepository.findByEmail(member.getEmail())
      .ifPresent(m -> {
        throw new IllegalStateException("이미 존재하는 회원입니다.");
      });
    memberRepository.findByUid(member.getUid())
      .ifPresent(m -> {
        throw new IllegalStateException("이미 존재하는 회원입니다.");
      });
  }

  public List<Account> getAccounts(Long memberId) {
    // TODO: Account 가져오기
    return null;
  }

} 
