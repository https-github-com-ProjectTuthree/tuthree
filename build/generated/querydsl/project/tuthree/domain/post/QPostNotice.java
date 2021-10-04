package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostNotice is a Querydsl query type for PostNotice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostNotice extends EntityPathBase<PostNotice> {

    private static final long serialVersionUID = -1247428766L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostNotice postNotice = new QPostNotice("postNotice");

    public final project.tuthree.domain.user.QAdmin admin;

    public final DatePath<java.util.Date> alterAt = createDate("alterAt", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<project.tuthree.domain.Status> secret = createEnum("secret", project.tuthree.domain.Status.class);

    public final StringPath title = createString("title");

    public final EnumPath<NoticeType> type = createEnum("type", NoticeType.class);

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final DatePath<java.util.Date> writeAt = createDate("writeAt", java.util.Date.class);

    public QPostNotice(String variable) {
        this(PostNotice.class, forVariable(variable), INITS);
    }

    public QPostNotice(Path<? extends PostNotice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostNotice(PathMetadata metadata, PathInits inits) {
        this(PostNotice.class, metadata, inits);
    }

    public QPostNotice(Class<? extends PostNotice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new project.tuthree.domain.user.QAdmin(forProperty("admin")) : null;
    }

}

