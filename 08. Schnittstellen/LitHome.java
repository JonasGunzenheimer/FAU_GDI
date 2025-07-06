interface Switchable {
    void turnOn();

    void turnOff();

    boolean isOn();
}

// * HIER KÖNNT IHRE WERBUNG STEHEN 
public class LitHome {
    public static void main(String[] args) {
        SimpleLight simpleLight1 = new SimpleLight("55:12:CA:FE:BA:BE");
        SimpleLight simpleLight2 = new SimpleLight("55:12:CA:FE:00:42");

        Light light0 = new Light("192.168.7.50", (short) 80, "light/0");
        Light light1 = new Light("192.168.7.51", (short) 80, "light/1");
        Light light2 = new Light("192.168.7.51", (short) 81, "light/1");
        Light light3 = new Light("192.168.7.52", (short) 88, "light/0");
        Light light4 = new Light("192.168.7.52", (short) 88, "light/1");
        Light light5 = new Light("192.168.7.52", (short) 88, "light/2");

        Switch switch0 = new Switch(light0);

        Switch switch1 = new Switch(light1);
        switch1.linkLight(light2);

        Switch switch2 = new Switch(light3);
        switch2.linkLight(light4);
        switch2.linkLight(simpleLight1);

        Switch switch3 = new Switch(light5);
        switch3.linkLight(simpleLight2);

        light0.setHue(200);
        light1.setHue(100);
        light2.setHue(300);
        light3.setHue(350);
        light4.setHue(50);
    }
}

class Light implements Switchable {

    // * Attribute 
    private boolean isOn;
    private int hue;
    private double saturation;
    private double brightness;
    private final Adapter adapter;

    // * öffentlicher Parameter Konstruktor 
    public Light(String ip, short port, String endpoint) {
        this.adapter = new Adapter(ip, port, endpoint);

        // Initalzustand einstellen 
        this.hue = 270;
        setHue(this.hue);
        this.brightness = 0.8;
        setBrightness(this.brightness);
        this.saturation = 1.0;
        setSaturation(this.saturation);
        turnOff();
    }

    // * öffentliche Instanzmethoden zum Auslesen der Attribute (getter)
    @Override
    public boolean isOn() {
        return isOn;
    }

    public int getHue() {
        return hue;
    }

    public double getBrightness() {
        return brightness;
    }

    public double getSaturation() {
        return saturation;
    }

    // * setter 
    public void setHue(int hue) {
        this.hue = ((hue % 360) + 360) % 360;
        adapter.send("hue", this.hue);
    }

    public void setBrightness(double brightness) {
        if (brightness < 0.01) {
            this.brightness = 0.01;
        } else if (brightness > 1.0) {
            this.brightness = 1.0;
        } else {
            this.brightness = brightness;
        }
        adapter.send("brightness", this.brightness);
    }

    public void setSaturation(double saturation) {
        if (saturation < 0.0) {
            this.saturation = 0.0;
        } else if (saturation > 1.0) {
            this.saturation = 1.0;
        } else {
            this.saturation = saturation;
        }
        adapter.send("saturation", this.saturation);
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        adapter.send("on");
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        adapter.send("off");
    }
}

class Switch implements Switchable {
    private boolean isOn;
    private final Switchable[] lights = new Switchable[5];
    private final SimpleAdapter adapter;

    // * Nächste zuzuweisende ID als Klassenvariable 
    private static int nextId = 1;

    // * eigene Swtich-ID als Instanzvariable 
    private final int id;

    public Switch(Switchable light) {

        this.lights[0] = light; // * erstem Slot im Array zuweisen 

        // * SimpleAdapter-Instanz erzeugen 
        this.id = nextId;
        this.adapter = new SimpleAdapter(this.id);
        nextId++; // * nextId hochzählen, damit fortlaufend 

        Home.registerSwitch(this.id, this); // ! Objekt referenziert sich selbst 

        // * Schlater im "Aus"-Zustand initialisieren 
        isOn = false;
        turnOff();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void turnOn() {
        // * Zustand des Schalters anpassen 
        this.isOn = true;

        // * Netzwerk neuen Zustand mitteilen 
        this.adapter.send("on");

        // * mit dem Schaltern verubdene Lichter ausschalten
        for (Switchable light : lights) {
            if (light != null) { // ! Nullcheck falls light nicht existiert
                light.turnOn();
            }
        }
    }

    @Override
    public void turnOff() {
        // * Zustand des Schalters anpassen 
        this.isOn = false;

        // * Netzwerk neuen Zustand mitteilen 
        this.adapter.send("off");

        // * mit dem Schaltern verubdene Lichter ausschalten
        for (Switchable light : lights) {
            if (light != null) { // ! Nullcheck falls light nicht existiert
                light.turnOff();
            }
        }
    }

    public boolean linkLight(Switchable light) {
        for (int i = 0; i < lights.length; i++) {
            if (lights[i] == null) {
                lights[i] = light;

                if (this.isOn) {
                    light.turnOn();
                } else {
                    light.turnOff();
                }
                return true;
            }
        }
        return false;
    }

}

class SimpleLight implements Switchable {

    private final LightAdapter lightadapter;
    private final boolean isOn;

    @Override
    public void turnOn() {
        this.lightadapter.send(true);
    }

    @Override
    public void turnOff() {
        this.lightadapter.send(false);
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    public SimpleLight(String macAdresse) {
        this.lightadapter = new LightAdapter(macAdresse);
        this.isOn = false;
        turnOff();
    }
}