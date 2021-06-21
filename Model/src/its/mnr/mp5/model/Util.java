package its.mnr.mp5.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.DecimalFormat;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import oracle.jbo.domain.Date;
import oracle.jbo.domain.Timestamp;

import oracle.xml.parser.v2.XMLNode;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import org.w3c.dom.Node;

public class Util {
    public Util() {
        super();
    }

    public static void sendMessage(String toaddress, String subject, String body, String host, Integer port,
                                   String fromemail) {
        System.out.println("Inside Util.java sendMessage: toaddress: "+toaddress+" subject: "+subject+" body: "+body+" host: "+host+" port: "+port+" fromemail: "+fromemail);

        try {
            if(body!=null){
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(port);
            //email.setAuthenticator(new DefaultAuthenticator("username", "password"));
            //email.setSSLOnConnect(true);
            email.setFrom(fromemail);
            email.setSubject(subject);
            email.setMsg(body);
            email.addTo(toaddress);
            email.send();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void printXML(Node n, String fileName) {
        System.out.println("Inside Util printXML n: "+n+" fileName: "+fileName);
        File file = new java.io.File(fileName);
        //java.io.File file = new java.io.File("C:/Windows/Temp/emp.xml");
        PrintWriter output = null;
        try {
            output = new java.io.PrintWriter(file);
            ((XMLNode)n).print(output);
        } catch (IOException e) {
            //throw new RuntimeException("IOExceptions will crash the application", e);
            System.out.println("Inside Util printXML e.getMessage(): "+e.getMessage());
        } catch (Exception e) {
            System.out.println("Inside Util printXML e.getMessage() 2: "+e.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    System.out.println("Inside Util printXML e.getMessage() finally: "+e.getMessage());
                }
            }
        }

    }

    public static Boolean writeTextFile(String fileName, String s) {
        System.out.println("Inside Util writeTextFile");
        Boolean Chk = false;

        FileWriter output = null;
        try {
            output = new FileWriter(fileName);
            output.write(s);
            System.out.println("Inside Util writeTextFile s: "+s);
            //output.flush();
            //output.close();
        } catch (Exception e) {
            System.out.println("Inside Util writeTextFile Exception: "+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Chk;
    }
    
    
    public static boolean deleteFile(String sfilename) {
        Boolean deleteChk = false;
        try {
            File file = new File(sfilename);
            if (file.delete()) {
                deleteChk = true;
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    return deleteChk;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }
    
    public static Double roundToHundredth(Double dValue){
            DecimalFormat df = new DecimalFormat("###.##");
            return Double.valueOf(df.format(dValue));        
        }
    
    /** function return current date time in format (dd / MM / yyyy - HH:mm:ss) */
     public static String getDisplayedDateTime()
     {
       Calendar cal = Calendar.getInstance();
       String dateFormat = "dd / MM / yyyy - HH:mm:ss";
       SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
       return sdf.format(cal.getTime());
     }

     /** function return current date or time depending on the format which you are want. Just send a format and the function will return the current date or time depending on the 
         format which you are sent (e.g. getDisplayedDateFormat("yyyy-mm-dd") it will return 2013-11-18 */
     public static String getDisplayedDateFormat(String dateFormat)
     {
       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
       return sdf.format(cal.getTime());
     }

     /** function return the index of current day in this week (if today is Sunday it will retun 1 and if the today is Monday this function will return 2 and so on... 
         till Saturday it will return 7) */
     public static int getCurrentDayOfWeek()
     {
       Calendar cal = Calendar.getInstance();
       return cal.get(Calendar.DAY_OF_WEEK);
     }

     /** function return the name of current day in this week (Saturday - Sunday - ...... - Friday) */
     public static String getCurrentDayOfWeekName()
     {
       int numberOfDay = getCurrentDayOfWeek();
       if(numberOfDay == 7)
       {
         return "Saturday";
       }
       else if(numberOfDay == 1)
       {
         return "Sunday";
       }
       else if(numberOfDay == 2)
       {
         return "Monday";
       }
       else if(numberOfDay == 3)
       {
         return "Tuesday";
       }
       else if(numberOfDay == 4)
       {
         return "Wednesday";
       }
       else if(numberOfDay == 5)
       {
         return "Thursday";
       }
       return "Friday";
     }

     /** function return current day. ( e.g. today is 2013/11/18 this function will return 18) */
     public static int getCurrentDay()
     {
       Calendar cal = Calendar.getInstance();
       return cal.get(Calendar.DAY_OF_MONTH);
     }

     /** function return current month. ( e.g. today is 2013/11/18 this function will return 11) */
     public static int getCurrentMonth()
     {
       Calendar cal = Calendar.getInstance();
       return cal.get(Calendar.MONTH) + 1;
     }

     /** function return current year. ( e.g. today is 2013/11/18 this function will return 2013) */
     public static int getCurrentYear()
     {
       Calendar cal = Calendar.getInstance();
       return cal.get(Calendar.YEAR);
     }

     /** function will return current date as an oracle Date object */
     public static oracle.jbo.domain.Date getCurrentOracleDate()
     {
       return new oracle.jbo.domain.Date(oracle.jbo.domain.Date.getCurrentDate());
     }

     /** function will return current date and time as an oracle Date object */
     public static oracle.jbo.domain.Date getCurrentOracleDateTime()
     {
       return new oracle.jbo.domain.Date(new java.sql.Timestamp(System.currentTimeMillis()));
     }

     /** function will return current date and time as an oracle Timestamp object */
     public static oracle.jbo.domain.Timestamp getCurrentOracleTimestampDateTime()
     {
       return new oracle.jbo.domain.Timestamp(new java.util.Date().getTime());
     }

     /** function will return current time */
     public static String getCurrentTime()
     {
       return  getDisplayedDateFormat("HH:mm");
     }

     /** function receive oracle date and return true or false (if sent date greater than current date it will return true 
         but if sent date less than or equal current date it will return false */
     public static boolean isDateGreaterThanCurrentDate(oracle.jbo.domain.Date d)
     {
       if(d == null)
         return false;
     
       oracle.jbo.domain.Date currentDate = new oracle.jbo.domain.Date(oracle.jbo.domain.Date.getCurrentDate());
       if(d.compareTo(currentDate) > 0)
         return true;
     
       return false;
     }

     /** function receive two oracle dates and return true or false (if first date greater than second date it will return true 
         but if first date less than or equal second date it will return false */
     public static boolean isFirstDateGreaterThanSecondDate(oracle.jbo.domain.Date d1, oracle.jbo.domain.Date d2)
     {
       if(d1 == null && d2 == null)
         return false;
     
       if(d1 == null && d2 != null)
         return false;
     
       if(d1 != null && d2 == null)
         return true;
     
       if(d1.compareTo(d2) > 0)
         return true;
     
       return false;
     }

     /** function receive three oracle dates and return true or false (if first date greater than or equal second date and first date less than or equal third date it will return true. 
         otherwise it will return false */
     public static boolean isFirstDateBetweenTwoDates(oracle.jbo.domain.Date d1, oracle.jbo.domain.Date d2, oracle.jbo.domain.Date d3)
     {
       if(d1 == null || d2 == null || d3 == null)
         return false;
     
       if(d1.compareTo(d2) >= 0 && d1.compareTo(d3) <= 0)
         return true;
     
       return false;
     }

     /** function receive date and integer then add the integer to the date then return the result date.
         (e.g. if you send 2013/07/13 and 5 to this function the function will return 2013/07/18 */
     public static Date addDayToOracleDate(oracle.jbo.domain.Date date, int days)
     {
       if (date != null)
       {
         Calendar c1 = Calendar.getInstance();
         c1.setTime(date.getValue());
         c1.add(Calendar.DATE, days);
         java.util.Date javaUtilDate = c1.getTime();
         long javaMilliseconds = javaUtilDate.getTime();
         java.sql.Date javaSqlDate = new java.sql.Date(javaMilliseconds);
         return new oracle.jbo.domain.Date(javaSqlDate);
       }
       return null;
     }

     /** function receive two oracle dates then return the number of days difference between them. (e.g. if you send 2013/07/20 , 2013/07/13 to the function the function will return 7 */
     public static long getDifferenceDaysBetweenTwoDates(oracle.jbo.domain.Date d1, oracle.jbo.domain.Date d2)
     {
       if(d1 != null && d2 != null)
       {
         return (d1.getValue().getTime() - d2.getValue().getTime())/(24 * 60 * 60 * 1000);
       }
       return 0;
     }

     /** function receive oracle date and convert it to java.util date then return java.util date */
     public static java.util.Date convertOracleDateToJavaUtilDate(oracle.jbo.domain.Date oracleDate)
     {
       if(oracleDate == null)
         return null;
     
       java.sql.Date javaSqlDate = oracleDate.dateValue();
       long javaMilliSeconds = javaSqlDate.getTime();
       return new java.util.Date(javaMilliSeconds);
     }

     /** function receive oracle date and convert it to java.sql date then return java.sql date */
     public static java.sql.Date convertOracleDateToJavaSqlDate(oracle.jbo.domain.Date oracleDate)
     {
       if(oracleDate == null)
         return null;
     
       return oracleDate.dateValue();
     }

     /** function receive java.util date and convert it to oracle date then return oracle date */
     public static Date convertJavaUtilDateToOracleDate(java.util.Date javaUtilDate)
     {
       if(javaUtilDate == null)
         return null;
     
       long javaMilliseconds = javaUtilDate.getTime();
       java.sql.Date javaSqlDate = new java.sql.Date(javaMilliseconds);
       return new oracle.jbo.domain.Date(javaSqlDate);
     }

     /** function receive java.sql date and convert it to oracle date then return oracle date */
     public static Date convertJavaSqlDateToOracleDate(java.sql.Date javaSqlDate)
     {
       if(javaSqlDate == null)
         return null;
     
       return new oracle.jbo.domain.Date(javaSqlDate);
     }

     /** function receive java.util date and convert it to java.sql date then return java.sql date */
     public static java.sql.Date convertJavaUtilDateToJavaSqlDate(java.util.Date javaUtilDate)
     {
       if(javaUtilDate == null)
         return null;
     
       long javaMilliseconds = javaUtilDate.getTime();
       return new java.sql.Date(javaMilliseconds);
     }

     /** function receive java.sql date and convert it to java.util date then return java.util date */
     public static java.util.Date convertJavaSqlDateToJavaUtilDate(java.sql.Date javaSqlDate)
     {
       if(javaSqlDate == null)
         return null;
     
       long javaMilliseconds = javaSqlDate.getTime();
       return new java.util.Date(javaMilliseconds);
     }

}
