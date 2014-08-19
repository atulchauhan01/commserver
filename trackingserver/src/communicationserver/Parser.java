package communicationServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Parser
{
  String phone_no;
  String device18Id;
  String date6;
  String availability;
  String latitude9;
  String north;
  String longitude10;
  String east;
  String speed5;
  String time6;
  String angle6;
  String powerStatus;
  String accStatus;
  String reserved7;
  String mileage;
  String mileageinHexa6;

  public void parseData(String data)
  {
    int i = data.length();

    String phone_no = data.substring(1, 13);

    String device18Id = data.substring(13, 32);

    String time6 = data.substring(65, 71);
    IST time = new IST();
    String ISTnew = time.setIST(time6);

    String date6 = data.substring(32, 38);

    String year = date6.substring(0, 2);
    String month = date6.substring(2, 4);
    String day = date6.substring(4, 6);
    int j = Integer.parseInt(month);
    String finalDate;

    switch (j) {
    case 1:
      finalDate = "20" + year + "-01-" + day + " " + ISTnew;
      break;
    case 2:
      finalDate = "20" + year + "-02-" + day + " " + ISTnew;
      break;
    case 3:
      finalDate = "20" + year + "-03-" + day + " " + ISTnew;
      break;
    case 4:
      finalDate = "20" + year + "-04-" + day + " " + ISTnew;
      break;
    case 5:
      finalDate = "20" + year + "-05-" + day + " " + ISTnew;
      break;
    case 6:
      finalDate = "20" + year + "-06-" + day + " " + ISTnew;
      break;
    case 7:
      finalDate = "20" + year + "-07-" + day + " " + ISTnew;
      break;
    case 8:
      finalDate = "20" + year + "-08-" + day + " " + ISTnew;
      break;
    case 9:
      finalDate = "20" + year + "-09-" + day + " " + ISTnew;
      break;
    case 10:
      finalDate = "20" + year + "-10-" + day + " " + ISTnew;
      break;
    case 11:
      finalDate = "20" + year + "-11-" + day + " " + ISTnew;
      break;
    case 12:
      finalDate = "20" + year + "-12-" + day + " " + ISTnew;
      break;
    default:
      finalDate = "50-Jan-01 00:00:00";
    }

   finalDate = finalDate + " GMT";

    SimpleDateFormat dfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
    try {
      Date dateGMT = dfGMT.parse(finalDate);

      SimpleDateFormat dfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      dfGMT.setTimeZone(TimeZone.getTimeZone("IST"));
      finalDate = dfGMT.format(dateGMT);
    }
    catch (ParseException e) {
      System.out.println(e + "Date Format error");
    }

    String availability = data.substring(38, 39);

    Float fdegree = null;
    Float fmin = null;
    Float fsec = null;

    fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)));
    fsec = Float.valueOf(Float.parseFloat("." + data.substring(44, 48)) / 60.0F);
    fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)) / 60.0F);

    fdegree = Float.valueOf(Float.parseFloat(data.substring(39, 41)));
    fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());
    String latitude9 = fdegree.toString();

    String north = data.substring(48, 49);

    fmin = Float.valueOf(Float.parseFloat(data.substring(52, 54)) / 60.0F);
    fsec = Float.valueOf(Float.parseFloat("." + data.substring(55, 59)) / 60.0F);
    fdegree = Float.valueOf(Float.parseFloat(data.substring(49, 52)));
    fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());

    String longitude10 = fdegree.toString();

    String east = data.substring(59, 60);

    String speed5 = data.substring(60, 65);

    String angle6 = data.substring(71, 77);

    String powerStatus = data.substring(77, 78);

    String accStatus = data.substring(78, 79);

    String reserved7 = data.substring(79, 85);

    String mileage = data.substring(85, 86);

    String mileageinHexa6 = data.substring(86, 94);
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trackman", "root", "root");
        System.out.println("Connection :::::::::::"+ con);

      String data1 = new String(data);
      Date date1 = new Date();

      String date2 = date1.toString();

      Statement statement = con.createStatement();
      ResultSet resultSet = statement.executeQuery("select max(messageid) as messageid  from audit");
        System.out.println("maxid");
      int id = 0;
      resultSet.getFetchSize();
      if ((resultSet != null) && (resultSet.last())) {
        id = resultSet.getInt("messageid") + 1;
      }
      PreparedStatement pst = con.prepareStatement("insert into audit values(" +id + ",?,?)");
      pst.setString(1, date2);
      pst.setString(2, data1);
      System.out.println("#################################################################");
      pst.executeUpdate();
      pst.close();

      Statement stmt = con.createStatement();
      stmt.executeUpdate("insert into livemessagedata values('" + id + 
        "','" + phone_no + "','" + device18Id + "','" + finalDate + 
        "','" + availability + "','" + latitude9 + "','" + north + 
        "','" + longitude10 + "','" + east + "','" + speed5 + 
        "','" + angle6 + "','" + powerStatus + "','" + accStatus + 
        "','" + reserved7 + "','" + mileage + "','" + 
        mileageinHexa6 + "','Noida')");
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

//  public static void main(String[] args) {
//    String data1 = "(011177777712BP05000077777777712110718A1303.7464N07728.0893E000.011541211.25000000000L566602F8)";
//    String data2 = "(020000000034BP05000020000000034110926A2618.7289N08945.3916E000.0204111149.1900000000L02AB9417)";
//    String testdata = "(010000000001BP05000010000000001081120V2838.9405N07720.4238E000.0000000000.0000000000L0075564C)";
//    String testdata1 = "(010000000144BP05000010000000144111103A2838.9635N07720.4376E000.0141109191.5000000000L0075564C)";
//    new Parser().parseData(testdata1);
//  }
}
