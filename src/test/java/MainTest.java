import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Main test class
 * author Guangpeng Wu
 */
public class MainTest {


  private Main mainExample;
  private String[] argsTest;
  private String[] argsTest2;

  /**
   * set up the program arguments
   * @throws Exception when invalid arguments
   */
  @Before
  public void setUp() throws Exception {
    argsTest = new String[]{"--email","--email-template","myEmailTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };
    argsTest2 = new String[]{"--letter","--letter-template","myLetterTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };
  }

  /**
   * test main
   */
  @Test
  public void main() {
    String[] args = new String[]{"--email","--email-template","myEmailTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };

    try {
      mainExample.main(args);
    } catch (IOException e) {
      e.printStackTrace();
    }


    String[] args2 = new String[]{"--letter","--letter-template","myLetterTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };

    try {
      mainExample.main(args2);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test main when it throws exception
   */
  @org.junit.Test
  public void getException() throws Exception{
    String[] args3 = new String[]{"--email","--letter-template","myLetterTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };
    try {
      mainExample.main(args3);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test main when it throws exception
   */
  @org.junit.Test
  public void getException2() throws Exception{
    String[] args3 = new String[]{"--letter","--email-template","myEmailTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };
    try {
      mainExample.main(args3);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test main when it throws exception
   */
  @org.junit.Test
  public void getException3() throws Exception{
    String[] args3 = new String[]{"--email","--email-template","myEmailTemplate.txt","--csv-file","insurance-company-members.csv"
    };
    try {
      mainExample.main(args3);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test main when it throws exception
   */
  @org.junit.Test
  public void getException4() throws Exception{
    String[] args3 = new String[]{"--email","--email-template","myEmailTemplate.txt","--output-dir","output"
    };
    try {
      mainExample.main(args3);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test main when it throws exception
   */
  @org.junit.Test
  public void getException5() throws Exception{
    String[] args3 = new String[]{"--email-template","myEmailTemplate.txt","--output-dir","output","--csv-file","insurance-company-members.csv"
    };
    try {
      mainExample.main(args3);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * test read Email
   */
  @Test
  public void readEmail() throws IOException {
    mainExample.readEmail("input\\", "myEmailTemplate.txt","output","insurance-company-members.csv");
  }

  /**
   * test write Email
   */
  @Test
  public void writeEmail() throws IOException {
    mainExample.readEmail("input\\", "myEmailTemplate.txt","output","insurance-company-members.csv");
  }

  /**
   * test read Letter
   */
  @Test
  public void readLetter() throws IOException {
    mainExample.readLetter("input\\", "myLetterTemplate.txt","output","insurance-company-members.csv");

  }

  /**
   * test write Letter
   */
  @Test
  public void writeLetter() throws IOException {
    mainExample.readEmail("input\\", "myLetterTemplate.txt","output","insurance-company-members.csv");

  }

  /**
   * test read csv file
   */
  @Test
  public void readCsv() throws IOException {
    ArrayList<Map<String, String>> users =mainExample.readCsv("input\\","insurance-company-members.csv");
    Assert.assertEquals("James",users.get(0).get("\"first_name\""));
    Assert.assertEquals("Butt",users.get(0).get("\"last_name\""));
    Assert.assertEquals("Benton, John B Jr",users.get(0).get("\"company_name\""));
    Assert.assertEquals("6649 N Blue Gum St",users.get(0).get("\"address\""));
    Assert.assertEquals("New Orleans",users.get(0).get("\"city\""));
    Assert.assertEquals("Orleans",users.get(0).get("\"county\""));
    Assert.assertEquals("LA",users.get(0).get("\"state\""));
    Assert.assertEquals("70116",users.get(0).get("\"zip\""));
    Assert.assertEquals("504-621-8927",users.get(0).get("\"phone1\""));
    Assert.assertEquals("504-845-1427",users.get(0).get("\"phone2\""));
    Assert.assertEquals("http://www.bentonjohnbjr.com",users.get(0).get("\"web\""));
  }
}