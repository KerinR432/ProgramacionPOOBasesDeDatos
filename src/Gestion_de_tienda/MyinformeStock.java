package Gestion_de_tienda;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class MyinformeStock {
    static Scanner in = new Scanner(System.in);
    static String uri = "jdbc:mysql://localhost:3306/pr_ejemplo";
    static String usuario = "root";
    static String password = "Root123*";
    public static void main(String[] args) {
        char op;
        do {
            menu();
            op=in.nextLine().charAt(0);
            switch (op){
                case 'S':
                    String texto = buscarEnLaBasesDeDatos();
                    escribirInventario(texto);
                    mostrarInventario();
                    System.out.println("Se ha creado tu Inventario revisa tus archivos");
                    break;

                case 'N':
                    System.out.println("Gracias por utilizarnos, adios!!!");
                    break;
                default:
                    System.out.println("Lo siento parece que no hemos sido claros, solo S/N");
                    break;
            }
        }while(op!='N');
    }
    public static void menu(){
        System.out.println("Bienvenido a la App de la empresa");
        System.out.println("¿Quieres que generemos un informe con los productos\nque se estan agotando?");
        System.out.println("S/N");
    }

    public static String buscarEnLaBasesDeDatos(){
        String datos="";
        try {
            Connection miConexion = DriverManager.getConnection(uri, usuario, password);
            //Paso 2: Crear una sentencia SQL
            Statement setenciaSQL = miConexion.createStatement();
            //paso 3: ejecutamos la consulta (CAMBIA)
            String sql = "select * from stock where cantidad <= 10;";
            ResultSet resultSet = setenciaSQL.executeQuery(sql);
            int idx=0;
            //paso4: Recorremos el Resulset
            while (resultSet.next()){
                String n;
                int c;
                c=resultSet.getInt("cantidad");
                n=resultSet.getString("nombre");
                datos += "Producto:"+idx+"\n "+"Nombre :"+n+"|"+"|"+"Stock: "+c+"\n";
                idx++;

            }
                return datos;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void escribirInventario(String datos){
        FileWriter salida;
        try {
            //construyo el canal
            salida = new FileWriter("Inventario.txt");
            salida.write(datos);

            //cerrar el flujo o canal
            salida.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void mostrarInventario(){
        FileReader entrada;
        try {
            int car=0;
            entrada = new FileReader("Inventario.txt");
            while (car!=-1){
                car= entrada.read();
            }
            entrada.close();
        } catch (FileNotFoundException e) { //<-- este es él más concretó
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) { //<-- este es él más genera
            System.out.println(e.getMessage());
        }
    }
}

