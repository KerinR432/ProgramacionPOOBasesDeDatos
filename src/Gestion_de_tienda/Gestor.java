package Gestion_de_tienda;

import java.sql.*;
import java.util.Scanner;

public class Gestor {
    static Scanner in = new Scanner(System.in);
    static String uri = "jdbc:mysql://localhost:3306/pr_ejemplo";
    static String usuario = "root";
    static String password = "Root123*";
    public static void main(String[] args) {

        String precio = "", cantidad = "",nombreP = "",buscar="";
        int op = 0;
        do {
            menu();
            op = in.nextInt();
            in.nextLine();
            switch (op) {
                case 1:
                    System.out.println("Introduce un nombre...");
                    nombreP = in.nextLine();
                    try {
                        if (duplicado(nombreP)) {
                            System.out.println("Introduce Precio...");
                            precio = in.nextLine();
                            System.out.println("Introduce Stock...");
                            cantidad = in.nextLine();
                            darDeAlta(nombreP, precio, cantidad);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Error, ya existe producto");
                    break;
                case 2:
                    System.out.println("¿Que producto buscas? introduce el nombre");
                    buscar=in.nextLine();
                    String texto=buscarProducto(buscar);
                    System.out.println(texto);
                    break;
                case 3:
                    System.out.println("Introduce nombre producto: ");
                    nombreP = in.nextLine();
                    try {
                        if (!duplicado(nombreP)) {
                            System.out.println("Introduce nuevo precio: ");
                            precio = in.nextLine();
                            System.out.println("Introduce nuevo cantidad: ");
                            cantidad = in.nextLine();
                            try {
                                modificarProducto(precio, nombreP, cantidad);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Error, no existe producto");
                    break;
                case 4:
                    System.out.println("Gracias por usarnos, hasta la proxima!!!!");
                    break;
                default:
                    System.out.println("Error, sigue los que se te indica.");
                    break;
            }
        } while (op != 4);
    }

    //menu
    public static void menu() {
        System.out.println("1) Dar de alta un producto");
        System.out.println("2) Buscar un producto por su nombre");
        System.out.println("3) Modificar el stock y precio de un producto dado");
        System.out.println("4) Salir");
    }
    public static void darDeAlta(String nombre,String precio,String cantidad){
        try {
            Connection miConexion = DriverManager.getConnection(uri, usuario, password);
            Statement senteciaSQL = miConexion.createStatement();
            String sql = "insert into stock (nombre,precio,cantidad) values "+"('"+nombre+"',"+ precio+ "," + cantidad+");";
            senteciaSQL.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static String buscarProducto(String buscar){
        try {
            Connection miConexion = DriverManager.getConnection(uri, usuario, password);
            //Paso 2: Crear una sentencia SQL
            Statement setenciaSQL = miConexion.createStatement();
            //paso 3: ejecutamos la consulta (CAMBIA)
            String sql = "select * from stock;";
            ResultSet resultSet = setenciaSQL.executeQuery(sql);
            //paso4: Recorremos el Resulset
            while (resultSet.next()){
                String n="",p="",c="";
                n=resultSet.getString("nombre");
                if (n.equalsIgnoreCase(buscar)){
                    p=resultSet.getString("precio");
                    c=resultSet.getString("cantidad");
                    String texto = "Producto: "+n+"|"+"Precio: "+p+"|"+"Stock: "+c;
                    return texto;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void modificarProducto(String nPrecio,String nNombre,String nCantidad) throws SQLException {
            try {
                Connection miConexion = DriverManager.getConnection(uri, usuario, password);
                Statement senteciaSQL = miConexion.createStatement();
                String sql = "update stock set precio = " + nPrecio + ", cantidad =" + nCantidad + " where nombre ='" + nNombre + "'";
                senteciaSQL.executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }
    public static boolean duplicado(String nombre) throws SQLException {
        Connection miConexion = DriverManager.getConnection(uri, usuario, password);
        Statement setenciaSQL = miConexion.createStatement();
        String sql = "select nombre from stock;";
        ResultSet resultSet = setenciaSQL.executeQuery(sql);
        String n = "";
        while (resultSet.next()){
            n=resultSet.getString("nombre");
            if (nombre.equalsIgnoreCase(n)){
                return false;
            }
        }
        return true;
    }
}
