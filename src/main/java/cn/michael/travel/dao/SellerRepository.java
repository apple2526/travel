package cn.michael.travel.dao;

import cn.michael.travel.pojo.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SellerRepository extends JpaRepository<Seller, Integer>, JpaSpecificationExecutor<Seller> {
}
