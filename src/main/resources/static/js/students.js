window.addEventListener("DOMContentLoaded", function () {
    // JS for deleting student modal
    const deleteLinks = document.querySelectorAll("a[href*='/school/dashboard/students/student/delete']");
    const modal = document.getElementById("deleteModal");
    const confirmBtn = document.getElementById("confirmDeleteBtn");
    const cancelBtn = document.getElementById("cancelDeleteBtn");

    let deleteUuid = "";

    deleteLinks.forEach(link => {
        link.addEventListener("click", e => {
            e.preventDefault();
            // Extract UUID from the href (e.g., "/school/.../delete?uuid=123")
            const urlParams = new URLSearchParams(link.getAttribute("href").split('?')[1]);
            deleteUuid = urlParams.get('uuid');
            modal.classList.remove("hidden");
            modal.classList.add("flex");
        });
    });

    if (confirmBtn) {
        confirmBtn.addEventListener("click", () => {
            if (!deleteUuid) return;

            // Create a hidden form for POST request
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/school/dashboard/students/student/delete';

            // Add UUID input
            const uuidInput = document.createElement('input');
            uuidInput.type = 'hidden';
            uuidInput.name = 'uuid';
            uuidInput.value = deleteUuid;
            form.appendChild(uuidInput);

            // Add CSRF token (required for POST)
            const csrfInput = document.createElement('input');
            csrfInput.type = 'hidden';
            csrfInput.name = '_csrf';
            csrfInput.value = document.querySelector('meta[name="_csrf"]').content; // Ensure CSRF meta tag exists
            form.appendChild(csrfInput);

            // Submit the form
            document.body.appendChild(form);
            form.submit();
        });
    }

    if(cancelBtn) {
        cancelBtn.addEventListener("click", () => {
            modal.classList.remove("flex");
            modal.classList.add("hidden");
            deleteUuid = "";
        });
    }
});