package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolspring.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/panel")
@RequiredArgsConstructor
public class AdminPanelController {
    private final IUserService userService;
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
}
