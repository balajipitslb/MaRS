package its.mnr.mp5.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

import oracle.jbo.domain.BlobDomain;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class FileUtil {
    public FileUtil() {
        super();
    }
    public static BlobDomain createBlobDomain(UploadedFile file)
    {
        //ystem.out.println("createBlobDomain");
        // init the internal variables
        InputStream in = null;
        BlobDomain blobDomain = null;
        OutputStream out = null;

        try
        {
            // Get the input stream representing the data from the client
            in = file.getInputStream();
            // create the BlobDomain datatype to store the data in the db
            blobDomain = new BlobDomain();
            // get the outputStream for hte BlobDomain
            out = blobDomain.getBinaryOutputStream();
            // copy the input stream into the output stream
            /*
             * IOUtils is a class from the Apache Commons IO Package (http://www.apache.org/)
             * Here version 2.0.1 is used
             * please download it directly from http://projects.apache.org/projects/commons_io.html
             */
            IOUtils.copy(in, out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.fillInStackTrace();
        }

        // return the filled BlobDomain
        return blobDomain;
    }    
    public void downloadFile(FacesContext facesContext, OutputStream outputStream)
    {
        System.out.println("downloadFile");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        // get an ADF attributevalue from the ADF page definitions
        AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("Docfile");
        if (attr == null)
        {
            return;
        }

        // the value is a BlobDomain data type
        BlobDomain blob = (BlobDomain) attr.getInputValue();

        try
        {   // copy hte data from the BlobDomain to the output stream 
            IOUtils.copy(blob.getInputStream(), outputStream);
            // cloase the blob to release the recources
            blob.closeInputStream();
            // flush the outout stream
            outputStream.flush();
        }
        catch (IOException e)
        {
            // handle errors
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
