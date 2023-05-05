
package claseepersona;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

public class ClaseePersona {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String nombreAlumno;
        String apellidoAlumno;
        String fechaAlumno;

        String fechaNacimiento;

        Persona persona;

        int numPersConFecha;
        int numPersSinFecha;

        String cadenaNuevaFecha;
        boolean fechaCorrecta;

        numPersConFecha = sc.nextInt();
        numPersSinFecha = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numPersConFecha; i++) {
            nombreAlumno = sc.nextLine();
            apellidoAlumno = sc.nextLine();
            fechaAlumno = sc.nextLine();

            try {
                persona = new Persona(nombreAlumno, apellidoAlumno, fechaAlumno);
                fechaNacimiento = persona.getFechaNacimiento();
                System.out.println("Procesado: " + persona.getNombre() + " " + persona.getApellidos()
                        + ", nacido el " + fechaNacimiento.substring(0, 2) + " del "
                        + fechaNacimiento.substring(3, 5) + " de " + fechaNacimiento.substring(6));
            } catch (IllegalArgumentException ex) {
                System.out.println("ERROR. Procesando siguiente persona");
            }

        }

        for (int i = 1; i <= numPersSinFecha; i++) {
            nombreAlumno = sc.nextLine();
            apellidoAlumno = sc.nextLine();

            try {
                persona = new Persona(nombreAlumno, apellidoAlumno);
                fechaNacimiento = persona.getFechaNacimiento();
                System.out.println("Procesado: " + persona.getNombre() + " "
                        + persona.getApellidos() + ", nacido el " + fechaNacimiento);

                if (i == numPersSinFecha) {
                    fechaCorrecta = false;
                    cadenaNuevaFecha = sc.nextLine();
                    while (!fechaCorrecta) {
                        try {
                            persona.setFechaNacimiento(cadenaNuevaFecha);
                            fechaNacimiento = persona.getFechaNacimiento();
                            fechaCorrecta = true;

                            System.out.println("Procesado: " + persona.getNombre() + " " + persona.getApellidos()
                                    + ", nacido el " + fechaNacimiento.substring(0, 2) + " del "
                                    + fechaNacimiento.substring(3, 5) + " de " + fechaNacimiento.substring(6));
                        } catch (IllegalArgumentException ex1) {
                            System.out.println("Fecha Incorrecta");
                            cadenaNuevaFecha = sc.nextLine();
                        }
                    }
                }

            } catch (IllegalArgumentException ex) {
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

    public Persona(String nombre, String apellidos, String fechaNacimiento) throws IllegalArgumentException {

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

    public void setFechaNacimiento(String fechaNacimiento) throws IllegalArgumentException {
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

}


