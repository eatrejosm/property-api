package mch.asset.repository;

import mch.asset.entity.MchProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MchPropertyRepository extends JpaRepository<MchProperty, Long> {


}
