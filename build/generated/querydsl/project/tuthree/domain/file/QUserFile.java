package project.tuthree.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFile is a Querydsl query type for UserFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserFile extends EntityPathBase<UserFile> {

    private static final long serialVersionUID = 446355669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFile userFile = new QUserFile("userFile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath realTitle = createString("realTitle");

    public final StringPath saveTitle = createString("saveTitle");

    public final project.tuthree.domain.room.QStudyRoom studyRoomId;

    public final project.tuthree.domain.post.QPostTestPaper testpaperId;

    public QUserFile(String variable) {
        this(UserFile.class, forVariable(variable), INITS);
    }

    public QUserFile(Path<? extends UserFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFile(PathMetadata metadata, PathInits inits) {
        this(UserFile.class, metadata, inits);
    }

    public QUserFile(Class<? extends UserFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studyRoomId = inits.isInitialized("studyRoomId") ? new project.tuthree.domain.room.QStudyRoom(forProperty("studyRoomId"), inits.get("studyRoomId")) : null;
        this.testpaperId = inits.isInitialized("testpaperId") ? new project.tuthree.domain.post.QPostTestPaper(forProperty("testpaperId"), inits.get("testpaperId")) : null;
    }

}

