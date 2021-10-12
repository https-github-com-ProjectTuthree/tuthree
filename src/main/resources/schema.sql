CREATE TABLE admin (
    admin_id  VARCHAR2(20)  NOT NULL,
    admin_pwd  VARCHAR2(30)  NOT NULL,
    PRIMARY KEY (admin_id)
);

CREATE TABLE parent(
    user_id  VARCHAR2(20)  NOT NULL,
    user_pwd  VARCHAR2(30)  NOT NULL,
    name  VARCHAR2(10) NOT NULL,
    email  VARCHAR2(50) NOT NULL,
    tel  VARCHAR2(11) NOT NULL,
    sex  VARCHAR2(2)  NOT NULL,
    birth  NUMBER NOT NULL,
    post  VARCHAR2(100),
    notification  VARCHAR2(5)  NOT NULL,
    grade  VARCHAR2(10) NOT NULL,
    create_date DATE NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE student
(
    user_id      VARCHAR2(20) NOT NULL,
    parent_id    VARCHAR2(20),
    user_pwd     VARCHAR2(30) NOT NULL,
    name         VARCHAR2(10) NOT NULL,
    email        VARCHAR2(50) NOT NULL,
    tel          VARCHAR2(11) NOT NULL,
    sex          VARCHAR2(2) NOT NULL,
    birth        NUMBER  NOT NULL,
    post         VARCHAR2(100),
    notification VARCHAR2(5) NOT NULL,
    grade        VARCHAR2(10) NOT NULL,
    create_date  DATE NOT NULL,
    region       VARCHAR2(30) NOT NULL,
    registration VARCHAR2(5) NOT NULL,
    subject      VARCHAR2(15) NOT NULL,
    cost         VARCHAR2(20)  NOT NULL,
    school       VARCHAR2(20) NOT NULL,
    detail       VARCHAR2(4000) NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (parent_id) REFERENCES parent (user_id)
);

CREATE TABLE teacher (
    user_id  VARCHAR2(20)  NOT NULL,
    user_pwd  VARCHAR2(30)  NOT NULL,
    name  VARCHAR2(10) NOT NULL,
    email  VARCHAR2(50) NOT NULL,
    tel  VARCHAR2(11) NOT NULL,
    sex  VARCHAR2(2)  NOT NULL,
    birth  NUMBER NOT NULL,
    post  VARCHAR2(100),
    grade  VARCHAR2(10) NOT NULL,
    notification  VARCHAR2(5)  NOT NULL,
    create_date DATE NOT NULL,
    region  VARCHAR2(30)  NOT NULL,
    registration  VARCHAR2(5)  NOT NULL,
    subject  VARCHAR2(15)  NOT NULL,
    cost  VARCHAR2(20)  NOT NULL,
    school  VARCHAR2(20)  NOT NULL,
    school_status  VARCHAR2(20)  NOT NULL,
    major  VARCHAR2(20)  NOT NULL,
    certification  VARCHAR2(50)  NOT NULL,
    certify_status  VARCHAR2(5)  NOT NULL,
    detail  VARCHAR2(4000)  NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE child(
    id NUMBER NOT NULL,
    parent_id VARCHAR2(20) NOT NULL,
    student_id VARCHAR2(20) NOT NULL,
    student_name VARCHAR2(20) NOT NULL,
    status VARCHAR2(5) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE log (
    log_id   VARCHAR2(20) NOT NULL,
    time     VARCHAR2(30) NOT NULL,
    ip_addr  VARCHAR2(10) NOT NULL,
    log_type VARCHAR2(10) NOT NULL,
    user_id  VARCHAR2(11) NOT NULL,
    PRIMARY KEY (log_id)
);

CREATE TABLE study_room (
    room_id  NUMBER  NOT NULL,
    teacher_id  VARCHAR2(20)  NOT NULL,
    student_id  VARCHAR2(20)  NOT NULL,
    room_status  VARCHAR2(5)  NOT NULL,
    PRIMARY KEY (room_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(user_id),
    FOREIGN KEY (student_id) REFERENCES student(user_id)
);

CREATE TABLE calendar (
    cal_id  NUMBER  NOT NULL,
    room_id  NUMBER  NOT NULL,
    date  DATE  NOT NULL,
    schedule  VARCHAR2(100)  NOT NULL,
    PRIMARY KEY (cal_id),
    FOREIGN KEY (room_id) REFERENCES study_room(room_id)
);

CREATE TABLE post_testpaper (
                                post_id  VARCHAR2(20)  NOT NULL,
                                user_id  VARCHAR2(20)  NOT NULL,
                                title  VARCHAR2(100)  NOT NULL,
                                content  VARCHAR2(4000)  NOT NULL,
                                view  NUMBER  NOT NULL,
                                write_at  DATE  NOT NULL,
                                alter_at  DATE,
                                secret  VARCHAR2(5)  NOT NULL,
                                PRIMARY KEY (post_id),
                                FOREIGN KEY (user_id) REFERENCES teacher(user_id)
);

CREATE TABLE file (
    file_id  NUMBER  NOT NULL,
    teacher_id  VARCHAR2(20),
    student_id  VARCHAR2(20),
    testpaper_id NUMBER,
    save_title  VARCHAR2(50)  NOT NULL,
    real_title  VARCHAR2(50)  NOT NULL,
    PRIMARY KEY (file_id),
    FOREIGN KEY (teacher_id, student_id) REFERENCES study_room(teacher_id, student_id),
    FOREIGN KEY (testpaper_id) REFERENCES post_testpaper(post_id)
);

CREATE TABLE study_room_info (
    teacher_id  VARCHAR2(20)  NOT NULL,
    student_id  VARCHAR2(20)  NOT NULL,
    subject  VARCHAR2(20)  NOT NULL,
    cost  VARCHAR2(20)  NOT NULL,
    day  VARCHAR2(10)  NOT NULL,
    start  VARCHAR2(10)  NOT NULL,
    end  VARCHAR2(10)  NOT NULL,
    detail  VARCHAR2(2000),
    check_date  DATE  NOT NULL,
    status  VARCHAR2(5)  NOT NULL,
    PRIMARY KEY (teacher_id, student_id),
    FOREIGN KEY (teacher_id, student_id) REFERENCES study_room(teacher_id, student_id)
);

CREATE TABLE post_find (
    post_id  NUMBER  NOT NULL,
    teacher_id  VARCHAR2(20)  NOT NULL,
    student_id  VARCHAR2(20)  NOT NULL,
    view  NUMBER  NOT NULL,
    write_at  DATE  NOT NULL,
    alter_at  DATE,
    PRIMARY KEY (post_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(user_id),
    FOREIGN KEY (student_id) REFERENCES student(user_id)
);



CREATE TABLE post_faq (
    post_id  VARCHAR2(20)  NOT NULL,
    admin_id  VARCHAR2(20)  NOT NULL,
    type  VARCHAR2(50)  NOT NULL,
    title  VARCHAR2(100)  NOT NULL,
    content  VARCHAR2(4000)  NOT NULL,
    view  NUMBER  NOT NULL,
    write_at  DATE  NOT NULL,
    alter_at  DATE,
    secret  VARCHAR2(5)  NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

CREATE TABLE post_notice (
    post_id  VARCHAR2(20)  NOT NULL  PRIMARY KEY,
    admin_id  VARCHAR2(20)  NOT NULL,
    type  VARCHAR2(50)  NOT NULL,
    title  VARCHAR2(100)  NOT NULL,
    content  VARCHAR2(4000)  NOT NULL,
    view  NUMBER  NOT NULL,
    write_at  DATE  NOT NULL,
    alter_at  DATE,
    secret  VARCHAR2(5)  NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

CREATE TABLE post_review (
    teacher_id  VARCHAR2(20)  NOT NULL,
    student_id  VARCHAR2(20)  NOT NULL,
    star  NUMBER  NOT NULL,
    content  VARCHAR2(4000)  NOT NULL,
    write_at  DATE  NOT NULL,
    PRIMARY KEY (teacher_id, student_id),
    FOREIGN KEY (teacher_id, student_id) REFERENCES study_room(teacher_id, student_id)
);

CREATE TABLE chat_room (
    room_id  NUMBER  NOT NULL,
    user_id1  VARCHAR2(20)  NOT NULL,
    user_id2  VARCHAR2(20)  NOT NULL,
    PRIMARY KEY (room_id)
);

CREATE TABLE chat (
    chat_id  NUMBER  NOT NULL,
    room_id  NUMBER  NOT NULL,
    user_id  VARCHAR2(20)  NOT NULL,
    chat_at  DATE  NOT NULL,
    chat_content  VARCHAR2(1000)  NOT NULL,
    PRIMARY KEY (chat_id),
    FOREIGN KEY (room_id) REFERENCES chat_room(room_id)
);

CREATE TABLE post_study (
    post_id  NUMBER  NOT NULL,
    teacher_id  VARCHAR2(20)  NOT NULL,
    student_id  VARCHAR2(20)  NOT NULL,
    date  DATE  NOT NULL,
    number  NUMBER  NOT NULL,
    start  VARCHAR2(10)  NOT NULL,
    end  VARCHAR2(10)  NOT NULL,
    detail  VARCHAR2(2000)  NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (teacher_id, student_id) REFERENCES study_room(teacher_id, student_id)
);




