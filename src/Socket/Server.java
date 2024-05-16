import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đang chạy và lắng nghe cổng " + PORT);

            while (true) {
                // Chấp nhận kết nối từ client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối.");

                // Mở luồng vào/ra với client
                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                // Luồng nhập từ bàn phím
                BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(System.in));

                String messageFromClient;
                String messageFromServer;

                while (true) {
                    // Đọc dữ liệu từ client
                    messageFromClient = inputFromClient.readLine();
                    if (messageFromClient.equals("bye")) {
                        break;
                    }
                    System.out.println("Client: " + messageFromClient);

                    // Kiểm tra nếu là yêu cầu "time"
                    if (messageFromClient.equals("time")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        String currentTime = dateFormat.format(new Date());
                        outputToClient.println("Thời gian hiện tại: " + currentTime);
                    } else {
                        // Nhập dữ liệu từ bàn phím và gửi tới client
                        System.out.print("Server: ");
                        messageFromServer = inputFromServer.readLine();
                        outputToClient.println(messageFromServer);
                    }
                }

                // Đóng kết nối với client
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
