let modalConfirmCallback = null;

function openModal({
    title = "Are you sure?",
    message = "This action cannot be undone.",
    confirmText = "Confirm",
    cancelText = "Cancel",
    onConfirm = null
}) {
    document.getElementById("modalTitle").innerText = title;
    document.getElementById("modalMessage").innerText = message;
    document.getElementById("modalConfirm").innerText = confirmText;
    document.getElementById("modalCancel").innerText = cancelText;
    if(confirmText=="Update"){
        document.getElementById("modalConfirm").classList.add("darkBtn");
    }
    modalConfirmCallback = onConfirm;

    document.getElementById("globalModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("globalModal").classList.add("hidden");
    document.getElementById("modalConfirm").classList.remove("darkBtn");
    modalConfirmCallback = null;
}

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("modalCancel")
        .addEventListener("click", closeModal);

    document.getElementById("modalConfirm")
        .addEventListener("click", () => {
            if (typeof modalConfirmCallback === "function") {
                modalConfirmCallback();
            }
            closeModal();
        });

});




// openModal({
//     title: "Delete Academic?",
//     message: "This academic record has students. Delete anyway?",
//     confirmText: "Delete",
//     onConfirm: () => deleteAcademic(academicId)
// });
// ⚠️ Warning popup
// openModal({
//     title: "Action not allowed",
//     message: "This academic record has students assigned.",
//     confirmText: "Ok",
//     onConfirm: null
// });
// ℹ️ Info popup
// openModal({
//     title: "Saved",
//     message: "Academic updated successfully.",
//     confirmText: "Close"
// });
