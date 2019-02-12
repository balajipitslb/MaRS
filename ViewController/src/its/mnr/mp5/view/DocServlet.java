package its.mnr.mp5.view;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.sql.DataSource;

public class DocServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/plain; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType(CONTENT_TYPE);
        String docId = request.getParameter("id");
        OutputStream os = response.getOutputStream();
        Connection conn = null;
        String mimeType = null;

        try {
            Context ctx = new InitialContext();
            //Datasource as defined in <res-ref-name> element of weblogic.xml
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/MP5DS");
            conn = ds.getConnection();
            PreparedStatement statement =
                conn.prepareStatement("SELECT docid, docfile, filetype " + "FROM MRLT_DOCUMENTS " + "WHERE docid = ?");
            statement.setInt(1, new Integer(docId));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                mimeType = (String)rs.getString("FILETYPE");
                // Set the content-type. Only images are taken into account
                response.setContentType(mimeType + "; charset=utf8");
                Blob blob = rs.getBlob("DOCFILE");
                BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                int b;
                byte[] buffer = new byte[10240];
                while ((b = in.read(buffer, 0, 10240)) != -1) {
                    os.write(buffer, 0, b);
                }
                os.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("SQLException error");
            }
        }
    }
}

