package gr.aueb.cf.schoolspring.controller;

import gr.aueb.cf.schoolspring.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolspring.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolspring.dto.*;
import gr.aueb.cf.schoolspring.model.static_data.Region;
import gr.aueb.cf.schoolspring.service.IRegionService;
import gr.aueb.cf.schoolspring.service.IStudentService;
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
public class StudentsController {
    private final IStudentService studentService;
    private final IRegionService regionService;
    private final Logger LOGGER = LoggerFactory.getLogger(StudentsController.class);

    @GetMapping("/students")
    public String getPaginatedStudents(
            @RequestParam(defaultValue = "0") int page,  // Default to the first page (0-indexed)
            @RequestParam(defaultValue = "8") int size,  // Default page size
            Model model) {

        Page<StudentReadOnlyDTO> studentsPage = studentService.getPaginatedStudents(page, size);

        model.addAttribute("studentsPage", studentsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentsPage.getTotalPages());

        return "students";
    }

    @GetMapping("/students/student")
    public String getStudentDetails(
            @RequestParam(defaultValue = "") String uuid,
            Model model
    ) {
        try {
            StudentReadOnlyDTO dto = studentService.findStudentByUuid(uuid);
            model.addAttribute("studentReadOnlyDTO", dto);
            return "student-details";
        } catch (EntityNotFoundException e) {
            LOGGER.error("Student with uuid={} not found.", uuid, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "student-details";
        }
    }

    @GetMapping("/students/student/insert")
    public String getStudentInsertForm(Model model) {
        model.addAttribute("studentInsertDTO", new StudentInsertDTO("", "", "", "", "", "", "", "", 0));
        model.addAttribute("regions", regionService.findAllRegions());
        return "students-insert";
    }

    @PostMapping("/students/student/insert")
    public String insertStudent(@Valid @ModelAttribute("studentInsertDTO") StudentInsertDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attrs
                                ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", regionService.findAllRegions());
            return "students-insert";
        }
        try {
            StudentReadOnlyDTO readOnlyDTO = studentService.saveStudent(dto);
            LOGGER.info("Student with id={} inserted", readOnlyDTO.id());
            attrs.addFlashAttribute("successMessage", "Ο μαθητής με vat: " + readOnlyDTO.vat() + " δημιουργήθηκε με επιτυχία");
            return "redirect:/school/dashboard/students";
        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            LOGGER.error("Student with vat={} not inserted.", dto.vat(), e);
            model.addAttribute("regions", regionService.findAllRegions());
            model.addAttribute("errorMessage", e.getMessage());
            return "students-insert";
        }
    }

    @GetMapping("/students/student/update")
    public String getStudentUpdateForm(
            @RequestParam(defaultValue = "") String uuid,
            Model model
    ) {
        try {
            List<Region> regions = regionService.findAllRegions();
            StudentReadOnlyDTO readOnlyDTO = studentService.findStudentByUuid(uuid);
            Region studentRegion = regions.stream().filter(r -> r.getName().equals(readOnlyDTO.region())).findFirst().orElseThrow(() -> new EntityNotFoundException("Region", "Region with name " + readOnlyDTO.region() + " not found."));
            StudentUpdateDTO updateDTO = new StudentUpdateDTO(
                    uuid,
                    readOnlyDTO.firstname(),
                    readOnlyDTO.lastname(),
                    readOnlyDTO.vat(),
                    readOnlyDTO.email(),
                    readOnlyDTO.fatherName(),
                    readOnlyDTO.street(),
                    readOnlyDTO.streetNum(),
                    readOnlyDTO.zipCode(),
                    studentRegion.getId()
            );
            model.addAttribute("studentUpdateDTO", updateDTO);
            model.addAttribute("regions", regions);
            return "students-update";
        } catch (EntityNotFoundException e) {
            LOGGER.error("Not found error.", e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("regions", regionService.findAllRegions());
            return "students-update";
        }
    }

    @PostMapping("/students/student/update")
    public String updateStudent(@Valid @ModelAttribute("studentUpdateDTO") StudentUpdateDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attrs
                                ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("regions", regionService.findAllRegions());
            return "students-update";
        }
        try {
            StudentReadOnlyDTO readOnlyDTO = studentService.updateStudent(dto.uuid(), dto);
            LOGGER.info("Student with uuid={} updated.", readOnlyDTO.id());
            attrs.addFlashAttribute("successMessage", "Ο Μαθητής με uuid " + readOnlyDTO.uuid() + " ενημερώθηκε επιτυχώς.");
            return "redirect:/school/dashboard/students";
        } catch (EntityNotFoundException | EntityInvalidArgumentException | EntityAlreadyExistsException e) {
            LOGGER.error("Student with vat={} not updated.", dto.vat(), e);
            model.addAttribute("regions", regionService.findAllRegions());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("studentUpdateDTO", dto);
            return "students-update";
        }
    }

    @PostMapping("/students/student/delete")
    public String deleteStudent(
        @RequestParam(defaultValue = "") String uuid,
        Model model,
        RedirectAttributes attrs
    ) {
        try {
            studentService.deleteStudentByUuid(uuid);
            LOGGER.info("Student with uuid={} deleted", uuid);
            attrs.addFlashAttribute("successMessage", "Ο μαθητής με uuid " + uuid + " διαγράφηκε επιτυχώς.");
            return "redirect:/school/dashboard/students";
        } catch (EntityNotFoundException e) {
            LOGGER.error("Student with uuid={} not deleted", uuid, e);
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/school/dashboard/students";
        }
    }
}
