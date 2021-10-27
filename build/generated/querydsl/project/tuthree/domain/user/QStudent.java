package project.tuthree.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = 168002268L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudent student = new QStudent("student");

    public final NumberPath<Integer> birth = createNumber("birth", Integer.class);

    public final StringPath cost = createString("cost");

    public final DateTimePath<java.util.Date> createDate = createDateTime("createDate", java.util.Date.class);

    public final StringPath detail = createString("detail");

    public final StringPath email = createString("email");

    public final EnumPath<Grade> grade = createEnum("grade", Grade.class);

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final EnumPath<project.tuthree.domain.Status> notification = createEnum("notification", project.tuthree.domain.Status.class);

    public final StringPath post = createString("post");

    public final StringPath pwd = createString("pwd");

    public final EnumPath<project.tuthree.domain.Status> registration = createEnum("registration", project.tuthree.domain.Status.class);

    public final EnumPath<School> school = createEnum("school", School.class);

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public final StringPath tel = createString("tel");

    public final QUser user;

    public final EnumPath<project.tuthree.domain.Status> userDel = createEnum("userDel", project.tuthree.domain.Status.class);

    public QStudent(String variable) {
        this(Student.class, forVariable(variable), INITS);
    }

    public QStudent(Path<? extends Student> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudent(PathMetadata metadata, PathInits inits) {
        this(Student.class, metadata, inits);
    }

    public QStudent(Class<? extends Student> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

