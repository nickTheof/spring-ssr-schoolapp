window.addEventListener('DOMContentLoaded', function() {
    const mobileMenuToggle = document.getElementById("mobileMenuToggle");
    if (mobileMenuToggle) {
        mobileMenuToggle.addEventListener("click", function() {
            const mobileSubmenu = document.getElementById("mobileSubmenu");
            const mobileMenuIcon = document.getElementById("mobile-menu-icon")
            mobileSubmenu?.classList.toggle("hidden")
            mobileMenuIcon?.classList.toggle("rotate-180")
        })
    }

    const submenuToggleTeachers = document.getElementById("submenu-toggle-teachers")
    if(submenuToggleTeachers) {
        submenuToggleTeachers.addEventListener("click", function() {
            const submenuContentTeachers = document.getElementById("submenu-content-teachers");
            const submenuTeachersIcon = document.getElementById("submenu-teachers-icon")
            submenuContentTeachers?.classList.toggle("hidden")
            submenuTeachersIcon?.classList.toggle("rotate-180")
        })
    }
    const submenuToggleStudents = document.getElementById("submenu-toggle-students")
    if(submenuToggleStudents) {
        submenuToggleStudents.addEventListener("click", function() {
            const submenuContentStudents = document.getElementById("submenu-content-students");
            const submenuStudentsIcon = document.getElementById("submenu-students-icon")
            submenuContentStudents?.classList.toggle("hidden")
            submenuStudentsIcon?.classList.toggle("rotate-180")
        })
    }
})