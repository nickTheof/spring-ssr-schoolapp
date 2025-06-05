window.addEventListener("DOMContentLoaded", function () {
      // JS for deleting teacher modal
        const deleteLinks = document.querySelectorAll("a[href*='/school/dashboard/teachers/teacher/delete']");
        const modal = document.getElementById("deleteModal");
        const confirmBtn = document.getElementById("confirmDeleteBtn");
        const cancelBtn = document.getElementById("cancelDeleteBtn");

        let deleteUrl = "";

        deleteLinks.forEach(link => {
          link.addEventListener("click", e => {
            e.preventDefault();
            deleteUrl = link.getAttribute("href");
            modal.classList.remove("hidden");
            modal.classList.add("flex");
          });
        });

        confirmBtn.addEventListener("click", () => {
          window.location.href = deleteUrl;
        });

        cancelBtn.addEventListener("click", () => {
          modal.classList.remove("flex");
          modal.classList.add("hidden");
          deleteUrl = "";
        });
      });