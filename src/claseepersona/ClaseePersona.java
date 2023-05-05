package claseepersona;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class ClaseePersona {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String nombre;
        String apellidos;
        String fechaNacimiento;

        int anyosPersona;
        int cantPersonas;
        Persona persona;

        cantPersonas = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= cantPersonas; i++) {
            nombre = sc.nextLine();
            apellidos = sc.nextLine();
            fechaNacimiento = sc.nextLine();

            try {
                persona = new Persona(nombre, apellidos, fechaNacimiento);
                anyosPersona = persona.getEdad(fechaNacimiento);

                if (anyosPersona == -1) {
                    System.out.println(persona.getNombre() + " " + persona.getApellidos()
                            + " aun no ha nacido a dia de hoy");
                } else {

                    System.out.println(persona.getNombre() + " " + persona.getApellidos()
                            + " tiene " + anyosPersona + " anyos a dia de hoy");
                }
            } catch (IllegalArgumentException ex1) {
                System.out.println("ERROR. Procesando siguiente persona");
            }
        }
    }
}

class Persona {

    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;

    public Persona(String nombre, String apellidos) {
        if ("".equals(nombre) || "".equals(apellidos)) {
            throw new IllegalArgumentException();

        } else {
            this.nombre = nombre;
            this.apellidos = apellidos;
        }
    }

    public Persona(String nombre, String apellidos, String fechaNacimiento)throws IllegalArgumentException {

        if ("".equals(nombre) || "".equals(apellidos)) {
            throw new IllegalArgumentException();

        } else {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.fechaNacimiento = generarFecha(fechaNacimiento);
        }

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNacimiento(char separador) {
        String fecha = null;

        if (separador != '-' && separador != '/') {
            throw new IllegalArgumentException();

        } else {
            if (this.fechaNacimiento != null) {
                fecha = String.format("%02d%c%02d%c%04d", this.fechaNacimiento.getDayOfMonth(), separador,
                        this.fechaNacimiento.getMonthValue(), separador, this.fechaNacimiento.getYear());
            }
            return fecha;
        }

    }

    public String getFechaNacimiento() {
        return getFechaNacimiento('-');
    }

    public void setFechaNacimiento(String fechaNacimiento)throws IllegalArgumentException {
        this.fechaNacimiento = generarFecha(fechaNacimiento);
    }

    private LocalDate generarFecha(String fecha) {

        int dia;
        int mes;
        int anyo;

        if (!fecha.matches("[0-9]{2}[-][0-9]{2}[-][0-9]{4}")
                && !fecha.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")) {
            throw new IllegalArgumentException();
        } else {
            try {
                dia = Integer.parseInt(fecha.subSequence(0, 2).toString());
                mes = Integer.parseInt(fecha.subSequence(3, 5).toString());
                anyo = Integer.parseInt(fecha.subSequence(6, fecha.length()).toString());

                return LocalDate.of(anyo, mes, dia);
            } catch (NumberFormatException ex1) {
                throw new IllegalArgumentException();
            } catch (DateTimeException ex2) {
                throw new IllegalArgumentException();
            }

        }

    }

    public int getEdadEnFecha(String cadenaFecha)throws IllegalArgumentException {

        LocalDate fechaComparacion = generarFecha(cadenaFecha);
        LocalDate fechaNacimiento = this.fechaNacimiento;

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException();
        }

        int edadFecha = fechaComparacion.getYear() - fechaNacimiento.getYear();

        if (fechaComparacion.getMonthValue() < fechaNacimiento.getMonthValue()
                || (fechaComparacion.getMonthValue() == fechaNacimiento.getMonthValue()
                && fechaComparacion.getDayOfMonth() < fechaNacimiento.getDayOfMonth())) {
            edadFecha--;
        }
        if (fechaNacimiento.isBefore(fechaComparacion)) {
            edadFecha = -1;
        }

        return edadFecha;
    }

    public int getEdad(String cadenaFecha)throws IllegalArgumentException {
        int edad = 0;
        LocalDate fechaNacimiento = generarFecha(cadenaFecha);
        Period periodo;

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException();
        }

        if (this.fechaNacimiento.isAfter(LocalDate.now())) {
            edad = -1;
        } else {
                periodo = Period.between(fechaNacimiento, LocalDate.now());
                edad = periodo.getYears();
            
        }
        return edad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.nombre);
        hash = 11 * hash + Objects.hashCode(this.apellidos);
        hash = 11 * hash + Objects.hashCode(this.fechaNacimiento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
 
    
}

