function togglePassword(inputId, iconWrapper) {
  const input = document.getElementById(inputId);
  const icon = iconWrapper.querySelector("img");

  if (input.type === "password") {
    input.type = "text";
    icon.src = "/assets/icons/eye-off.svg"; 
  } else {
    input.type = "password";
    icon.src = "/assets/icons/eye.svg"; 
  }
}
