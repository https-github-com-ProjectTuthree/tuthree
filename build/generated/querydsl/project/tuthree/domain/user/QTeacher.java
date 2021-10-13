package project.tuthree.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeacher is a Querydsl query type for Teacher
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacher extends EntityPathBase<Teacher> {

    private static final long serialVersionUID = 607571075L;

    public static final QTeacher teacher = new QTeacher("teacher");

    public final NumberPath<Integer> birth = createNumber("birth", Integer.class);

    public final StringPath certification = createString("certification");

    public final BooleanPath certifyStatus = createBoolean("certifyStatus");

    public final StringPath cost = createString("cost");

    public final DatePath<java.util.Date> create_date = createDate("create_date", java.util.Date.class);

    public final StringPath detail = createString("detail");

    public final StringPath email = createString("email");

    public final EnumPath<Grade> grade = createEnum("grade", Grade.class);

    public final StringPath id = createString("id");

    public final StringPath major = createString("major");

    public final StringPath name = createString("name");

    public final EnumPath<project.tuthree.domain.Status> notification = createEnum("notification", project.tuthree.domain.Status.class);

    public final StringPath post = createString("post");

    public final StringPath pwd = createString("pwd");

    public final EnumPath<project.tuthree.domain.Status> registration = createEnum("registration", project.tuthree.domain.Status.class);

    public final StringPath school = createString("school");

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public final NumberPath<Double> star = createNumber("star", Double.class);

    public final EnumPath<SchoolStatus> status = createEnum("status", SchoolStatus.class);

    public final StringPath tel = createString("tel");

    public QTeacher(String variable) {
        super(Teacher.class, forVariable(variable));
    }

    public QTeacher(Path<? extends Teacher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacher(PathMetadata metadata) {
        super(Teacher.class, metadata);
    }

}

