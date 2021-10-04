package project.tuthree.domain.user;

public interface Persistable<ID>{
    ID getId();
    boolean isNew();
}

