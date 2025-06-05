package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolspring.service.IRegionService;
import gr.aueb.cf.schoolspring.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/school/dashboard")
@RequiredArgsConstructor
public class TeachersController {
    private final ITeacherService teacherService;
    private final IRegionService regionService;
    private final Logger LOGGER = LoggerFactory.getLogger(TeachersController.class);

    @GetMapping("/teachers")
    public String getPaginatedTeachers(
            @RequestParam(defaultValue = "0") int page,  // Default to the first page (0-indexed)
            @RequestParam(defaultValue = "8") int size,  // Default page size
            Model model) {

        Page<TeacherReadOnlyDTO> teachersPage = teacherService.getPaginatedTeachers(page, size);

        model.addAttribute("teachersPage", teachersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", teachersPage.getTotalPages());

        return "teachers";
    }

    @GetMapping("/teachers/teacher")
    public String getTeacherDetails(
            @RequestParam(defaultValue = "") String uuid,
            Model model
    ) {
        try {
            TeacherReadOnlyDTO dto = teacherService.findTeacherByUuid(uuid);
            model.addAttribute("teacherReadOnlyDTO", dto);
            return "teacher-details";
        } catch (EntityNotFoundException e) {
            LOGGER.error("Teacher with uuid={} not found", uuid, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "teacher-details";
        }
    }
}
