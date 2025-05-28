package UD11;

import java.sql.*;

public class AccesoABasesDeDatosPersona_Consultar {
    //paso 1: crear conexión entre la app y mysql
    public static void main(String[] args) {

        String uri = "jdbc:mysql://localhost:3306/pr_ejemplo";
        String usuario = "root";
        String password = "Root123*";

        // Paso 1: Crear conexion entre estas app y MySQL
        try {
            Connection miConexion = DriverManager.getConnection(uri, usuario, password);
            //Paso 2: Crear una sentencia SQL
            Statement setenciaSQL = miConexion.createStatement();
            //paso 3: ejecutamos la consulta (CAMBIA)
            String sql = "select * from persona;";
            ResultSet resultSet = setenciaSQL.executeQuery(sql);
            //paso4: Recorremos el Resulset

            while (resultSet.next()){
                String dni = "";
                String nombre="";
                int edad = 0;

                dni = resultSet.getString("dni");
                nombre = resultSet.getString("nombre");
                edad = resultSet.getInt("edad");
                System.out.println("dni: "+dni+" nombre: "+nombre+" edad: "+edad);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
