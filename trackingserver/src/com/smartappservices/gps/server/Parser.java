package com.smartappservices.gps.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;




public class Parser {

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
    String finalDate;

    public void parseData(String data) {
        if (data.length() == 95) {
            int i = data.length();
            //System.out.println("length="+i);
            phone_no = data.substring(1, 13);
            //System.out.println("phone11No="+phone_no);

            device18Id = data.substring(13, 32);
            //System.out.println("device18Id="+device18Id);

            time6 = data.substring(65, 71);
            IST time = new IST();
            String ISTnew = time.setIST(time6);
            //convert 5:30
            //System.out.println("time6="+time6);

            date6 = data.substring(32, 38);

            /**
             *  Now generate right format from date6.
             */
            String year = date6.substring(0, 2);
            String month = date6.substring(2, 4);
            String day = date6.substring(4, 6);
            int j = Integer.parseInt(month);
            
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

                //SimpleDateFormat dfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfGMT.setTimeZone(TimeZone.getTimeZone("IST"));
                finalDate = dfGMT.format(dateGMT);
            } catch (ParseException e) {
                System.out.println(e + "Date Format error");
            }


            availability = data.substring(38, 39);
            //System.out.println("availability="+availability);

            Float fdegree = null;
            Float fmin = null;
            Float fsec = null;

            fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)));
            fsec = Float.valueOf(Float.parseFloat("." + data.substring(44, 48)) / 60.0F);
            fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)) / 60.0F);

            fdegree = Float.valueOf(Float.parseFloat(data.substring(39, 41)));
            fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());
            latitude9 = fdegree.toString();

            north = data.substring(48, 49);

            fmin = Float.valueOf(Float.parseFloat(data.substring(52, 54)) / 60.0F);
            fsec = Float.valueOf(Float.parseFloat("." + data.substring(55, 59)) / 60.0F);
            fdegree = Float.valueOf(Float.parseFloat(data.substring(49, 52)));
            fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());

            longitude10 = fdegree.toString();

            east = data.substring(59, 60);
            //System.out.println("east="+east);

            speed5 = data.substring(60, 65);
            //System.out.println("speed5="+speed5);



            angle6 = data.substring(71, 77);
            //System.out.println("angle6="+angle6);

            powerStatus = data.substring(77, 78);
            //System.out.println("powerStatus="+powerStatus);

            accStatus = data.substring(78, 79);
            //System.out.println("accStatus="+accStatus);

            reserved7 = data.substring(79, 85);
            //System.out.println("reserved7="+reserved7);

            mileage = data.substring(85, 86);
            //System.out.println("mileage="+mileage);

            mileageinHexa6 = data.substring(86, 94);
            //System.out.println("mileageinHexa6="+mileageinHexa6); 	
        } else {
            int i = data.length();
            System.out.println("length=" + i);
            phone_no = data.substring(1, 13);
            System.out.println("phone11No=" + phone_no + "  Length==>" + phone_no.length());

            device18Id = data.substring(13, 32);
            System.out.println("device18Id=" + device18Id + "  Length==>" + device18Id.length());

            time6 = data.substring(65, 71);
            IST time = new IST();
            String ISTnew = time.setIST(time6);
            //convert 5:30
            System.out.println("time6=" + time6 + "  Length==>" + time6.length());

            date6 = data.substring(32, 38);

            /**
             *  Now generate right format from date6.
             */
            String year = date6.substring(0, 2);
            String month = date6.substring(2, 4);
            String day = date6.substring(4, 6);
            int j = Integer.parseInt(month);



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

                //SimpleDateFormat dfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dfGMT.setTimeZone(TimeZone.getTimeZone("IST"));
                finalDate = dfGMT.format(dateGMT);
            } catch (ParseException e) {
                System.out.println(e + "Date Format error");
            }


            availability = data.substring(38, 39);
            System.out.println("availability=" + availability + "  Length==>" + availability.length());

            Float fdegree = null;
            Float fmin = null;
            Float fsec = null;

            fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)));
            fsec = Float.valueOf(Float.parseFloat("." + data.substring(44, 48)) / 60.0F);
            fmin = Float.valueOf(Float.parseFloat(data.substring(41, 43)) / 60.0F);

            fdegree = Float.valueOf(Float.parseFloat(data.substring(39, 41)));
            fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());
            latitude9 = fdegree.toString();
            north = data.substring(48, 49);

            fmin = Float.valueOf(Float.parseFloat(data.substring(52, 54)) / 60.0F);
            fsec = Float.valueOf(Float.parseFloat("." + data.substring(55, 59)) / 60.0F);
            fdegree = Float.valueOf(Float.parseFloat(data.substring(49, 52)));
            fdegree = Float.valueOf(fdegree.floatValue() + fmin.floatValue() + fsec.floatValue());
            longitude10 = fdegree.toString();

            east = data.substring(59, 60);
            System.out.println("east=" + east + "  Length==>" + east.length());

            speed5 = data.substring(60, 65);
            System.out.println("speed5=" + speed5 + "  Length==>" + speed5.length());



            angle6 = data.substring(71, 77);
            System.out.println("angle6=" + angle6 + "  Length==>" + angle6.length());

            powerStatus = data.substring(79, 80);
            System.out.println("powerStatus=" + powerStatus + "  Length==>" + powerStatus.length());

            accStatus = data.substring(80, 81);
            System.out.println("accStatus=" + accStatus + "  Length==>" + accStatus.length());

            reserved7 = data.substring(81, 87);
            System.out.println("reserved7=" + reserved7 + "  Length==>" + reserved7.length());

            mileage = data.substring(87, 88);
            System.out.println("mileage=" + mileage + "  Length==>" + mileage.length());

            mileageinHexa6 = data.substring(88, 96);
            System.out.println("mileageinHexa6=" + mileageinHexa6 + "  Length==>" + mileageinHexa6.length());

        }

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trackman", "root", "root");

            String data1 = data;
            Date date1 = new Date();

            String date2 = date1.toString();

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select max(messageid) as messageid  from audit");
            int id = 0;
            resultSet.getFetchSize();
            if ((resultSet != null) && (resultSet.last())) {
                id = resultSet.getInt("messageid") + 1;
            }
            PreparedStatement pst = con.prepareStatement("insert into audit values(" + id + ",?,?)");
            pst.setString(1, date2);
            pst.setString(2, data1);
            System.out.println("#################################################################");
            pst.executeUpdate();
            pst.close();


//            Statement st=con.createStatement();
//            ResultSet rs=st.executeQuery("select Message_ID from liveMessageData");
//            int id=0;
//            if(rs.last()){id=rs.getInt("Message_ID")+1;}
//            
            Statement stmt = con.createStatement();
            stmt.executeUpdate("insert into livemessagedata values('" + id + "','" + phone_no + "','" + device18Id + "','" + finalDate + "','" + availability + "','" + latitude9 + "','" + north + "','" + longitude10 + "','" + east + "','" + speed5 + "','" + angle6 + "','" + powerStatus + "','" + accStatus + "','" + reserved7 + "','" + mileage + "','" + mileageinHexa6 + "','Noida')");



        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //String data1 = "(011177777712BP05000077777777712110718A1303.7464N07728.0893E000.011541211.25000000000L566602F8)";
        String data1 = "(119996096676BP05000009996096676111107A3020.8757N07650.2781E000.0090120245.310001000000L0000001F)";
        new Parser().parseData(data1);
    }
}
