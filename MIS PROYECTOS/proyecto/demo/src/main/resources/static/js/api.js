api = (function () {
    var registrarUsuario = function (correo, nombre, contraseña, direccion, telefono) {
        var json = JSON.stringify({
            correo: correo,
            nombre: nombre,
            contraseña: contraseña,
            direccion: direccion,
            telefono: telefono
        });
        console.log("JSON a enviar:", json);
        $.ajax({
            url: 'http://localhost:8080/api/IniciarSesion',
            type: 'POST',
            data: json,
            contentType: "application/json",
            success: function (response) {
                console.log("Usuario registrado con éxito:", response);
                alert("Usuario registrado con éxito");



                // Redirige al usuario a la página de inicio de sesión
                window.location.href = "login.html";
            },
            error: function (xhr, status, error) {
                console.log("Error al registrar usuario:", error);
                alert("Error al registrar usuario");
            }
        });
    };

    var iniciarSesion = function (correo, contraseña) {
        var json = JSON.stringify({ correo: correo, contraseña: contraseña });

        $.ajax({
            url: 'http://localhost:8080/api/login',
            type: 'POST',
            data: json,
            contentType: "application/json",
            success: function (response) {
                console.log("Sesión iniciada con éxito:", response);
                alert("Sesión iniciada con éxito");

                // Guardar el correo del usuario en localStorage
                localStorage.setItem('correoUsuario', correo);

                // Redirigir al usuario a la página de productos
                window.location.href = "/product.html";
            },
            error: function (xhr, status, error) {
                console.log("Error al iniciar sesión:", error);
                alert("Error al iniciar sesión");
            }
        });
    };

    var obtenerProductos = function () {
        $.ajax({
            url: 'http://localhost:8080/api/productos', // Asegúrate de que la URL es correcta
            type: 'GET',
            success: function (productos) {
                console.log("Productos obtenidos:", productos);
                mostrarProductos(productos); // Llamar a la función para mostrar productos
            },
            error: function (xhr, status, error) {
                console.log("Error al obtener productos:", error);
                alert("Error al obtener productos");
            }
        });
    };



    function mostrarProductos(productos) {
        const contenedor = document.getElementById("productos-container");
        contenedor.innerHTML = ""; // Limpiar contenedor antes de agregar productos

        productos.forEach(producto => {
            const productoDiv = document.createElement("div");
            productoDiv.classList.add("product-card"); // Asigna la clase de estilo

            productoDiv.innerHTML = `
                <img src="${producto.imagen}" alt="${producto.nombre}" class="product-image">
                <div class="product-details">
                    <h2 class="product-name">${producto.nombre}</h2>
                    <p class="product-price">Precio: ${producto.precio}</p>
                    <input type="number" class="cantidad-input" value="1" min="1" max="10" id="cantidad-${producto.id}">
                    <button class="add-to-cart-btn" data-id="${producto.id}">Añadir al Carrito</button>
                </div>
            `;

            contenedor.appendChild(productoDiv);
        });

        // Añadir evento a cada botón de "Añadir al Carrito"
        const addToCartButtons = document.querySelectorAll(".add-to-cart-btn");
        addToCartButtons.forEach(button => {
            button.addEventListener("click", function() {
                const productoId = this.getAttribute("data-id");
                const cantidad = document.getElementById(`cantidad-${productoId}`).value;

                // Obtener el correo del usuario desde localStorage
                const correoUsuario = localStorage.getItem('correoUsuario');
                if (!correoUsuario) {
                    alert("Por favor, inicia sesión para agregar productos al carrito.");
                    return; // Salir si no hay correo del usuario
                }

                // Llamada a la función para agregar el producto al carrito
                api.agregarProductoAlCarrito(correoUsuario, productoId, cantidad);
            });
        });
    }

    var obtenerCarrito = function (correoUsuario) {
        $.ajax({
            url: 'http://localhost:8080/api/carrito/productos?correoUsuario=' + correoUsuario, // Cambia esto para que apunte al endpoint correcto
            type: 'GET',
            success: function (carritoProductos) {
                console.log("Carrito obtenido:", carritoProductos);
                mostrarCarrito(carritoProductos); // Llama a la función para mostrar el carrito
            },
            error: function (xhr, status, error) {
                console.log("Error al obtener carrito:", error);
                alert("Error al obtener carrito");
            }
        });
    };

    function mostrarCarrito(carritoProductos) {
        const carritoBody = document.getElementById('carrito-body');
        carritoBody.innerHTML = ""; // Limpiar el contenido anterior
        let totalPrice = 0;

        // Promesas para obtener todos los productos
        const productPromises = carritoProductos.map(item => {
            return obtenerProductoPorId(item.productoId).then(producto => {
                if (producto && producto.precio !== undefined) {
                    const precioTotal = Number(producto.precio) * item.cantidad; // Asegúrate de convertir el precio a número
                    const row = document.createElement('tr');

                    // Convierte la imagen en Base64 a una URL de datos
                    const imagenUrl = producto.imagen;

                    row.innerHTML = `
                        <td><img src="${imagenUrl}" alt="${producto.nombre}" style="width: 50px; height: auto;"></td>
                        <td>${producto.nombre}</td>
                        <td>${item.cantidad}</td>
                        <td>${precioTotal}</td>
                    `;
                    carritoBody.appendChild(row);
                    totalPrice += precioTotal; // Sumar al total
                } else {
                    console.error("Producto no válido:", item);
                }
            });
        });

        // Espera a que todas las promesas se resuelvan antes de actualizar el total
        Promise.all(productPromises).then(() => {
            document.getElementById('total-price').innerText = totalPrice; // Mostrar total
        });
    }

    // Función para obtener producto por ID
    function obtenerProductoPorId(productoId) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: 'http://localhost:8080/api/producto/' + productoId, // Cambia esto según tu API
                type: 'GET',
                success: function (producto) {
                    resolve(producto);
                },
                error: function (xhr, status, error) {
                    console.log("Error al obtener producto:", error);
                    resolve(null); // Resolver como null en caso de error para que la ejecución continúe
                }
            });
        });
    }


    var agregarProductoAlCarrito = function (correoUsuario, productoId, cantidad) {
        // Convertir productoId y cantidad a enteros
        var idProducto = parseInt(productoId, 10); // Asegúrate de que sea un entero
        var cantidadProducto = parseInt(cantidad, 10); // Asegúrate de que sea un entero

        // Verificar si la conversión fue exitosa
        if (isNaN(idProducto) || isNaN(cantidadProducto)) {
            alert("Por favor, asegúrate de que el ID del producto y la cantidad sean números válidos.");
            return; // Salir si la conversión falla
        }

        var json = JSON.stringify({
            correoUsuario: correoUsuario,
            productoId: idProducto, // Usar el ID convertido
            cantidad: cantidadProducto // Usar la cantidad convertida
        });

        console.log(json);

        $.ajax({
            url: 'http://localhost:8080/api/carrito/producto', // Asegúrate de que esta URL es correcta
            type: 'POST',
            data: json,
            contentType: "application/json",
            success: function (response) {
                console.log("Producto agregado al carrito con éxito:", response);
                alert("Producto agregado al carrito.");
            },
            error: function (xhr, status, error) {
                console.log("Error al agregar producto al carrito:", error);
                alert("Error al agregar producto al carrito.");
            }
        });
    };

    var crearFactura = function(carrito) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: 'http://localhost:8080/api/factura', // URL del controlador de factura
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(carrito), // Enviar carrito como JSON
                success: function(factura) {
                    resolve(factura);
                },
                error: function(xhr, status, error) {
                    console.error("Error al crear factura:", error);
                    reject(error);
                }
            });
        });
    }

    // Función para mostrar los detalles de la factura en el HTML
    var mostrarFactura = function(factura) {
        document.getElementById('nombre-usuario').innerText = factura.nombreUsuario;
        document.getElementById('direccion-usuario').innerText = factura.direccionUsuario;
        document.getElementById('fecha-creacion').innerText = factura.fechaCreacion;
        document.getElementById('total-pagar').innerText = `$${factura.totalAPagar}`;

        const productosFactura = document.getElementById('productos-factura');
        productosFactura.innerHTML = ''; // Limpiar tabla de productos

        factura.carrito.productos.forEach(producto => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${producto.productoId}</td>
                <td>${producto.cantidad}</td>
            `;
            productosFactura.appendChild(row);
        });
    }

    // Función para obtener el carrito del usuario
    var obtenerCarritoFactura = function (correoUsuario) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: 'http://localhost:8080/api/carrito/user?correoUsuario=' + correoUsuario, // Cambia esto para que apunte al endpoint correcto
                type: 'GET',
                success: function (carritoProductos) {
                    console.log("Carrito obtenido:", carritoProductos);
                    resolve(carritoProductos); // Resuelve la promesa con los productos del carrito
                },
                error: function (xhr, status, error) {
                    console.log("Error al obtener carrito:", error);
                    reject(error); // Rechaza la promesa en caso de error
                }
            });
        });
    };

    var facturacion = function(correoUsuario) {

        if (!correoUsuario) {
            alert("Por favor, inicia sesión para finalizar la compra.");
            return;
        }

        console.log("fasfkbjabsf");

        // Llamada a obtener el carrito del usuario antes de crear la factura
        obtenerCarritoFactura(correoUsuario)
            .then(carrito => {
                // Verifica que el carrito tenga productos
                if (!carrito || !carrito.productos || carrito.productos.length === 0) {
                    alert("El carrito está vacío. Agrega productos antes de finalizar la compra.");
                    return;
                }

                // Crear un objeto que represente el carrito en el formato esperado por la API
                const carritoParaFactura = {
                    carritoId: carrito.carritoId,
                    usuario: {
                        correo: carrito.usuario.correo,
                        nombre: carrito.usuario.nombre,
                        contraseña: carrito.usuario.contraseña,
                        direccion: carrito.usuario.direccion,
                        telefono: carrito.usuario.telefono
                    },
                    correo: carrito.usuario.correo,  // Campo 'correo' requerido en la factura
                    productos: carrito.productos,    // Lista de productos en el carrito
                    precioTotal: carrito.precioTotal // Precio total del carrito
                };

                console.log("Carrito para factura: ",carritoParaFactura);

                // Llamada a la API para crear la factura con el carrito
                crearFactura(carritoParaFactura)
                    .then(factura => {
                        mostrarFactura(factura);
                    })
                    .catch(error => console.error("No se pudo crear la factura", error));
            })
            .catch(error => console.error("Error al obtener el carrito del usuario:", error));
    };


    // Exportar el nuevo método
    return {
        registrarUsuario: registrarUsuario,
        iniciarSesion: iniciarSesion,
        obtenerProductos: obtenerProductos,
        obtenerCarrito: obtenerCarrito, // Exporta el método para obtener el carrito
        agregarProductoAlCarrito: agregarProductoAlCarrito,
        crearFactura: crearFactura,
        mostrarFactura: mostrarFactura,
        obtenerCarrito: obtenerCarrito,
        facturacion: facturacion
    };

})();

// Llama a obtenerProductos cuando la página se carga
document.addEventListener("DOMContentLoaded", function() {
    api.obtenerProductos();
});

document.getElementById('seguir-comprando-btn').addEventListener('click', () => {
    window.location.href = 'product.html'; // Redirige a la página de productos
});
