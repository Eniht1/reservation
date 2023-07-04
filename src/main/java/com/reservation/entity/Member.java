package com.reservation.entity;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.reservation.dto.MemberFormDto;
import com.reservation.entity.Member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name="mamber_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true)
	private String email;
	
	private LocalDateTime birthDate;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		//패스워드 암호화
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		//MemberFormDto -> Member엔티티 객체로 변환
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPassword(password);
		
		return member;	
	}

}
