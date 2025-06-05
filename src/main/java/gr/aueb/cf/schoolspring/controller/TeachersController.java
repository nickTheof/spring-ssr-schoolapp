package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolspring.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolspring.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.service.IRegionService;
import gr.aueb.cf.schoolspring.service.ITeacherService;
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
@RequestMapping("/school/dashboard/")
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

    @GetMapping("/teachers/teacher/insert")
    public String getTeacherInsertForm(Model model) {
        model.addAttribute("teacherInsertDTO", new TeacherInsertDTO("", "", "", "", "", "", "", "", 0));
        model.addAttribute("regions", regionService.findAllRegions());
        return "teachers-insert";
    }

    @PostMapping("/teachers/teacher/insert")
    public String insertTeacher(@Valid @ModelAttribute("teacherInsertDTO") TeacherInsertDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attrs
                                ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", regionService.findAllRegions());
            return "teachers-insert";
        }
        try {
            TeacherReadOnlyDTO readOnlyDTO = teacherService.saveTeacher(dto);
            LOGGER.info("Teacher with id={} inserted", readOnlyDTO.id());
            attrs.addFlashAttribute("successMessage", "Ο καθηγητής με vat: " + readOnlyDTO.vat() + "δημιουργήθηκε με επιτυχία");
            return "redirect:/school/dashboard/teachers";
        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            LOGGER.error("Teacher with vat={} not inserted", dto.vat(), e);
            model.addAttribute("regions", regionService.findAllRegions());
            model.addAttribute("errorMessage", e.getMessage());
            return "teachers-insert";
        }
    }

    @GetMapping("/teachers/teacher/update")
    public String getTeacherUpdateForm(
            @RequestParam(defaultValue = "") String uuid,
            Model model
    ) {
        try {
            List<Region> regions = regionService.findAllRegions();
            TeacherReadOnlyDTO readOnlyDTO = teacherService.findTeacherByUuid(uuid);
            Region teacherRegion = regions.stream().filter(r -> r.getName().equals(readOnlyDTO.region())).findFirst().orElseThrow(() -> new EntityNotFoundException("Region", "Region with name " + readOnlyDTO.region() + " not found."));
            TeacherUpdateDTO updateDTO = new TeacherUpdateDTO(
                    uuid,
                    readOnlyDTO.firstname(),
                    readOnlyDTO.lastname(),
                    readOnlyDTO.vat(),
                    readOnlyDTO.email(),
                    readOnlyDTO.fatherName(),
                    readOnlyDTO.street(),
                    readOnlyDTO.streetNum(),
                    readOnlyDTO.zipCode(),
                    teacherRegion.getId()
            );
            model.addAttribute("teacherUpdateDTO", updateDTO);
            model.addAttribute("regions", regions);
            return "teachers-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("regions", regionService.findAllRegions());
            return "teachers-update";
        }
    }

    @PostMapping("/teachers/teacher/update")
    public String updateTeacher(@Valid @ModelAttribute("teacherUpdateDTO") TeacherUpdateDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attrs
                                ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", regionService.findAllRegions());
            return "teachers-update";
        }
        try {
            TeacherReadOnlyDTO readOnlyDTO = teacherService.updateTeacher(dto.uuid(), dto);
            LOGGER.info("Teacher with uuid={} updated", readOnlyDTO.id());
            attrs.addFlashAttribute("successMessage", "Ο Καθηγητής με uuid " + readOnlyDTO.uuid() + " ενημερώθηκε επιτυχώς.");
            return "redirect:/school/dashboard/teachers";
        } catch (EntityNotFoundException | EntityInvalidArgumentException | EntityAlreadyExistsException e) {
            LOGGER.error("Teacher with vat={} not inserted", dto.vat(), e);
            model.addAttribute("regions", regionService.findAllRegions());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("teacherUpdateDTO", dto);
            return "teachers-update";
        }
    }
}
