package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.dto.UserInsertDTO;
import gr.aueb.cf.schoolspring.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolspring.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/school")
@RequiredArgsConstructor
public class RegisterPageController {
    private final IUserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(RegisterPageController.class);

    @GetMapping("/users/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userInsertDTO", new UserInsertDTO("", "", ""));
        return "register";
    }

    @PostMapping("/users/register")
    public String insertUser(@Valid @ModelAttribute("userInsertDTO") UserInsertDTO dto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes attrs) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        // Χειροκίνητο validation για τον κωδικό
        if (!dto.password().equals(dto.confirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Οι κωδικοί δεν ταιριάζουν.");
            return "register";
        }
        try {
            UserReadOnlyDTO readOnlyDTO = userService.saveUser(dto);
            LOGGER.info("Ο χρήστης με id={}, uuid={}, username={}, ρόλο={} εισήχθη επιτυχώς.", readOnlyDTO.id(), readOnlyDTO.uuid(), readOnlyDTO.username(), readOnlyDTO.role());
            attrs.addFlashAttribute("successMessage", "Ο χρήστης με username: " + readOnlyDTO.username() + "δημιουργήθηκε με επιτυχία");
            return "redirect:/";
        } catch (EntityAlreadyExistsException e) {
            LOGGER.error("User with username={} not inserted", dto.username(), e);
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

    }
}
