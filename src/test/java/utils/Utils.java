package utils;

import org.apache.commons.configuration.PropertiesConfiguration;

import javax.naming.ConfigurationException;

public class Utils {
    public static void setEnvVar(String key, String value) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }
    public static int RandomNum(int min, int max){
        double random=Math.random()*(max-min)+min;
        return (int)random;
    }

    public static void main(String[] args) {
        System.out.println(RandomNum(1000,5000));
    }
}
