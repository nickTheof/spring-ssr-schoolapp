package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.model.static_data.Region;

import java.util.List;

public interface IRegionService {
    List<Region> findAllRegions();
}
