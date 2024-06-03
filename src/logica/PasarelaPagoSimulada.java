package logica;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import interfaz.PasarelaPago;

public class PasarelaPagoSimulada implements PasarelaPago {

    @Override
    public boolean procesarPago(InformacionPago informacionPago) {
        String resultado;

        // Validación del número de tarjeta
        if (!isValidCardNumber(informacionPago.getNumeroTarjeta())) {
            resultado = "Pago fallido: número de tarjeta no válido";
        }
        // Validación de la fecha de expiración
        else if (!isValidExpiryDate(informacionPago.getFechaExpiracion())) {
            resultado = "Pago fallido: fecha de expiración no válida";
        }
        // Validación del CVV
        else if (!isValidCVV(informacionPago.getCvv())) {
            resultado = "Pago fallido: CVV no válido";
        }
        // Validación del monto
        else if (informacionPago.getMonto() <= 0) {
            resultado = "Pago fallido: monto no válido";
        } else {
            resultado = "Pago exitoso";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/resultado_pagos.txt", true))) {
            writer.write("Transacción ID: " + informacionPago.getIdTransaccion() + ", Resultado: " + resultado);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado.equals("Pago exitoso");
    }

    // Método para validar el número de tarjeta (sólo longitud de 16 dígitos y prefijos válidos)
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("^(4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$");
    }

    // Método para validar la fecha de expiración (formato MM/AA)
    private boolean isValidExpiryDate(String expiryDate) {
        return expiryDate.matches("^(0[1-9]|1[0-2])/[0-9]{2}$");
    }

    // Método para validar el CVV (3 dígitos)
    private boolean isValidCVV(String cvv) {
        return cvv.matches("^[0-9]{3}$");
    }
}

