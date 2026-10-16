
package com.spring.ex02;

import java.io.Reader;

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

    public String selectName() {
    	sqlMapper = getInstance();
    	SqlSession session = sqlMapper.openSession();
    	String name = session.selectOne("mapper.member.selectName");
    	return name;

    	}

    	public int selectPwd() {
    	sqlMapper = getInstance();
    	SqlSession session = sqlMapper.openSession();
    	int pwd =session.selectOne("mapper.member.selectPwd");
    	return pwd;
    	}
}

