package com.example.demo.repository.springDataJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository{
  
}
