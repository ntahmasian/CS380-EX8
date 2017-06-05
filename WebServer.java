/**
 * Created by Narvik on 6/2/17.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer{

    public static void main(String [] args)throws Exception{

        try(ServerSocket serverSocket = new ServerSocket(8080)){

            System.out.println("Connected");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Runnable server = () -> {
                        try{

                            InputStream is = socket.getInputStream();
                            OutputStream os = socket.getOutputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            PrintWriter pw = new PrintWriter(os, true);
                            String str = br.readLine();
                            String webSite = "www"+str.split(" ")[1];

                            if(webSite.equals("www/hello.html")){
                                File f = new File("www/hello.html");
                                BufferedReader fbr = new BufferedReader(new FileReader(f));

                                pw.print("HTTP/1.1 200 OK\n" +
                                        "Content-type: text/html\n" +
                                        "Content-length: 124\r\n\r\n");
                                System.out.println("HTTP/1.1 200 OK\n" +
                                        "Content-type: text/html\n" +
                                        "Content-length: 124");
                                System.out.println();
                                str = fbr.readLine();

                                while(str!= null){
                                    pw.print(str+"\r\n");
                                    str = fbr.readLine();
                                }

                                pw.flush();
                                pw.close();
                                fbr.close();
                            }
                            else{
                                File f = new File("www/NotFound.html");
                                BufferedReader fbr = new BufferedReader(new FileReader(f));

                                pw.print("HTTP/1.1 404 Not Found\n" +
                                        "Content-type: text/html\n" +
                                        "Content-length: 126\r\n\r\n");
                                System.out.println("HTTP/1.1 404 Not Found\n" +
                                        "Content-type: text/html\n" +
                                        "Content-length: 126");
                                System.out.println();
                                str = fbr.readLine();

                                while(str!= null){
                                    pw.print(str+"\r\n");
                                    str = fbr.readLine();
                                }

                                pw.flush();
                                pw.close();
                                fbr.close();
                            }
                        }
                        catch(Exception e){ }
                    };
                    Thread thread = new Thread(server);
                    thread.start();
                }
                catch(Exception e){
                    System.out.println("Something is wrong with the Socket!");
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}