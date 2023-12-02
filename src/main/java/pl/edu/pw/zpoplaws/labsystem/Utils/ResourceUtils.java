package pl.edu.pw.zpoplaws.labsystem.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {

    private ResourceUtils(){

    }

    public  static InputStream getResourceAsStream(final  String name) {
        return  ResourceUtils.class.getClassLoader().getResourceAsStream(name);
    }

    public static byte[] getResourceAsByteArray(final String name){
        try(final var stream = getResourceAsStream(name)) {
            return stream.readAllBytes();
        } catch (final IOException e ){
            throw new IllegalStateException("Failed to read resource: " + name, e);
        }
    }

    public static String getResourceAsString(final String name, final Charset charset) {
        return new String(getResourceAsByteArray(name), charset);
    }

    public static String getResourceAsString(final String name) {
        return getResourceAsString(name, StandardCharsets.UTF_8);
    }
}
