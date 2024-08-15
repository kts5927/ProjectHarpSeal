package com.projectharpseal.example.projectharpseal;

import com.projectharpseal.TestPractice.controller.MemberController;
import com.projectharpseal.TestPractice.domain.Member;
import com.projectharpseal.TestPractice.dto.MemberResponseDto;
import com.projectharpseal.TestPractice.repository.MemberRepository;
import com.projectharpseal.TestPractice.service.MemberService;
import com.projectharpseal.TestPractice.service.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberTest {

//    @Test
//    @DisplayName("멤버가 생성되는지 확인하는 테스트")
//    void createMember(){
//        Member member = Member.builder().age(10).name("hi").build();
//        Member member = new Member("hi", 10);
//        assertThat(member.getAge()).isEqualTo(10);
//        assertThat(member.getName()).isEqualTo("hi");
//    }
//
//    @Test
//    @DisplayName("멤버의 나이 바뀌는지 확인하는 테스트")
//    void changeAgeTest(){
//        Member member = Member.builder().age(10).name("hi").build();
//        member.changeAge(13);
//        assertThat(member.getAge()).isEqualTo(13);
//    }

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("멤버 만들기")
//    void createMember(){
//        /*given*/
//        Member member1 = Member.builder().name("hi1").age(10).build();
//        Member member2 = Member.builder().name("hi2").age(20).build();
//
//        /*when*/
//        Member result1 = memberRepository.save(member1);
//        Member result2 = memberRepository.save(member2);
//
//        /*then*/
//        assertThat(result1.getAge()).isEqualTo(member1.getAge());
//        assertThat(result2.getAge()).isEqualTo(member2.getAge());
//
//    }
//
//    @Test
//    @DisplayName("멤버의 리스트를 반환 하는지 확인")
//    void MemberList(){
//        /*given */
//        Member member1 = Member.builder().name("hi1").age(10).build();
//        Member member2 = Member.builder().name("hi2").age(20).build();
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        /*when*/
//        List<Member> result = memberRepository.findAll();
//
//        /*then*/
//        assertThat(result.size()).isEqualTo(2);
//    }

//    // Test 주체
//    MemberService memberService;
//
//    // Test 협력자
//    @MockBean
//    MemberRepository memberRepository;
//
//
//    // Test를 실행하기 전마다 MemberService에 가짜 객체를 주입시켜준다.
//    @BeforeEach
//    void setUp(){
//        memberService = new MemberServiceImpl(memberRepository);
//    }
//    @Test
//    @DisplayName("멤버 생성 성공")
//    void createMemberSuccess(){
//    /*given*/
//        Member member3 = Member.builder().name("hi3").age(10).build();
//        ReflectionTestUtils.setField(member3,"id",3l);
//
//        Mockito.when(memberRepository.save(member3)).thenReturn(member3); // 가짜 객체 응답 정의
//    /*when*/
//        Long hi3 = memberService.createMember("hi3", 10);
//    /*then */
//        assertThat(hi3).isEqualTo(3L);
//    }
//
//    @Test
//    @DisplayName("멤버 생성시 member1 과 이름이 같아서 예외 발생")
//    void createMemberFail(){
//    /*given */
//        Member member1 = Member.builder().name("hi1").age(10).build();
//        Mockito.when(memberRepository.findByName("hi1")).thenReturn(Optional.of(member1));
//
//    /*when then*/
//        assertThatThrownBy(() -> memberService.createMember("hi1",10)).isInstanceOf(IllegalStateException.class);
//    }

//
    @Autowired
    MockMvc mvc;

    @MockBean
    MemberServiceImpl memberService;

    @Test
    @DisplayName("리스트 반환받기")
    void getList() throws Exception {
    /* given*/
        List<MemberResponseDto.ListDto> list = List.of(new MemberResponseDto.ListDto("asd", 10)
                , new MemberResponseDto.ListDto("fsd", 12));
        Mockito.when(memberService.findAll()).thenReturn(list);

    /*when then*/
        mvc.perform(MockMvcRequestBuilders.get("/members").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("fsd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("asd"));
    }




}
