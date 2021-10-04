package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostTestPaper is a Querydsl query type for PostTestPaper
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostTestPaper extends EntityPathBase<PostTestPaper> {

    private static final long serialVersionUID = -891520176L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostTestPaper postTestPaper = new QPostTestPaper("postTestPaper");

    public final DatePath<java.util.Date> alterAt = createDate("alterAt", java.util.Date.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<project.tuthree.domain.Status> secret = createEnum("secret", project.tuthree.domain.Status.class);

    public final StringPath title = createString("title");

    public final project.tuthree.domain.user.QTeacher userId;

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final DatePath<java.util.Date> writeAt = createDate("writeAt", java.util.Date.class);

    public QPostTestPaper(String variable) {
        this(PostTestPaper.class, forVariable(variable), INITS);
    }

    public QPostTestPaper(Path<? extends PostTestPaper> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostTestPaper(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostTestPaper(PathMetadata metadata, PathInits inits) {
        this(PostTestPaper.class, metadata, inits);
    }

    public QPostTestPaper(Class<? extends PostTestPaper> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new project.tuthree.domain.user.QTeacher(forProperty("userId")) : null;
    }

}

