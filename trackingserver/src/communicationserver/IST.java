package communicationServer;

public class IST
{
  int min2;

  public String setIST(String time)
  {
    String hr = time.substring(0, 2);
    int hr1 = Integer.parseInt(hr);

    String min = time.substring(2, 4);
    int min1 = Integer.parseInt(min);

    if (min1 >= 60) {
      min1 -= 60;
      hr1++;
    }
    String hr2 = Integer.toString(hr1);

    String sec = time.substring(4, 6);
    String time1;
    
    if (min1 == 0)
      time1 = hr2 + ":" + "00" + ":" + sec;
    else {
      time1 = hr2 + ":" + min1 + ":" + sec;
    }

    return time1;
  }
}