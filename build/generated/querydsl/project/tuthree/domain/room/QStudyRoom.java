package project.tuthree.domain.room;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyRoom is a Querydsl query type for StudyRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyRoom extends EntityPathBase<StudyRoom> {

    private static final long serialVersionUID = -1659972683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyRoom studyRoom = new QStudyRoom("studyRoom");

    public final EnumPath<project.tuthree.domain.Status> Status = createEnum("Status", project.tuthree.domain.Status.class);

    public final project.tuthree.domain.user.QStudent studentId;

    public final project.tuthree.domain.user.QTeacher teacherId;

    public QStudyRoom(String variable) {
        this(StudyRoom.class, forVariable(variable), INITS);
    }

    public QStudyRoom(Path<? extends StudyRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyRoom(PathMetadata metadata, PathInits inits) {
        this(StudyRoom.class, metadata, inits);
    }

    public QStudyRoom(Class<? extends StudyRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studentId = inits.isInitialized("studentId") ? new project.tuthree.domain.user.QStudent(forProperty("studentId"), inits.get("studentId")) : null;
        this.teacherId = inits.isInitialized("teacherId") ? new project.tuthree.domain.user.QTeacher(forProperty("teacherId")) : null;
    }

}

