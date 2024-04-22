package consola;


import logica.inventario;
import logica.pieza;
import logica.pago;
import logica.cajero;
import java.util.Scanner;
import java.io.*;

public class pruebasadmin {
    private inventario inventory;
    private cajero cashier;

    public pruebasadmin() {
        this.inventory = new inventario(); 
        this.cashier = new cajero("Default Admin", "admin@example.com"); 
    }

    public static void main(String[] args) {
    	pruebasadmin console = new pruebasadmin();
        console.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("\n--- Consola de administración de la galería ---");
            System.out.println("1. Mostrar inventario");
            System.out.println("2. Confirmar pago");
            System.out.println("3. Actualizar estado de pieza");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    displayInventory();
                    break;
                case "2":
                    confirmPayment(scanner);
                    break;
                case "3":
                    updatePieceStatus(scanner);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Elección no válida, inténtelo de nuevo.");
            }
        } while (!input.equals("0"));
        scanner.close();
    }

    private void displayInventory() {
        System.out.println("\n--- Lista de inventario ---");
        for (pieza piece : inventory.getPiezasDisponibles()) {
            System.out.println(piece);
        }
    }

    private void confirmPayment(Scanner scanner) {
        System.out.print("Ingrese el ID de pago para confirmar: ");
        String paymentId = scanner.nextLine();
     
        pago payment = new pago(paymentId, 1000, "Mock Method", "Test Details", false);
        boolean result = cashier.confirmarPago(payment);
        System.out.println("Pago " + paymentId + " confirmado: " + result);
    }

    private void updatePieceStatus(Scanner scanner) {
        System.out.print("Ingrese el ID de la pieza para alternar el estado de la exposición: ");
        String pieceId = scanner.nextLine();
        pieza piece = inventario.buscarPiezaPorId(pieceId);
        if (piece != null) {
            piece.cambiarEstadoExhibicion(!piece.isEstaEnExhibicion());
            System.out.println("Pieza " + pieceId + " estado de la exposición actualizado.");
        } else {
            System.out.println("Pieza no encontrada.");
        }
    }
}

