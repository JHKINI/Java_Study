package com.spring.ex03;

import java.io.Reader;
import java.util.List;

import com.spring.ex01.MemberVO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {

    private static SqlSessionFactory sqlMapper = null;

    public static SqlSessionFactory getInstance() {

        if (sqlMapper == null) {

            try {

                String resource = "mybatis/SqlMapConfig.xml";

                Reader reader = Resources.getResourceAsReader(resource);

                sqlMapper = new SqlSessionFactoryBuilder().build(reader);

                reader.close();

            } catch (Exception e) {

                e.printStackTrace();

                throw new RuntimeException(e);

            }

        }

        return sqlMapper;
    }

    public List<MemberVO> selectAllMemberList() {

        sqlMapper = getInstance();

        SqlSession session = sqlMapper.openSession();

        List<MemberVO> membersList = null;

        membersList =
                session.selectList("mapper.member.selectAllMemberList");

        session.close();

        return membersList;
    }

    public MemberVO selectMemberById(String id) {

        sqlMapper = getInstance();

        SqlSession session = sqlMapper.openSession();

        MemberVO memberVO =
                session.selectOne("mapper.member.selectMemberById", id);

        session.close();

        return memberVO;
    }

    public List<MemberVO> selectMemberByPwd(int pwd) {

        sqlMapper = getInstance();

        SqlSession session = sqlMapper.openSession();

        List<MemberVO> membersList = null;

        membersList =
                session.selectList("mapper.member.selectMemberByPwd", pwd);

        session.close();

        return membersList;
    }
}