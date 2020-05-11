package cn.michael.travel.dao;

import cn.michael.travel.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    User findByCode(String code);

    User findByEmailAndPassword(String email, String password);
}
