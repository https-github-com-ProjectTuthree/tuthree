package project.tuthree.domain.room;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyRoomInfo is a Querydsl query type for StudyRoomInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyRoomInfo extends EntityPathBase<StudyRoomInfo> {

    private static final long serialVersionUID = 226937347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyRoomInfo studyRoomInfo = new QStudyRoomInfo("studyRoomInfo");

    public final DateTimePath<java.util.Date> checkDate = createDateTime("checkDate", java.util.Date.class);

    public final StringPath cost = createString("cost");

    public final StringPath day = createString("day");

    public final StringPath detail = createString("detail");

    public final StringPath end = createString("end");

    public final QStudyRoom id;

    public final StringPath start = createString("start");

    public final BooleanPath status = createBoolean("status");

    public final StringPath subject = createString("subject");

    public QStudyRoomInfo(String variable) {
        this(StudyRoomInfo.class, forVariable(variable), INITS);
    }

    public QStudyRoomInfo(Path<? extends StudyRoomInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyRoomInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyRoomInfo(PathMetadata metadata, PathInits inits) {
        this(StudyRoomInfo.class, metadata, inits);
    }

    public QStudyRoomInfo(Class<? extends StudyRoomInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStudyRoom(forProperty("id"), inits.get("id")) : null;
    }

}

