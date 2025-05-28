package Gestion_de_tienda;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class informeStockTest {
    private static final String TEST_DB_URI = "jdbc:mysql://localhost:3306/pr_ejemplo"; // Cambia esto a tu base de datos de prueba
    private static final String TEST_USER = "root";
    private static final String TEST_PASSWORD = "Root123*";
    private static final String INVENTARIO_FILE = "Inventario.txt";

    @Test
    public void elegir(){
        char esperado= 'S';
        char a = 'S';
        try {
            assertEquals(esperado,a);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    public void elegir2(){
        char esperado= 'S';
        char a = 's';
        try {
            assertEquals(esperado,a);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    public void elegir3(){
        char esperado= 'S';
        int a = 1;
        try {
            assertEquals(esperado,a);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    public void elegir4(){
        char esperado= 'S';
        char a = 'N';
        try {
            assertEquals(esperado,a);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    public void buscar(){
        String resultado = MyinformeStock.buscarEnLaBasesDeDatos();
        assertTrue(resultado.contains("Manzana"));
        assertTrue(resultado.contains("10"));
        assertTrue(resultado.contains("PERAS"));
        assertTrue(resultado.contains("1"));

    }
}