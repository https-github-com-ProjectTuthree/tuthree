package project.tuthree.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    Long deleteByUserId(String userId);
}
