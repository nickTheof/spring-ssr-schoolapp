package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.*;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.service.IRegionService;
import gr.aueb.cf.schoolspring.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/panel")
@RequiredArgsConstructor
public class AdminPanelController {
    private final IUserService userService;
    private final IRegionService regionService;
    private final Logger LOGGER = LoggerFactory.getLogger(AdminPanelController.class);

    @GetMapping("/users")
    public String getPaginatedUsers(
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model) {
        Page<UserReadOnlyDTO> usersPage;
        if (status == null) {
            usersPage = userService.getPaginatedUsers(page, size);
        } else {
            usersPage = userService.getPaginatedUsersByActivityStatus(status, page, size);
        }
        List<String> allRoles = List.of("ADMIN", "EDITOR", "READER");
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("status", status);
        model.addAttribute("allRoles", allRoles);
        return "users";
    }

    @PostMapping("/users/toggle-activation")
    public String toggleUserActivation(
            @RequestParam(defaultValue = "") String uuid,
            Model model,
            RedirectAttributes attrs
    ) {
        try {
            userService.toggleStatusActivityByUuid(uuid);
            LOGGER.info("Status activity of user with uuid={} updated", uuid);
        } catch (EntityNotFoundException e) {
            LOGGER.error("User with uuid={} not found.", uuid, e);
            attrs.addFlashAttribute("errorMessage", "Προέκυψε σφάλμα κατά την αλλαγή κατάστασης του χρήστη με uuid " + uuid);
        }
        return "redirect:/admin/panel/users";
    }

    @PostMapping("/users/update-role")
    public String updateUserRole(
            @RequestParam(defaultValue = "") String uuid,
            @RequestParam(defaultValue = "") String role,
            RedirectAttributes attrs
    ) {
        try {
            userService.updateRoleByUuid(uuid, role);
            LOGGER.info("Role of User with uuid={} updated.", uuid);
        } catch (EntityNotFoundException e) {
            LOGGER.error("User with uuid={} not found.", uuid, e);
            attrs.addFlashAttribute("errorMessage", "Προέκυψε σφάλμα κατά την αλλαγή ρόλου του χρήστη με uuid " + uuid);
        }
        return "redirect:/admin/panel/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(
            @RequestParam(defaultValue = "") String uuid,
            RedirectAttributes attrs
    ) {
        try {
            userService.deleteUserByUuid(uuid);
            LOGGER.info("User with uuid={} deleted.", uuid);
            attrs.addFlashAttribute("successMessage", "Ο Χρήστης με uuid " + uuid + "διαγράφηκε επιτυχώς");
        } catch (EntityNotFoundException e) {
            LOGGER.error("User with uuid={} not found.", uuid, e);
            attrs.addFlashAttribute("errorMessage", "Προέκυψε σφάλμα κατά την διαγραφή του χρήστη με uuid " + uuid);
        }
        return "redirect:/admin/panel/users";
    }

    @GetMapping("/users/insert")
    public String getUserInsertForm(Model model) {
        List<String> allRoles = List.of("ADMIN", "EDITOR", "READER");
        model.addAttribute("userInsertDTO", new UserInsertByAdminDTO("", "", "", Boolean.TRUE, ""));
        model.addAttribute("allRoles", allRoles);
        return "users-insert";
    }

    @PostMapping("/users/insert")
    public String userInsert(
            @Valid @ModelAttribute("userInsertDTO") UserInsertByAdminDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes attrs
    ) {
        List<String> allRoles = List.of("ADMIN", "EDITOR", "READER");
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", allRoles);
            return "users-insert";
        }

        if (!dto.password().equals(dto.confirmPassword())) {
            model.addAttribute("allRoles", allRoles);
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Οι κωδικοί δεν ταιριάζουν.");
            return "users-insert";
        }

