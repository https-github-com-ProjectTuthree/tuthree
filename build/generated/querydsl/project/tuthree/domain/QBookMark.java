package project.tuthree.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBookMark is a Querydsl query type for BookMark
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBookMark extends EntityPathBase<BookMark> {

    private static final long serialVersionUID = -1503561894L;

    public static final QBookMark bookMark = new QBookMark("bookMark");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath user1 = createString("user1");

    public final StringPath user2 = createString("user2");

    public QBookMark(String variable) {
        super(BookMark.class, forVariable(variable));
    }

    public QBookMark(Path<? extends BookMark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBookMark(PathMetadata metadata) {
        super(BookMark.class, metadata);
    }

}

