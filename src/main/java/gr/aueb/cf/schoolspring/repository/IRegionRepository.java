package gr.aueb.cf.schoolspring.repository;

import gr.aueb.cf.schoolspring.model.static_data.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionRepository extends JpaRepository<Region, Integer>, JpaSpecificationExecutor<Region> {
}
