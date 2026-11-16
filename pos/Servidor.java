public class Servidor {

    String hostname;
    String ip;
    int porta;

    public Servidor(String hostname, String ip, int porta) {
        this.hostname = hostname;
        this.ip = ip;
        this.porta = porta;
    }

    public String getHostname() {
        return hostname;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public void conectar() {
        int porta = 22;
        String ip = "192.168.1.10";
        String hostname = "SRV-LINUX";
        System.out.println("Conectando ao servidor " + ip);
        System.out.println("Hostname: " + hostname);
        System.out.println("Porta: " + porta); 
    }
}
