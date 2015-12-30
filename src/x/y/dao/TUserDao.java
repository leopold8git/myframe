package x.y.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import x.y.entity.User;

/**
 * Created by Administrator on 2015/12/30.
 */
public interface TUserDao extends JpaRepository<User,Integer> {
}
