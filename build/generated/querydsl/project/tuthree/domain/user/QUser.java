package project.tuthree.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1210947274L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> birth = createNumber("birth", Integer.class);

    public final DateTimePath<java.util.Date> create_date = createDateTime("create_date", java.util.Date.class);

    public final StringPath email = createString("email");

    public final EnumPath<Grade> grade = createEnum("grade", Grade.class);

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final EnumPath<project.tuthree.domain.Status> notification = createEnum("notification", project.tuthree.domain.Status.class);

    public final StringPath post = createString("post");

    public final StringPath pwd = createString("pwd");

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public final StringPath tel = createString("tel");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

