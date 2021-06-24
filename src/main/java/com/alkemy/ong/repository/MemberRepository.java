package com.alkemy.ong.repository;

import com.alkemy.ong.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface MemberRepository extends JpaRepository <Member, Integer>{
    
}
