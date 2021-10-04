package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostStudy is a Querydsl query type for PostStudy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostStudy extends EntityPathBase<PostStudy> {

    private static final long serialVersionUID = -174019585L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostStudy postStudy = new QPostStudy("postStudy");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final StringPath detail = createString("detail");

    public final StringPath end = createString("end");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> number = createNumber("number", Long.class);

    public final StringPath start = createString("start");

    public final project.tuthree.domain.room.QStudyRoom studyRoomId;

    public QPostStudy(String variable) {
        this(PostStudy.class, forVariable(variable), INITS);
    }

    public QPostStudy(Path<? extends PostStudy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostStudy(PathMetadata metadata, PathInits inits) {
        this(PostStudy.class, metadata, inits);
    }

    public QPostStudy(Class<? extends PostStudy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyRoomId = inits.isInitialized("studyRoomId") ? new project.tuthree.domain.room.QStudyRoom(forProperty("studyRoomId"), inits.get("studyRoomId")) : null;
    }

}

