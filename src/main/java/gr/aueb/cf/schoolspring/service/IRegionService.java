package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.RegionInsertDTO;
import gr.aueb.cf.schoolspring.dto.RegionReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.RegionUpdateDTO;
import gr.aueb.cf.schoolspring.model.static_data.Region;

import java.util.List;

public interface IRegionService {
    List<Region> findAllRegions();
    RegionReadOnlyDTO findByName(String name) throws EntityNotFoundException;
    RegionReadOnlyDTO findById(Integer id) throws EntityNotFoundException;
    RegionReadOnlyDTO insertRegion(RegionInsertDTO dto) throws EntityAlreadyExistsException;
    RegionReadOnlyDTO updateRegion(RegionUpdateDTO dto) throws EntityNotFoundException, EntityAlreadyExistsException;
    void deleteRegionById(Integer id) throws EntityNotFoundException, EntityInvalidArgumentException;
}
