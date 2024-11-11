document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const verCarritoBtn = document.getElementById('ver-carrito-btn'); // Obtener el botón
    const verFacturaBtn = document.getElementById('ver-factura-btn');

    const correoUsuario = localStorage.getItem('correoUsuario'); // Cambia esto según tu implementación

    if (loginForm) {
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Evita el comportamiento predeterminado del formulario

            const correo = document.getElementById('email').value;
            const contraseña = document.getElementById('password').value;

            console.log('Iniciar sesión con:', correo, contraseña);

            // Llamada a la API para iniciar sesión
            api.iniciarSesion(correo, contraseña);
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Evita el comportamiento predeterminado del formulario

            const correo = document.getElementById('email').value;
            const nombre = document.getElementById('name').value;
            const contraseña = document.getElementById('password').value;
            const direccion = document.getElementById('address').value;
            const telefono = document.getElementById('phone').value;

            console.log('Registrar usuario:', correo, nombre, contraseña, direccion, telefono);

            // Llamada a la API para registrar un nuevo usuario
            api.registrarUsuario(correo, nombre, contraseña, direccion, telefono);
        });
    }
    // Funcionalidad del botón "Ver Carrito"
    if (verCarritoBtn) {
        verCarritoBtn.addEventListener('click', function() {
            if (correoUsuario) {
                // Redirigir al usuario a la página del carrito
                window.location.href = "carrito.html"; // Asegúrate de que esta URL es correcta
            } else {
                alert("Debes iniciar sesión para ver tu carrito.");
            }
        });
    }

    // Funcionalidad del botón "Ver Carrito"
        if (verFacturaBtn) {
            verFacturaBtn.addEventListener('click', function() {
                if (correoUsuario) {
                    console.log("se va a factura");
                    api.facturacion(correoUsuario);
                    // Redirigir al usuario a la página del carrito
                    window.location.href = "factura.html"; // Asegúrate de que esta URL es correcta
                } else {
                    alert("Debes iniciar sesión para ver tu carrito.");
                }
            });
        }

    // Llama a obtenerCarrito cuando la página del carrito se carga
    if (document.getElementById('carrito-body')) { // Asegúrate de que estás en la página del carrito
        if (correoUsuario) { // Verificar que el correo del usuario existe
            api.obtenerCarrito(correoUsuario);
        } else {
            alert("No has iniciado sesión. Redirigiendo a la página de inicio de sesión.");
            window.location.href = "login.html"; // Redirigir si no hay correo
        }
    }
});
