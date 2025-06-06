package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.RegionInsertDTO;
import gr.aueb.cf.schoolspring.dto.RegionReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.RegionUpdateDTO;
import gr.aueb.cf.schoolspring.mapper.Mapper;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.repository.IRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService implements IRegionService{
    private final IRegionRepository regionRepository;
    private final Mapper mapper;

    @Override
    public List<Region> findAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public RegionReadOnlyDTO findByName(String name) throws EntityNotFoundException {
        Region region = regionRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Region", "Region with name " + name + " not found")
        );
        return mapper.mapToRegionReadOnlyDTO(region);
    }

    @Override
    public RegionReadOnlyDTO findById(Integer id) throws EntityNotFoundException {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Region", "Region with id " + id + " not found")
        );
        return mapper.mapToRegionReadOnlyDTO(region);
    }

    @Override
    public RegionReadOnlyDTO insertRegion(RegionInsertDTO dto) throws EntityAlreadyExistsException {
        if (regionRepository.findByName(dto.name()).isPresent()){
            throw new EntityAlreadyExistsException("Region", "Region with name " + dto.name() + " already exists.");
        }
        Region region = mapper.mapToRegion(dto);
        Region savedRegion = regionRepository.save(region);
        return mapper.mapToRegionReadOnlyDTO(savedRegion);
    }

    @Override
    public RegionReadOnlyDTO updateRegion(RegionUpdateDTO dto) throws EntityNotFoundException, EntityAlreadyExistsException {
        Region region = regionRepository.findById(dto.id()).orElseThrow(
                () -> new EntityNotFoundException("Region", "Region with id " + dto.id() + " not found")
        );
        if (regionRepository.findByName(dto.name()).isPresent()){
            throw new EntityAlreadyExistsException("Region", "Region with name " + dto.name() + " already exists.");
        }
        region.setName(dto.name());
        Region updatedRegion = regionRepository.save(region);
        return mapper.mapToRegionReadOnlyDTO(updatedRegion);
    }

    @Override
    public void deleteRegionById(Integer id) throws EntityNotFoundException, EntityInvalidArgumentException {
        Region region = regionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Region", "Region with id " + id + " not found")
        );
        if (!region.getAllStudents().isEmpty() || !region.getAllTeachers().isEmpty())
                throw new EntityInvalidArgumentException("Region", "Foreign key constraint violation. Region with id " + id + " cannot be deleted");
        regionRepository.deleteById(id);
    }
}
