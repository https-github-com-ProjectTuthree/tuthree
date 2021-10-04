package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFaq is a Querydsl query type for PostFaq
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostFaq extends EntityPathBase<PostFaq> {

    private static final long serialVersionUID = 1832206028L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFaq postFaq = new QPostFaq("postFaq");

    public final project.tuthree.domain.user.QAdmin admin;

    public final DatePath<java.util.Date> alterAt = createDate("alterAt", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<project.tuthree.domain.Status> secret = createEnum("secret", project.tuthree.domain.Status.class);

    public final StringPath title = createString("title");

    public final EnumPath<FaqType> type = createEnum("type", FaqType.class);

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final DatePath<java.util.Date> writeAt = createDate("writeAt", java.util.Date.class);

    public QPostFaq(String variable) {
        this(PostFaq.class, forVariable(variable), INITS);
    }

    public QPostFaq(Path<? extends PostFaq> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFaq(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFaq(PathMetadata metadata, PathInits inits) {
        this(PostFaq.class, metadata, inits);
    }

    public QPostFaq(Class<? extends PostFaq> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new project.tuthree.domain.user.QAdmin(forProperty("admin")) : null;
    }

}

