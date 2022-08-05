package GE.GestoreEventi;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import spark.Spark;


class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        Eventi eventi = new Eventi();
        
      

        // SPARKJAVA SERVER
        port(8080);
        
        //CORS FILTERS
        Spark.options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        //END CORS FILTERS

        // GET //http://localhost:8080/eventi/listaEventi
        get("/eventi/listaEventi", (request, response) -> {
        	new Jdbc().insertLog();
            return new Gson().toJson(eventi.ListaEventiString());

        });

        // PUT-- //http://localhost:8080/eventi/prenota/Pausini/10
        put("/eventi/prenota/:nome/:posti", (request, response) -> {
        	new Jdbc().insertLog();
            String nome = request.params("nome");
            String posti = request.params("posti");
            response.status(200);
            return new Gson().toJson(eventi.Prenota(nome, Integer.parseInt(posti)));
        });
        
        // END SPARKJAVA SERVER

        // SERVERSOCKET
        try {
            server = new ServerSocket(1456);
            server.setReuseAddress(true);
            System.out.println("Server connected");

            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected " + client.getInetAddress().getHostAddress());
                new Jdbc().insertLog();
                ClientHandler clientHandler = new ClientHandler(client, eventi);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                    threadPool.shutdown();
                    Spark.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public Eventi eventi;
        String nome = null;
        int posti = 0;

        public ClientHandler(Socket socket, Eventi eventi) {
            this.clientSocket = socket;
            this.eventi = eventi;
        }

        private String RecuperaNome(BufferedReader in) throws IOException {
            String l1 = null;
            while (l1 == null) {
                l1 = in.readLine();
            }
            return l1;
        }

        private int RecuperaPosti(BufferedReader in) throws IOException {
            String l2 = null;
            while (l2 == null) {
                l2 = in.readLine();
            }
            return Integer.parseInt(l2);
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientSocket.setSoTimeout(60000);
                String line = null;

                while (true) {

                    try {
                        while (line == null) {
                            line = in.readLine();
                            if (line != null) {
                                clientSocket.setSoTimeout(60000);
                                break;
                            }
                        }
                    } catch (SocketTimeoutException e) {
                        System.out.println("Tempo di sessione chiuso, Client inattivo");
                        clientSocket.close();
                    }

                    if ("ListaEventi".equalsIgnoreCase(line)) {
                        out.println("ListaEventi: " + eventi.ListaEventi());
                        out.flush();
                    }

                    if ("Prenota".equalsIgnoreCase(line)) {
                        this.nome = RecuperaNome(in);
                        this.posti = RecuperaPosti(in);

                        if (eventi.Prenota(nome, posti).equals("OK")) {
                            out.println("ListaEventi: " + eventi.ListaEventi());
                            out.flush();
                        } else
                            out.println(eventi.Prenota(nome, posti));
                        out.flush();

                    }

                    if ("Crea".equalsIgnoreCase(line)) {
                        this.nome = RecuperaNome(in);
                        this.posti = RecuperaPosti(in);

                        try {
                            eventi.Crea(nome, posti);
                        } catch (IllegalArgumentException e) {
                            out.println(e);
                            out.flush();
                        }
                        out.println("ListaEventi: " + eventi.ListaEventi());
                        out.flush();
                    }

                    if ("Aggiungi".equalsIgnoreCase(line)) {
                        this.nome = RecuperaNome(in);
                        this.posti = RecuperaPosti(in);

                        try {
                            eventi.Aggiungi(nome, posti);
                        } catch (IllegalArgumentException e) {
                            out.println(e);
                            out.flush();
                        }
                        out.println("ListaEventi: " + eventi.ListaEventi());
                        out.flush();

                    }

                    if ("Chiudi".equalsIgnoreCase(line)) {
                        this.nome = RecuperaNome(in);

                        try {
                            eventi.Chiudi(nome);
                        } catch (IllegalArgumentException e) {
                            out.println(e);
                            out.flush();
                        }
                        out.println("ListaEventi: " + eventi.ListaEventi());
                        out.flush();

                    }

                    line = null;
                }
            } catch (SocketException s) {
                System.out.println("Closing client: " + clientSocket.getInetAddress().getHostAddress());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
}
