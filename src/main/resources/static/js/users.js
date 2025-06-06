
  window.addEventListener("DOMContentLoaded", function () {
    const deleteLinks = document.querySelectorAll(".delete-user-link");
    const modal = document.getElementById("deleteModal");
    const cancelBtn = document.getElementById("cancelDeleteBtn");
    const uuidInput = document.getElementById("deleteUuidInput");

    deleteLinks.forEach(link => {
      link.addEventListener("click", e => {
        e.preventDefault();
        const uuid = link.getAttribute("data-uuid");
        uuidInput.value = uuid;
        modal.classList.remove("hidden");
        modal.classList.add("flex");
      });
    });

    if (cancelBtn) {
        cancelBtn.addEventListener("click", () => {
          modal.classList.remove("flex");
          modal.classList.add("hidden");
          uuidInput.value = "";
    });
   }
  });
