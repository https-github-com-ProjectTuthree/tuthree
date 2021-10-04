package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostAdmin is a Querydsl query type for PostAdmin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostAdmin extends EntityPathBase<PostAdmin> {

    private static final long serialVersionUID = -191127163L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostAdmin postAdmin = new QPostAdmin("postAdmin");

    public final project.tuthree.domain.user.QAdmin admin;

    public final DatePath<java.util.Date> alterAt = createDate("alterAt", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<project.tuthree.domain.Status> secret = createEnum("secret", project.tuthree.domain.Status.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final DatePath<java.util.Date> writeAt = createDate("writeAt", java.util.Date.class);

    public QPostAdmin(String variable) {
        this(PostAdmin.class, forVariable(variable), INITS);
    }

    public QPostAdmin(Path<? extends PostAdmin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostAdmin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostAdmin(PathMetadata metadata, PathInits inits) {
        this(PostAdmin.class, metadata, inits);
    }

    public QPostAdmin(Class<? extends PostAdmin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new project.tuthree.domain.user.QAdmin(forProperty("admin")) : null;
    }

}

