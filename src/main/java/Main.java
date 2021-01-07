import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * assignment 3 Main class
 * implement the email and the letter generator program for the insurance company
 * author Guangpeng Wu
 */
public class Main {

  /**
   * main method
   * build each status with different flag
   * get the path and file
   * @param args program arguments
   * @throws IOException when invalid arguments
   */
  public static void main(String[] args) throws IOException {

    boolean emailFlag=false;
    boolean letterFlag=false;
    boolean emailTemplateFlag=false;
    boolean letterTemplateFlag=false;
    boolean outputDirFlag=false;
    boolean csvFlag=false;

    String emailTemplateFile="";
    String letterTemplateFile="";
    String outputPath="";
    String csvFile="";

    //System.out.println(Arrays.toString(args));

    for (int i = 0; i < args.length; i++) {
      //System.out.println(args[i]);
      if(args[i].equals("--email")){
        emailFlag=true;
      }

      if(args[i].equals("--letter")){
        letterFlag=true;
      }

      if(args[i].equals("--email-template")){
        emailTemplateFlag=true;
        emailTemplateFile = args[i + 1];
      }

      if(args[i].equals("--letter-template")){
        letterTemplateFlag=true;
        letterTemplateFile = args[i + 1];
      }

      if(args[i].equals("--output-dir")){
        outputDirFlag=true;
        outputPath = args[i + 1];
      }

      if(args[i].equals("--csv-file")){
        csvFlag=true;
        csvFile = args[i + 1];
      }
    }

    String inputFilePath = "input\\";

    if(emailFlag&&!emailTemplateFlag){
      try {
        throw new Exception("--email provided but no --email-template was given.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if(letterFlag&&!letterTemplateFlag){
      try {
        throw new Exception("--letter provided but no --letter-template was given.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if(!letterFlag&&!emailFlag){
      try {
        throw new Exception("--letter not provided and --email not provided.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if(!outputDirFlag){
      try {
        throw new Exception("--output-dir not provided.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if(!csvFlag){
      try {
        throw new Exception("--csv-file not provided.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if(emailFlag&&emailTemplateFlag&&outputDirFlag&&csvFlag){
      readEmail(inputFilePath,emailTemplateFile,outputPath,csvFile);
    }

    if(letterFlag&&letterTemplateFlag&&outputDirFlag&&csvFlag){
      readLetter(inputFilePath,letterTemplateFile,outputPath,csvFile);
    }

  }

  /**
   * read the email
   * @param inputFilePath input File Path
   * @param emailTemplateFile email Template File
   * @param outputPath output Path
   * @param csvFile csv File
   * @throws IOException when invalid input
   */
  public static void readEmail(String inputFilePath, String emailTemplateFile,String outputPath,String csvFile) throws IOException{
    FileInputStream fin = new FileInputStream(inputFilePath+emailTemplateFile);
    InputStreamReader reader = new InputStreamReader(fin);
    BufferedReader buffReader = new BufferedReader(reader);
    String strTmp;
    StringBuilder content= new StringBuilder();
    while((strTmp = buffReader.readLine())!=null){
      //System.out.println(strTmp);
      content.append(strTmp).append("\r\n");
    }
    buffReader.close();

    //readCsv to get the information and sub-replace in the email-template to create the email, by using regular-expression

    //output(filepath) to get the output place
    //System.out.println(content);

    ArrayList<Map<String, String>> users =readCsv(inputFilePath,csvFile);

    writeEmail(outputPath, content.toString(),users);
  }

  /**
   * write email
   * @param outputPath output Path
   * @param content content
   * @param users users
   */
  public static void writeEmail(String outputPath,String content,ArrayList<Map<String, String>> users) {
    try{

      Path path = Paths.get(outputPath);

      Files.createDirectories(path);


      //System.out.println(users.get(0).get("\"email\""));

      //users.size()
      for(int i=0;i<users.size();i++){

        //regular expression to subchange content of email
        String newContent=content.replaceAll("\\[\\[email\\]\\]",users.get(i).get("\"email\""));
        newContent=newContent.replaceAll("\\[\\[first_name\\]\\]",users.get(i).get("\"first_name\""));
        newContent=newContent.replaceAll("\\[\\[last_name\\]\\]",users.get(i).get("\"last_name\""));
        newContent=newContent.replaceAll("\\[\\[company_name\\]\\]",users.get(i).get("\"company_name\""));
        newContent=newContent.replaceAll("\\[\\[address\\]\\]",users.get(i).get("\"address\""));
        newContent=newContent.replaceAll("\\[\\[city\\]\\]",users.get(i).get("\"city\""));
        newContent=newContent.replaceAll("\\[\\[county\\]\\]",users.get(i).get("\"county\""));
        newContent=newContent.replaceAll("\\[\\[state\\]\\]",users.get(i).get("\"state\""));
        newContent=newContent.replaceAll("\\[\\[zip\\]\\]",users.get(i).get("\"zip\""));
        newContent=newContent.replaceAll("\\[\\[phone1\\]\\]",users.get(i).get("\"phone1\""));
        newContent=newContent.replaceAll("\\[\\[phone2\\]\\]",users.get(i).get("\"phone2\""));
        newContent=newContent.replaceAll("\\[\\[web\\]\\]",users.get(i).get("\"web\""));


        File writename = new File(outputPath+"\\email"+(i+1)+".txt");
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(newContent);
        out.flush();
        out.close();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * read letter
   * @param inputFilePath input File Path
   * @param letterTemplateFile letter Template File
   * @param outputPath output Path
   * @param csvFile csv File
   * @throws IOException when invalid input
   */
  public static void readLetter(String inputFilePath, String letterTemplateFile,String outputPath,String csvFile) throws IOException{
    FileInputStream fin = new FileInputStream(inputFilePath+letterTemplateFile);
    InputStreamReader reader = new InputStreamReader(fin);
    BufferedReader buffReader = new BufferedReader(reader);
    String strTmp;
    StringBuilder content= new StringBuilder();
    while((strTmp = buffReader.readLine())!=null){
      //System.out.println(strTmp);
      content.append(strTmp).append("\r\n");
    }
    buffReader.close();

    //output(filepath) to get the output place
    //System.out.println(content);

    ArrayList<Map<String, String>> users =readCsv(inputFilePath,csvFile);

    writeLetter(outputPath, content.toString(),users);
  }

  /**
   * write letter
   * @param outputPath output Path
   * @param content content
   * @param users users
   */
  public static void writeLetter(String outputPath,String content,ArrayList<Map<String, String>> users) {
    try{

      Path path = Paths.get(outputPath);

      Files.createDirectories(path);


      //System.out.println(users.get(0).get("\"email\""));

      //users.size()
      for(int i=0;i<users.size();i++){

        //regular expression to subchange content of email
        String newContent=content.replaceAll("\\[\\[email\\]\\]",users.get(i).get("\"email\""));
        newContent=newContent.replaceAll("\\[\\[first_name\\]\\]",users.get(i).get("\"first_name\""));
        newContent=newContent.replaceAll("\\[\\[last_name\\]\\]",users.get(i).get("\"last_name\""));
        newContent=newContent.replaceAll("\\[\\[company_name\\]\\]",users.get(i).get("\"company_name\""));
        newContent=newContent.replaceAll("\\[\\[address\\]\\]",users.get(i).get("\"address\""));
        newContent=newContent.replaceAll("\\[\\[city\\]\\]",users.get(i).get("\"city\""));
        newContent=newContent.replaceAll("\\[\\[county\\]\\]",users.get(i).get("\"county\""));
        newContent=newContent.replaceAll("\\[\\[state\\]\\]",users.get(i).get("\"state\""));
        newContent=newContent.replaceAll("\\[\\[zip\\]\\]",users.get(i).get("\"zip\""));
        newContent=newContent.replaceAll("\\[\\[phone1\\]\\]",users.get(i).get("\"phone1\""));
        newContent=newContent.replaceAll("\\[\\[phone2\\]\\]",users.get(i).get("\"phone2\""));
        newContent=newContent.replaceAll("\\[\\[web\\]\\]",users.get(i).get("\"web\""));


        File writename = new File(outputPath+"\\letter"+(i+1)+".txt");
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(newContent);
        out.flush();
        out.close();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * read csv
   * @param inputFilePath input File Path
   * @param csvFile csv File
   * @return the arraylist of users
   * @throws IOException when invalid format file
   */
  public static ArrayList readCsv(String inputFilePath, String csvFile) throws IOException{

    ArrayList<Map<String, String>> users = new ArrayList<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(inputFilePath+csvFile));
      String header = reader.readLine();
      String[] headerSplit = header.split(",");

      //System.out.println(header);

      String line;
      //ArrayList<Map<String, String>> users = new ArrayList<>();
      while((line=reader.readLine())!=null){
        String[] item = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

        HashMap<String, String> user = new HashMap<>();

        for (int i = 0; i < item.length; i++) {
          item[i]=item[i].replaceAll("\"","");

          user.put(headerSplit[i], item[i]);

          //System.out.println(headerSplit[i]+" "+item[i]);
        }
        users.add(user);

        //System.out.println();

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return users;
  }

}
