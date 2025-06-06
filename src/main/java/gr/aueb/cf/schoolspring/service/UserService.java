package gr.aueb.cf.schoolspring.service;

import gr.aueb.cf.schoolspring.core.enums.Role;
import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.UserInsertDTO;
import gr.aueb.cf.schoolspring.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.UserUpdateDTO;
import gr.aueb.cf.schoolspring.mapper.Mapper;
import gr.aueb.cf.schoolspring.model.User;
import gr.aueb.cf.schoolspring.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserReadOnlyDTO saveUser(UserInsertDTO dto) throws EntityAlreadyExistsException {
        if(userRepository.findByUsername(dto.username()).isPresent())
            throw new EntityAlreadyExistsException("User", "Ο χρήστης με username " + dto.username() + " υπάρχει ήδη.");
        User user = mapper.mapToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        User savedUser = userRepository.save(user);
        return mapper.mapToUserReadOnlyDTO(savedUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserReadOnlyDTO updateUser(String uuid, UserUpdateDTO dto) throws EntityAlreadyExistsException, EntityNotFoundException {
        User fetchedUser = userRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με uuid " + uuid + " δεν βρέθηκε.")
        );
        Optional<User> fetchUserByUsername = userRepository.findByUsername(dto.username());
        if(fetchUserByUsername.isPresent() && !fetchUserByUsername.get().getId().equals(fetchedUser.getId())) {
            throw new EntityAlreadyExistsException("User", "Ο χρήστης με username " + dto.username() + " υπάρχει ήδη.");
        }
        User toUpdate = mapper.mapToUser(dto, fetchedUser);
        toUpdate.setPassword(passwordEncoder.encode(dto.password()));
        User updatedUser = userRepository.save(toUpdate);
        return mapper.mapToUserReadOnlyDTO(updatedUser);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteUserByUuid(String uuid) throws EntityNotFoundException {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με uuid " + uuid + " δεν βρέθηκε.")
        );
        userRepository.delete(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void toggleStatusActivityByUuid(String uuid) throws EntityNotFoundException {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με " + uuid + " δεν βρέθηκε.")
        );
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Override
    public Page<UserReadOnlyDTO> getPaginatedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(mapper::mapToUserReadOnlyDTO);
    }

    @Override
    public UserReadOnlyDTO findByUsername(String username) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με " + username + " δεν βρέθηκε.")
        );
        return mapper.mapToUserReadOnlyDTO(user);
    }

    @Override
    public UserReadOnlyDTO findByUuid(String uuid) throws EntityNotFoundException {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με uuid " + uuid + " δεν βρέθηκε.")
        );
        return mapper.mapToUserReadOnlyDTO(user);
    }

    @Override
    public Page<UserReadOnlyDTO> getPaginatedUsersByActivityStatus(Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByIsActive(isActive, pageable);
        return userPage.map(mapper::mapToUserReadOnlyDTO);
    }

    @Override
    public Page<UserReadOnlyDTO> getPaginatedUsersByRole(Role role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByRole(role, pageable);
        return userPage.map(mapper::mapToUserReadOnlyDTO);
    }

    @Override
    public void updateRoleByUuid(String uuid, String role) throws EntityNotFoundException {
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new EntityNotFoundException("User", "Ο χρήστης με uuid " + uuid + " δεν βρέθηκε.")
        );
        if (!role.equals(user.getRole().name())) {
            user.setRole(Role.valueOf(role));
            userRepository.save(user);
        }
    }
}
