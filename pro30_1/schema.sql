-- pro30 답변형 게시판 테이블 (Oracle). 이미 만들어 둔 테이블이 있으면 건너뛰어도 됩니다.

CREATE TABLE t_member (
    id        VARCHAR2(20) PRIMARY KEY,
    pwd       VARCHAR2(20),
    name      VARCHAR2(50),
    email     VARCHAR2(50),
    joinDate  DATE DEFAULT sysdate
);

CREATE TABLE t_board (
    articleNO      NUMBER(10) PRIMARY KEY,
    parentNO       NUMBER(10) DEFAULT 0,
    title          VARCHAR2(500) NOT NULL,
    content        VARCHAR2(4000),
    imageFileName  VARCHAR2(200),
    writeDate      DATE DEFAULT sysdate NOT NULL,
    id             VARCHAR2(20)
);

-- 글이 삭제되면 그 글의 이미지 행도 같이 삭제 (ON DELETE CASCADE)
CREATE TABLE t_imageFile (
    imageFileNO    NUMBER(10) PRIMARY KEY,
    imageFileName  VARCHAR2(200),
    regDate        DATE DEFAULT sysdate,
    articleNO      NUMBER(10),
    CONSTRAINT fk_articleNO FOREIGN KEY (articleNO)
        REFERENCES t_board (articleNO) ON DELETE CASCADE
);

-- 테스트용 회원
INSERT INTO t_member(id, pwd, name, email) VALUES ('hong', '1212', '홍길동', 'hong@test.com');
INSERT INTO t_member(id, pwd, name, email) VALUES ('lee',  '1212', '이순신', 'lee@test.com');
COMMIT;
