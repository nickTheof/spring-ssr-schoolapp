  let selectedRegionId = null;

  function confirmDelete(element) {
    selectedRegionId = element.getAttribute("data-id");
    document.getElementById("deleteModal").classList.remove("hidden");
  }

  function hideModal() {
    selectedRegionId = null;
    document.getElementById("deleteModal").classList.add("hidden");
  }

  function submitDelete() {
    if (selectedRegionId) {
      document.getElementById("deleteRegionId").value = selectedRegionId;
      document.getElementById("deleteForm").submit();
    }
  }