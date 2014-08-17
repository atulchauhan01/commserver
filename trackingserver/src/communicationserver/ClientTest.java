package communicationserver;

import communicationServer.IST;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ClientTest {

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

    public void parseString(String data) {
        int i = data.length();
        System.out.println("1.length: " + i);
        
        phone_no = data.substring(1, 13);
        System.out.println("2.Phone NO: "+phone_no);
        
        device18Id = data.substring(13, 32);
        System.out.println("4.3.Device Id: "+device18Id);
        
        time6 = data.substring(65, 71);
        System.out.println("5.Time: "+time6);

        date6 = data.substring(32, 38);
        System.out.println("6.date :"+date6);
        
        availability = data.substring(38, 39);
        System.out.println("7.Availability: "+availability);

        latitude9=data.substring(39, 48);
        System.out.println("8.Latitude: "+latitude9);
        
        north = data.substring(48, 49);
        System.out.println("9.North: "+north);

        longitude10=data.substring(49, 59);
        System.out.println("10.Longitude: "+longitude10);

        east = data.substring(59, 60);
        System.out.println("11.East: "+east);
        
        speed5 = data.substring(60, 65);
        System.out.println("12.Speed: "+speed5);
        
        angle6 = data.substring(71, 77);
        System.out.println("13.Angle: "+angle6);
        
        powerStatus = data.substring(77, 78);
        System.out.println("14.Power Status: "+powerStatus);

        accStatus = data.substring(78, 79);
        System.out.println("15.Acc Status: "+accStatus);
        
        reserved7 = data.substring(79, 85);
        System.out.println("17.reverse: "+reserved7);

        mileage = data.substring(85, 86);
        System.out.println("18.Mileage: "+mileage);
        
        mileageinHexa6 = data.substring(86, 94);
        System.out.println("19.Reserve: "+mileageinHexa6);
        
        System.out.println("OpenGTS's String:"+"imei:123451042191239,tracker ,1107090553,9735551234,F,215314.000,A,4103.7641,N,14244.9450,W,0.08");        
    
       String dataGTS="imei:000"+phone_no+","+device18Id+","+date6+"0000,"+phone_no+",F,"+time6+".000,"+availability+","+latitude9+","+north+","+longitude10+","+east+","+speed5+"";
       String ss[]=dataGTS.split(",");
        System.out.println("ss.length ==> "+ss.length);
        
      // System.out.println("length==>"+dataGTS.length());
        System.out.println("Our String:      "+dataGTS+"\n");
        
        //System.out.println("Our String:      "+"imei:000"+phone_no+","+device18Id+","+date6+"0000,"+phone_no+",F,"+time6+".000,"+availability+","+latitude9+","+north+","+longitude10+","+east+","+speed5+"");
    }

    public static void main(String[] args) {
        //String testdata1 = "(010000000144BP05000010000000144111103A2838.9635N07720.4376E000.0141109191.5000000000L0075564C)";
                            //(099999999927BP05000099999999927111218A1408.8752N07629.2164E000.0000402327.6700000003L0617FE59
          String testdata1 = "(099999999927BP05000099999999927111219A1648.7288N07434.1547E018.507180063.17001000003L061E28BF)";
        new ClientTest().parseString(testdata1);
    }
}