        try {
            UserReadOnlyDTO readOnlyDTO = userService.saveUser(dto);
            LOGGER.info("Ο χρήστης με id={}, uuid={}, username={}, isActive={}, ρόλο={} εισήχθη επιτυχώς.", readOnlyDTO.id(), readOnlyDTO.uuid(), readOnlyDTO.username(), readOnlyDTO.isActive(), readOnlyDTO.role());
            attrs.addFlashAttribute("successMessage", "Ο χρήστης με username: " + readOnlyDTO.username() + "δημιουργήθηκε με επιτυχία");
            return "redirect:/admin/panel/users";
        } catch (EntityAlreadyExistsException e) {
            LOGGER.error("User with username={} not inserted", dto.username(), e);
            model.addAttribute("allRoles", allRoles);
            model.addAttribute("errorMessage", e.getMessage());
            return "users-insert";
        }
    }

    @GetMapping("/regions")
    public String getRegionsAdminPanel(Model model) {
        List<Region> regions = regionService.findAllRegions();
        model.addAttribute("regions", regions);
        model.addAttribute("regionDTO", new RegionInsertDTO(""));
        return "regions";
    }

    @GetMapping("/regions/edit/{id}")
    public String showEditRegionForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Region> regions = regionService.findAllRegions();
            RegionReadOnlyDTO regionReadOnlyDTO = regionService.findById(id);
            model.addAttribute("regionDTO", new RegionUpdateDTO(regionReadOnlyDTO.id(), regionReadOnlyDTO.name()));
            model.addAttribute("regions", regions);
            model.addAttribute("editMode", true);
            return "regions";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Η περιοχή δεν βρέθηκε.");
            return "redirect:/admin/panel/regions";
        }
    }

    @PostMapping("/regions/insert")
    public String insertRegion(
            @Valid @ModelAttribute("regionDTO") RegionInsertDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes attrs
    ) {
        List<Region> regions = regionService.findAllRegions();

        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", regions);
            return "regions";
        }
        try {
            regionService.insertRegion(dto);
            LOGGER.info("Region with name={} inserted successfully.", dto.name());
            attrs.addFlashAttribute("successMessage", "Η περιοχή με όνομα " + dto.name() + " προστέθηκε επιτυχώς");
            return "redirect:/admin/panel/regions";
        } catch (EntityAlreadyExistsException e) {
            LOGGER.error("Region with name={} not inserted.", dto.name(), e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("regions", regions);
            return "regions";
        }
    }

    @PostMapping("/regions/update")
    public String updateRegion(
            @Valid @ModelAttribute("regionDTO") RegionUpdateDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes attrs
    ) {
        List<Region> regions = regionService.findAllRegions();
        if (bindingResult.hasErrors()) {
            LOGGER.error("Region with id={} not updated.", dto.id());
            attrs.addFlashAttribute("errorMessage", bindingResult.getFieldError("name").getDefaultMessage());
            return "redirect:/admin/panel/regions";
        }
        try {
            regionService.updateRegion(dto);
            LOGGER.info("Region with id={} updated successfully", dto.id());
            attrs.addFlashAttribute("successMessage", "Η περιοχή με id " + dto.id() + " ενημερώθηκε επιτυχώς");
            return "redirect:/admin/panel/regions";
        } catch (EntityNotFoundException | EntityAlreadyExistsException e) {
            LOGGER.error("Region with id={} not updated.", dto.id(), e);
            model.addAttribute("errorMessage", "Η περιοχή με όνομα " + dto.name() + " υπάρχει ήδη.");
            model.addAttribute("regions", regions);
            return "regions";
        }

    }

    @PostMapping("/regions/delete")
    public String deleteRegion(@RequestParam("id") Integer id,
                               RedirectAttributes attrs) {
        try {
            regionService.deleteRegionById(id);
            LOGGER.info("H περιοχή με id={} διαγράφηκε επιτυχώς", id);
            attrs.addFlashAttribute("successMessage", "H περιοχή με id " + id + " διαγράφηκε επιτυχώς");
        } catch (EntityNotFoundException | EntityInvalidArgumentException e) {
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            LOGGER.error("Fail to delete Region with id={}.", id, e);
        }
        return "redirect:/admin/panel/regions";
    }
}
