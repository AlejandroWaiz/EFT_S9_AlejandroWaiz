/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.eft_s9_alejandrowaiz;

import java.util.ArrayList;
import java.util.Scanner;

class Entrada {
    // Variables de instancia
    int id;
    int numero;
    String ubicacion;
    double precio;
    String tipoDescuento;
    double descuento;
    double precioFinal;

    private static int contadorEntradas = 0;

    // Constructor
    public Entrada(int numero, String ubicacion, double precio, String tipo, double descuento) {
        this.id = ++contadorEntradas;
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.tipoDescuento = tipo;
        this.descuento = descuento;
        calcularPrecioFinal();
    }

    // Método para calcular el precio final aplicando el descuento
    private void calcularPrecioFinal() {
        this.precioFinal = precio - (precio * descuento / 100);
    }

    // Método para mostrar la información de la entrada
    public void mostrarInformacion() {       
        System.out.println("--------------------------------");
        System.out.println("-----------TEATRO MORO----------");
        System.out.println("--------------------------------");
        System.out.println("ID entrada: " + id);
        System.out.println("|Número de asiento: " + numero);
        System.out.println("|Sector: " + ubicacion);
        System.out.println("|Precio: $" + precio);
        System.out.println("|Tipo descuento: " + tipoDescuento);
        System.out.println("|Porcentaje descuento: " + ((int)descuento) + "%");
        System.out.println("|Precio Final: $" + precioFinal);
    }
}

public class EFT_S9_AlejandroWaiz {
    
    static ArrayList<Entrada> entradas = new ArrayList<>();
    static boolean[] asientosOcupados = new boolean[60];
    
    public static void main(String[] args) {
        
    Scanner scanner = new Scanner(System.in);
    int opcion;
    
            System.out.println("!Bienvenid@ al teatro Moro!\n");

        do {
            System.out.println("Menú:\n");
            System.out.println("1. Comprar una entrada");
            System.out.println("2. Mostrar entradas compradas");
            System.out.println("3. Salir");
            System.out.print("\nSelecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    comprarEntrada(scanner);
                    break;
                case 2:
                    mostrarEntradasCompradas();
                    break;
                case 3:
                    System.out.println("Tu boleta final sería:\n\n");
                    salir();
                    System.out.println("\nQue tengas un buen dia!");
                    scanner.close();
                    return;    
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }
    
    public static void comprarEntrada(Scanner scanner) {
        System.out.println("\nElige una ubicación:");
        System.out.println("1. VIP (1-10) - Precio: $100");
        System.out.println("2. Platea (11-30) - Precio: $50");
        System.out.println("3. General (31-60) - Precio: $30");
        System.out.println("\n4. Volver al menú principal");

        int opcionUbicacion = scanner.nextInt();
        if (opcionUbicacion == 4) return;

        String ubicacion = "";
        double precio = 0;
        int inicio = 0, fin = 0;

        switch (opcionUbicacion) {
            case 1:
                ubicacion = "VIP";
                precio = 100;
                inicio = 1;
                fin = 10;
                break;
            case 2:
                ubicacion = "Platea";
                precio = 50;
                inicio = 11;
                fin = 30;
                break;
            case 3:
                ubicacion = "General";
                precio = 30;
                inicio = 31;
                fin = 60;
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        // Mostrar los asientos disponibles en la ubicación seleccionada
        System.out.println("\nAsientos disponibles: ");
        for (int i = inicio; i <= fin; i++) {
            if (!asientosOcupados[i - 1]) {
                System.out.print(i + " ");
            }
        }
        
        System.out.println();

        int numeroAsiento = scanner.nextInt();
        if (numeroAsiento < inicio || numeroAsiento > fin || asientosOcupados[numeroAsiento - 1]) {
            System.out.println("Número de asiento no válido o ya está ocupado.");
            return;
        }

        boolean descuentoValido = false;
        String tipo = "";
        double descuento = 0;
        double edad = 0;

        do {

             // Solicitar el tipo de entrada
        System.out.println("\n¿Deseas optar a alguno de nuestros descuentos? Solo puede elegir uno");
        System.out.println("1. Público General (sin descuento)");
        System.out.println("2. Niños (Hasta 10 años) (10% descuento)");
        System.out.println("3. Estudiantes (11 - 27 años) (15% descuento)");
        System.out.println("4. Mujeres (20% descuento)");
        System.out.println("5. Tercera Edad (25% descuento)");
        System.out.println("\n4. Volver al menú principal");

        int opcionTipo = scanner.nextInt();

        if (opcionTipo == 2 || opcionTipo == 3 || opcionTipo == 5) {
            System.out.println("\nPor favor ingresa tu edad:"); 
            edad = scanner.nextInt();
        }

        descuentoValido = validarEdad(opcionTipo, edad);

        if (!descuentoValido){
            System.out.println("Tu opción o tu edad son invalidas para el tipo de descuento, por favor vuelve a intentarlo\n");
            continue;
        }

        switch (opcionTipo) {
            case 1:
                tipo = "Público General";
                descuento = 0;
                break;
            case 2:
                tipo = "Niños";
                descuento = 10;
                break;
            case 3:
                tipo = "Estudiante";
                descuento = 15;
                break;
            case 4:
                tipo = "Mujeres";
                descuento = 20;
                break;
            case 5:
                tipo = "Tercera Edad";
                descuento = 25;
                break;
        }

        }while(!descuentoValido);

        asientosOcupados[numeroAsiento - 1] = true;
        Entrada entrada = new Entrada(numeroAsiento, ubicacion, precio, tipo, descuento);
        entradas.add(entrada);
        entrada.mostrarInformacion();
    }

    public static boolean validarEdad(int opcion, double edad){

        switch (opcion) {
            case 1:
                return true;

            case 2:
                if (edad >= 0 && edad <= 10) {
                    return true;
                } 
                return false;

            case 3:
            if (edad >= 11 && edad <= 27) {
                return true;
            } 
                return false;

            case 4:
                return true;

            case 5:
            if (edad >= 60) {
                return true;
            } 
                return false;
            default:
                System.out.println("Por favor ingresa una opción válida.");
                return false;
        }


    }

    public static void mostrarEntradasCompradas() {

        if (entradas.isEmpty()){

            System.out.println("No tienes entradas pagadas");

        } else {

            for (Entrada entrada : entradas) {
                entrada.mostrarInformacion();
            }

        }
        
    }
    
        public static void salir() {
            
        double total = 0;
        double totalConDescuentos = 0;

        if (entradas.isEmpty()){

            System.out.println("No tienes entradas pagadas");

        } else {

            for (Entrada entrada : entradas) {
                total = total + entrada.precio;
                totalConDescuentos = totalConDescuentos + entrada.precioFinal;
                entrada.mostrarInformacion();
        }
          
        System.out.println("--------------------------------");
        System.out.println("-----------TEATRO MORO----------");
        System.out.println("--------------------------------");
        System.out.println("Total sin descuentos: "+ total); 
        System.out.println("Total con descuentos: "+ totalConDescuentos);
        
        double ahorrado = total - totalConDescuentos;   
        System.out.println("Ahorrado: "+ ahorrado); 
        System.out.println("--------------------------------");
        System.out.println("---!Que disfrutes la función!---");
        System.out.println("--------------------------------");

        }
        
    }
    
}
