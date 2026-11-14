
package com.sgoi.ui;

import com.sgoi.exception.DataAccessException;
import com.sgoi.model.Cliente;
import com.sgoi.service.ClienteService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClienteMenu {

    private final Scanner scanner;
    private final ClienteService clienteService;

    public ClienteMenu() {
        this.scanner = new Scanner(System.in);
        this.clienteService = new ClienteService();
    }

    // Iniciar el menú principal
    public void iniciar() {

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarCliente();
                    case 2 -> listarClientes();
                    case 3 -> buscarCliente();
                    case 4 -> actualizarCliente();
                    case 5 -> eliminarCliente();
                    case 0 -> System.out.println(
                            "\nAplicación finalizada."
                    );
                    default -> System.out.println(
                            "\nOpción no válida. Intente nuevamente."
                    );
                }

            } catch (IllegalArgumentException e) {
                System.out.println("\nValidación: " + e.getMessage());

            } catch (DataAccessException e) {
                System.out.println("\nError de acceso a datos: "
                        + e.getMessage());

            } catch (Exception e) {
                System.out.println(
                        "\nSe presentó un error inesperado."
                );
            }

            if (opcion != 0) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }

        } while (opcion != 0);
    }

    // Mostrar las opciones disponibles
    private void mostrarMenu() {

        System.out.println("\n==================================");
        System.out.println(" SISTEMA SGOI - GESTIÓN DE CLIENTES");
        System.out.println("==================================");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Listar clientes");
        System.out.println("3. Buscar cliente por ID");
        System.out.println("4. Actualizar cliente");
        System.out.println("5. Eliminar cliente");
        System.out.println("0. Salir");
        System.out.println("==================================");
    }

    // CREATE: registrar un cliente
    private void registrarCliente() {

        System.out.println("\n--- REGISTRAR CLIENTE ---");

        String nombre = leerTexto("Nombre: ");
        String documento = leerTexto("Documento: ");
        String telefono = leerTextoOpcional("Teléfono: ");
        String correo = leerTextoOpcional("Correo: ");
        String direccion = leerTextoOpcional("Dirección: ");

        Cliente cliente = new Cliente(
                nombre,
                documento,
                telefono,
                correo,
                direccion
        );

        Cliente registrado = clienteService.registrar(cliente);

        System.out.println("\nCliente registrado correctamente.");
        System.out.println("ID asignado: " + registrado.getId());
    }

    // READ: listar todos los clientes
    private void listarClientes() {

        System.out.println("\n--- LISTADO DE CLIENTES ---");

        List<Cliente> clientes = clienteService.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : clientes) {
            mostrarDatosCliente(cliente);
            System.out.println("----------------------------------");
        }

        System.out.println("Total de clientes: " + clientes.size());
    }

    // READ: buscar un cliente por ID
    private void buscarCliente() {

        System.out.println("\n--- BUSCAR CLIENTE ---");

        Long id = leerLong("Ingrese el ID del cliente: ");

        Optional<Cliente> resultado = clienteService.buscarPorId(id);

        if (resultado.isPresent()) {
            System.out.println("\nCliente encontrado:");
            mostrarDatosCliente(resultado.get());
        } else {
            System.out.println("\nNo existe un cliente con ese ID.");
        }
    }

    // UPDATE: actualizar un cliente
    private void actualizarCliente() {

        System.out.println("\n--- ACTUALIZAR CLIENTE ---");

        Long id = leerLong("Ingrese el ID del cliente: ");

        Optional<Cliente> resultado = clienteService.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        Cliente cliente = resultado.get();

        System.out.println("\nDatos actuales:");
        mostrarDatosCliente(cliente);

        System.out.println("\nIngrese los nuevos datos:");

        cliente.setNombre(leerTexto("Nombre: "));
        cliente.setDocumento(leerTexto("Documento: "));
        cliente.setTelefono(leerTextoOpcional("Teléfono: "));
        cliente.setCorreo(leerTextoOpcional("Correo: "));
        cliente.setDireccion(leerTextoOpcional("Dirección: "));

        boolean actualizado = clienteService.actualizar(cliente);

        if (actualizado) {
            System.out.println("\nCliente actualizado correctamente.");
        } else {
            System.out.println("\nNo fue posible actualizar el cliente.");
        }
    }

    // DELETE: eliminar un cliente
    private void eliminarCliente() {

        System.out.println("\n--- ELIMINAR CLIENTE ---");

        Long id = leerLong("Ingrese el ID del cliente: ");

        Optional<Cliente> resultado = clienteService.buscarPorId(id);

        if (resultado.isEmpty()) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }

        System.out.println("\nCliente seleccionado:");
        mostrarDatosCliente(resultado.get());

        System.out.print("\n¿Confirma la eliminación? (S/N): ");
        String confirmacion = scanner.nextLine().trim();

        if (confirmacion.equalsIgnoreCase("S")) {

            boolean eliminado = clienteService.eliminar(id);

            if (eliminado) {
                System.out.println("\nCliente eliminado correctamente.");
            } else {
                System.out.println("\nNo fue posible eliminar el cliente.");
            }

        } else {
            System.out.println("\nOperación cancelada.");
        }
    }

    // Mostrar la información de un cliente
    private void mostrarDatosCliente(Cliente cliente) {

        System.out.println("ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Documento: " + cliente.getDocumento());
        System.out.println("Teléfono: " + cliente.getTelefono());
        System.out.println("Correo: " + cliente.getCorreo());
        System.out.println("Dirección: " + cliente.getDireccion());
        System.out.println("Fecha de registro: " + cliente.getFechaRegistro());
    }

    // Leer un texto obligatorio
    private String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // Leer un texto opcional
    private String leerTextoOpcional(String mensaje) {

        System.out.print(mensaje);
        String valor = scanner.nextLine().trim();

        return valor.isEmpty() ? null : valor;
    }

    // Leer un número entero
    private int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }

    // Leer un ID numérico
    private Long leerLong(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un ID numérico válido.");
            }
        }
    }
}
