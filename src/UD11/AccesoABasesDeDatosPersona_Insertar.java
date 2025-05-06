package UD11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoABasesDeDatosPersona_Insertar {
    public static void main(String[] args) {
        //esto es el protocolo://ip:puerto_tcp/nombre_DB
        String uri = "jdbc:mysql://localhost:3306/pr_ejemplo";
        String usuario = "root";
        String password = "Root123*";
        // Paso 1: Crear conexion entre estas app y MySQL
        try {
            Connection miConexion = DriverManager.getConnection(uri, usuario, password);
            //Paso 2: Crear una sentencia SQL
            Statement setenciaSQL = miConexion.createStatement();
            //Paso 3: Ejecutar Sentencia SQL
            String sql = "insert into persona (dni,nombre,edad) values " +
                    "('123ab','Freddy Bernabeu',25);";
            setenciaSQL.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
