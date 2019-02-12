package its.mnr.mp5.view;

import java.util.ArrayList;
import java.util.List;

public class ContentTypes
{

    public static String get(String fileName)
    {

        String mime = null;
        String ext = fileName.toLowerCase();

        if (ext.endsWith(".pdf"))
        {
            mime = "application/pdf";
        }
        else if (ext.endsWith(".doc"))
        {
            mime = "application/msword";
        }
        else if (ext.endsWith(".xls"))
        {
            mime = "application/msexcel";
        }
        else if (ext.endsWith(".docx"))
        {
            mime = "application/msword2007";
        }
        else if (ext.endsWith(".xlsx"))
        {
            mime = "application/msexcel2007";
        }
        else if (ext.endsWith(".ppt"))
        {
            mime = "application/vnd.ms-powerpoint";
        }
        else if (ext.endsWith(".rar"))
        {
            mime = "application/octet-stream";
        }
        else if (ext.endsWith(".zip"))
        {
            mime = "application/zip";
        }
        else if (ext.endsWith(".jpg"))
        {
            mime = "image/jpeg";
        }
        else if (ext.endsWith(".jpeg"))
        {
            mime = "image/jpeg";
        }
        else if (ext.endsWith(".gif"))
        {
            mime = "image/gif";
        }
        else if (ext.endsWith(".png"))
        {
            mime = "image/png";
        }
        else if (ext.endsWith(".txt"))
        {
            mime = "text/plain";
        }
        else if (ext.endsWith(".htm"))
        {
            mime = "text/html";
        }
        else if (ext.endsWith(".html"))
        {
            mime = "text/html";
        }
        else if (ext.endsWith(".htmls"))
        {
            mime = "text/html";
        }
        return mime;
    }
    
    public static Boolean mimeTypeAllowed(String sCat, String sMimeType) {
        Boolean ret = false;
        List list = new ArrayList();

        if ("DEFAULT".equals(sCat)) {
            list.add("application/pdf");
            list.add("image/jpeg");
            list.add("image/gif");
            list.add("image/png");
            list.add("text/plain");
        } else if ("ASSETPHOTO".equals(sCat)) {
            list.add("image/jpeg");
            list.add("image/gif");
            list.add("image/png");
        }

        ret = list.contains(sMimeType);

        return ret;

    }
}
