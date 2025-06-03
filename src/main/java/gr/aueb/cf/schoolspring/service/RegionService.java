package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.repository.IRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService implements IRegionService{
    private final IRegionRepository regionRepository;

    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }
}
