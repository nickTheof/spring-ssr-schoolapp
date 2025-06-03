package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.UserInsertDTO;
import gr.aueb.cf.schoolspring.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;


public interface IUserService {
    UserReadOnlyDTO saveUser(UserInsertDTO dto)
        throws EntityAlreadyExistsException;
    UserReadOnlyDTO updateUser(Long id, UserUpdateDTO dto)
        throws EntityAlreadyExistsException, EntityNotFoundException;
    void deleteUserByUuid(String uuid) throws EntityNotFoundException;
    void disableUserByUuid(String uuid) throws EntityNotFoundException;
    Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size);
    UserReadOnlyDTO findByUsername(String username) throws EntityNotFoundException;
    Page<UserReadOnlyDTO> getPaginatedUsersByActivityStatus(Boolean isActive, int page, int size);
    Page<UserReadOnlyDTO> getPaginatedUserByRole(Role role, int page, int size);
}
