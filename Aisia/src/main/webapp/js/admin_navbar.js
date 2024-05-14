document.addEventListener("DOMContentLoaded", function() {
    const adminBtn = document.getElementById("adminBtn");
    const userBtn = document.getElementById("userBtn");
    const navbarAdmin = document.getElementById("navbar-admin");
    const navbarUsuario = document.getElementById("navbar-usuario");

    adminBtn.addEventListener("click", function() {
        navbarAdmin.style.display = navbarAdmin.style.display === "block" ? "none" : "block";
        navbarUsuario.style.display = "none";
    });

    userBtn.addEventListener("click", function() {
        navbarUsuario.style.display = navbarUsuario.style.display === "block" ? "none" : "block";
        navbarAdmin.style.display = "none";
    });
});
