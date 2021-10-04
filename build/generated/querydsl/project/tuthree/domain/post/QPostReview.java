package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostReview is a Querydsl query type for PostReview
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostReview extends EntityPathBase<PostReview> {

    private static final long serialVersionUID = -1142087710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostReview postReview = new QPostReview("postReview");

    public final StringPath content = createString("content");

    public final project.tuthree.domain.room.QStudyRoom id;

    public final NumberPath<Integer> star = createNumber("star", Integer.class);

    public final DateTimePath<java.util.Date> writeAt = createDateTime("writeAt", java.util.Date.class);

    public QPostReview(String variable) {
        this(PostReview.class, forVariable(variable), INITS);
    }

    public QPostReview(Path<? extends PostReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostReview(PathMetadata metadata, PathInits inits) {
        this(PostReview.class, metadata, inits);
    }

    public QPostReview(Class<? extends PostReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new project.tuthree.domain.room.QStudyRoom(forProperty("id"), inits.get("id")) : null;
    }

}

