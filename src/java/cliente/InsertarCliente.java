/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cliente;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ianga
 */
@WebServlet(name = "InsertarCliente", urlPatterns = {"/InsertarCliente"})
public class InsertarCliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Declaración de variables           
            Connection conexion;
            Statement sentencia;
            PreparedStatement sentenciaps;
            ResultSet resultado;
            
            // Obtener información del formulario
            String id = request.getParameter("id");
            String nombre = request.getParameter("nombre");
            String telefono = request.getParameter("telefono");
            String direccion = request.getParameter("direccion");
            String correo = request.getParameter("correo");
            
            // Llamada para cargar el driver que conecta a la base de datos
            Class.forName("com.mysql.jdbc.Driver"); 
            // Obtener una conexión con la base de datos.
            conexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/menu_master_db","root", "");
            
            // Crear un PreparedStatement objeto para enviar instrucciones SQL con parámetros
            sentenciaps = conexion.prepareStatement("INSERT INTO cliente VALUES (?,?,?,?,?)");
            // Establecer un valor en cada uno de los marcadores de parámetro de la sentencia SQL
            sentenciaps.setInt(1, Integer.parseInt(id));
            sentenciaps.setString(2, nombre);
            sentenciaps.setInt(3, Integer.parseInt(telefono));
            sentenciaps.setString(4, direccion);
            sentenciaps.setString(5, correo);
            sentenciaps.executeUpdate();
            
            // Crear un Statement objeto para enviar instrucciones SQL sin parámetros
            sentencia = conexion.createStatement();
            // Ejecutar una consulta SQL en la tabla cliente
            resultado = sentencia.executeQuery ("SELECT * FROM cliente");
            
            // Página HTML con la respuesta
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Datos Ingresados Exitosamente</h1>");
            out.println("<table align='center' with='75%' border=1>");
            out.println("<tr><th>id</th><th>Nombre</th><th>Telefono</th><th>Direccion</th><th>Correo"+"</th></tr>");
            
            // Visualizar todos los datos de la tabla cliente
            while (resultado.next()){
               out.println("<tr><td>"+resultado.getInt("id_cliente")+"</td><td>"+
               resultado.getString("nombre")+"</td><td>"+resultado.getInt("telefono")+"</td><td>"+resultado.getString("direccion")+"</td><td>"+resultado.getString("correo")+"</td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            conexion.close();
        }
        catch (ClassNotFoundException e1) {
                  //Error si no puedo leer el driver 
             System.out.println("ERROR: No encuentro el driver de la BD: "+  e1.getMessage());
        }
        catch (SQLException e2) {
                 //Error SQL: login/passwd mal
             System.out.println("ERROR: Fallo en SQL: "+e2.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
