package Db_alumnos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

public class Ventana extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel etq;
    private JLabel et2;
    private JLabel et3;
    private JLabel et4;
    private JTextField tex1;
    private JTextField tex2;
    private JTextField tex3;
    private JTextField tex4;
    private JButton bto1;
    private JButton bto2;
    static Scanner in = new Scanner(System.in);
    static Connection connection;

    public Ventana() {
        super("Informe estudiantes");
        this.setBounds(0, 0, 500, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        contruirPanel();
        this.setVisible(true);
    }

    private void contruirPanel() {
        panel = new JPanel();

        et2 = new JLabel();
        et2.setText("Nombre");
        panel.add(et2);

        tex1 = new JTextField(10);
        panel.add(tex1);

        et3 = new JLabel();
        et3.setText("Edad");
        panel.add(et3);

        tex2 = new JTextField(3);
        panel.add(tex2);

        et4 = new JLabel();
        et4.setText("Nota media");
        panel.add(et4);

        tex3 = new JTextField(5);
        panel.add(tex3);

        etq = new JLabel();
        etq.setText("id");
        panel.add(etq);

        tex4 = new JTextField(2);
        panel.add(tex4);

        bto1 = new JButton("Insertar");
        panel.add(bto1);
        bto1.addActionListener(this);
        bto2 = new JButton("Buscar");
        panel.add(bto2);
        bto2.addActionListener(this);

        this.add(panel);


    }

    public static void conectarConBD() {
        try {
            String uri = "jdbc:mysql://localhost:3306/db_alumnos";
            String usuario = "root";
            String password = "Root123*";
            connection = DriverManager.getConnection(uri, usuario, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void ejecutarIntruccionesSQL(String sql) {
        try {
            Statement senteciaSQL = connection.createStatement();
            senteciaSQL.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String buscarAlumno(String id) {
        Statement setenciaSQL;
        ResultSet resultSet;
        String datosAlumnos = "";
        try {
            setenciaSQL = connection.createStatement();
            String sql = "select * from alumno" + " where id=" +
                    id + ";";
            resultSet = setenciaSQL.executeQuery(sql);
            if (resultSet.next()) {
                datosAlumnos = resultSet.getString("nombre") + ","
                        + resultSet.getDouble("edad") + ","
                        + resultSet.getInt("nota_media");
            }

            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

        }
        return datosAlumnos;
    }

    public static String ultimoID() {
        Statement setenciaSQL;
        ResultSet resultSet;
        int idS = 0;
        try {
            setenciaSQL = connection.createStatement();
            String sql = "select max(id)+1 as id from alumno";
            resultSet = setenciaSQL.executeQuery(sql);
            if (resultSet.next()) {
                idS = resultSet.getInt("id");
                idS += 1;
            }
            System.out.println(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "" + idS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        conectarConBD();
        String sql = "insert into alumno values (";
        if (bto1 == e.getSource()) {
            int id = Integer.parseInt(ultimoID());
            tex4.setText("" + id);
            System.out.println(tex4);
            System.out.println();
            sql += tex4.getText();
            sql += ",'";
            sql += tex1.getText();
            sql += "',";
            sql += tex2.getText();
            sql += ",";
            sql += tex3.getText();
            sql += ")";
            System.out.println(sql);
            ejecutarIntruccionesSQL(sql);
        }
        if (bto2 == e.getSource()) {
            String id = tex4.getText();
            String datos = buscarAlumno(id);
            String[] ej = null;
            for (int i = 0; i < 3; i++) {
                ej = datos.split(",");
                System.out.println(ej[i]);

            }
            tex1.setText(ej[0]);
            tex2.setText(ej[1]);
            tex3.setText(ej[2]);


        }
    }
}
