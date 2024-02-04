package teko.ch.zigbee.baseApi;

import java.util.List;

public class LightState {
    private State state;
    private SwUpdate swupdate;
    private String type;
    private String name;
    private String modelid;
    private String manufacturername;
    private String productname;
    private Capabilities capabilities;
    private Config config;
    private String uniqueid;
    private String swversion;
    private String swconfigid;
    private String productid;

    public static class State {
        private boolean on;
        private int bri;
        private int hue;
        private int sat;
        private String effect;
        private List<Double> xy;
        private int ct;
        private String alert;
        private String colormode;
        private String mode;
        private boolean reachable;
        // Getters and setters for State fields...

        public boolean isOn() {
            return on;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public int getBri() {
            return bri;
        }

        public void setBri(int bri) {
            this.bri = bri;
        }

        public int getHue() {
            return hue;
        }

        public void setHue(int hue) {
            this.hue = hue;
        }

        public int getSat() {
            return sat;
        }

        public void setSat(int sat) {
            this.sat = sat;
        }

        public String getEffect() {
            return effect;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public List<Double> getXy() {
            return xy;
        }

        public void setXy(List<Double> xy) {
            this.xy = xy;
        }

        public int getCt() {
            return ct;
        }

        public void setCt(int ct) {
            this.ct = ct;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public String getColormode() {
            return colormode;
        }

        public void setColormode(String colormode) {
            this.colormode = colormode;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public boolean isReachable() {
            return reachable;
        }

        public void setReachable(boolean reachable) {
            this.reachable = reachable;
        }
    }

    public static class SwUpdate {
        private String state;
        private String lastinstall;
        // Getters and setters for SwUpdate fields...

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLastinstall() {
            return lastinstall;
        }

        public void setLastinstall(String lastinstall) {
            this.lastinstall = lastinstall;
        }
    }

    public static class Capabilities {
        private boolean certified;
        private Control control;
        private Streaming streaming;
        // Getters and setters for Capabilities fields...

        public boolean isCertified() {
            return certified;
        }

        public void setCertified(boolean certified) {
            this.certified = certified;
        }

        public Control getControl() {
            return control;
        }

        public void setControl(Control control) {
            this.control = control;
        }

        public Streaming getStreaming() {
            return streaming;
        }

        public void setStreaming(Streaming streaming) {
            this.streaming = streaming;
        }
    }

    public static class Control {
        private int mindimlevel;
        private int maxlumen;
        private String colorgamuttype;
        private List<List<Double>> colorgamut;
        private Ct ct;
        // Getters and setters for Control fields...

        public int getMindimlevel() {
            return mindimlevel;
        }

        public void setMindimlevel(int mindimlevel) {
            this.mindimlevel = mindimlevel;
        }

        public int getMaxlumen() {
            return maxlumen;
        }

        public void setMaxlumen(int maxlumen) {
            this.maxlumen = maxlumen;
        }

        public String getColorgamuttype() {
            return colorgamuttype;
        }

        public void setColorgamuttype(String colorgamuttype) {
            this.colorgamuttype = colorgamuttype;
        }

        public List<List<Double>> getColorgamut() {
            return colorgamut;
        }

        public void setColorgamut(List<List<Double>> colorgamut) {
            this.colorgamut = colorgamut;
        }

        public Ct getCt() {
            return ct;
        }

        public void setCt(Ct ct) {
            this.ct = ct;
        }
    }

    public static class Ct {
        private int min;
        private int max;
        // Getters and setters for Ct fields...

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }

    public static class Streaming {
        private boolean renderer;
        private boolean proxy;
        // Getters and setters for Streaming fields...

        public boolean isRenderer() {
            return renderer;
        }

        public void setRenderer(boolean renderer) {
            this.renderer = renderer;
        }

        public boolean isProxy() {
            return proxy;
        }

        public void setProxy(boolean proxy) {
            this.proxy = proxy;
        }
    }

    public static class Config {
        private String archetype;
        private String function;
        private String direction;
        private Startup startup;
        // Getters and setters for Config fields...

        public String getArchetype() {
            return archetype;
        }

        public void setArchetype(String archetype) {
            this.archetype = archetype;
        }

        public String getFunction() {
            return function;
        }

        public void setFunction(String function) {
            this.function = function;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public Startup getStartup() {
            return startup;
        }

        public void setStartup(Startup startup) {
            this.startup = startup;
        }
    }

    public static class Startup {
        private String mode;
        private boolean configured;
        private CustomSettings customsettings;
        // Getters and setters for Startup fields...

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public boolean isConfigured() {
            return configured;
        }

        public void setConfigured(boolean configured) {
            this.configured = configured;
        }

        public CustomSettings getCustomsettings() {
            return customsettings;
        }

        public void setCustomsettings(CustomSettings customsettings) {
            this.customsettings = customsettings;
        }
    }

    public static class CustomSettings {
        private int bri;
        private List<Double> xy;
        // Getters and setters for CustomSettings fields...

        public int getBri() {
            return bri;
        }

        public void setBri(int bri) {
            this.bri = bri;
        }

        public List<Double> getXy() {
            return xy;
        }

        public void setXy(List<Double> xy) {
            this.xy = xy;
        }
    }

    // Getters and setters for LightState fields...

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public SwUpdate getSwupdate() {
        return swupdate;
    }

    public void setSwupdate(SwUpdate swupdate) {
        this.swupdate = swupdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    public String getManufacturername() {
        return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getSwversion() {
        return swversion;
    }

    public void setSwversion(String swversion) {
        this.swversion = swversion;
    }

    public String getSwconfigid() {
        return swconfigid;
    }

    public void setSwconfigid(String swconfigid) {
        this.swconfigid = swconfigid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }
}
