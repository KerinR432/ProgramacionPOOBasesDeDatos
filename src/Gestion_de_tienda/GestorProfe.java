package Gestion_de_tienda;

import java.sql.*;
import java.util.Scanner;

public class GestorProfe {
     static Scanner in = new Scanner(System.in);
     static Connection connection;
    public static void main(String[] args) throws SQLException {
        int op=0;
        conectarConBD();
        do {
            mostrarMenu();
            op=Integer.parseInt(in.nextLine());
                String sql="";
            switch (op){
                case 1:
                    sql="insert into stock values('";
                    System.out.println("Introduce el nombre, precio y cantidad...");
                    sql+=in.nextLine()+"',";
                    sql+=in.nextLine()+",";
                    sql+=in.nextLine()+")";
                    System.out.println(sql);
                    if(!existeProductos(sql)) {
                        ejecutarIntruccionesSQL(sql);
                    }
                    break;
                    case 2:
                        System.out.println("Introduce el nombre del producto a buscar...");
                        String nombre = in.nextLine();
                        String datoProd=buscarProducto(nombre);
                        if(buscarProducto(nombre).equals("")){
                            System.out.println("Producto no encontrado");
                        }else{
                            System.out.println(datoProd);
                        }
                        break;
                case 3:
                    sql = "update stock set precio=";
                    System.out.println("Introduce Nombre del producto");
                    String nombre2 = in.nextLine();
                    System.out.println("Introduce nuevo precio y cantidad...");
                    sql+=in.nextLine()+",cantidad="+in.nextLine()+"where nombre,"
                    +nombre2+",";
                    System.out.println(sql);
                    if (existeProductos(nombre2)) {
                        ejecutarIntruccionesSQL(sql);
                    }else{
                        System.out.println("El producto espeficiado no existe");
                    }
                case 4:
                case 5:
                    System.out.println("Has salido weon!!!");
            }

            //2. Buscar
            //4. Borra. Precondicion: Nombre no exista
        }while (op!=5);
        //5. Salir --> cerrar la conexión
    }
    public static void conectarConBD() throws SQLException{
        try {
            String uri = "jdbc:mysql://localhost:3306/pr_ejemplo";
            String usuario = "root";
            String password = "Root123*";
            connection = DriverManager.getConnection(uri, usuario, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void mostrarMenu(){
        System.out.println("1. Dar de alta.\n" + "2. Buscar\n" +
                "3 Modificar. Precondicion:\n" + "4. Borra.\n" +
                "5. Salir");
    }
    public static void ejecutarIntruccionesSQL(String sql){
        try {
            Statement senteciaSQL = connection.createStatement();
            senteciaSQL.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static String buscarProducto(String nombre){
        Statement setenciaSQL = null;
        ResultSet resultSet = null;
        String datosProductos="";
        try {
            setenciaSQL = connection.createStatement();
            String sql="select * from stock "+" where nombre='"+
                    nombre+"'";
            resultSet= setenciaSQL.executeQuery(sql);
            if (resultSet.next()){
                datosProductos=resultSet.getString("nombre")+","
                        +resultSet.getDouble("Precio")+","
                        +resultSet.getInt("cantidad");
            }
            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return datosProductos;
    }
    public static boolean existeProductos(String nombre){
        Statement setenciaSQL = null;
        ResultSet resultSet = null;
        try {
            setenciaSQL = connection.createStatement();
            String sql="select * from stock "+" where nombre='"+
                    nombre+"'";
            resultSet= setenciaSQL.executeQuery(sql);
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
}
