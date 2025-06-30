public class NetworkedHome {
    public static void main(String[] args) {
        Manager.RUN_TESTS = false; 
        Light licht1 = new Light("192.168.7.50", (short)80, "light/0"); 

    }
}

class Light {

    // * Attribute 
    private boolean isOn; 
    private int hue; 
    private double saturation; 
    private double brightness;
    
    // * öffentlicher Parameter Konstruktor 
    public Light(String ip, short port, String endpoint) {
        Adapter adapter = new Adapter(ip, port, endpoint); 
    }
}