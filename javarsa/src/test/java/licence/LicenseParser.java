package licence;

import com.sun.jna.Library;

public interface LicenseParser extends Library {
    String GetValueByName(byte[] name, int size);
}
