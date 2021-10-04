package project.tuthree.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFind is a Querydsl query type for PostFind
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostFind extends EntityPathBase<PostFind> {

    private static final long serialVersionUID = 963819715L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFind postFind = new QPostFind("postFind");

    public final DateTimePath<java.util.Date> alterAt = createDateTime("alterAt", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.tuthree.domain.user.QStudent studentId;

    public final project.tuthree.domain.user.QTeacher teacherId;

    public final NumberPath<Long> view = createNumber("view", Long.class);

    public final DateTimePath<java.util.Date> writeAt = createDateTime("writeAt", java.util.Date.class);

    public QPostFind(String variable) {
        this(PostFind.class, forVariable(variable), INITS);
    }

    public QPostFind(Path<? extends PostFind> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFind(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFind(PathMetadata metadata, PathInits inits) {
        this(PostFind.class, metadata, inits);
    }

    public QPostFind(Class<? extends PostFind> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.studentId = inits.isInitialized("studentId") ? new project.tuthree.domain.user.QStudent(forProperty("studentId"), inits.get("studentId")) : null;
        this.teacherId = inits.isInitialized("teacherId") ? new project.tuthree.domain.user.QTeacher(forProperty("teacherId")) : null;
    }

}

