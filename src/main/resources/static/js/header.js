window.addEventListener('DOMContentLoaded', () => {
    const logoutToggle = document.getElementById("logoutToggle");
    if (logoutToggle) {
        logoutToggle.addEventListener("click", function() {
            const dropdownMenu = document.getElementById("dropdownHeaderLogoutMenu");
            dropdownMenu?.classList.toggle("hidden")
        })
    }

})