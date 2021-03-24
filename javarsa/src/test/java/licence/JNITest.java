package licence;

import com.sun.jna.Native;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class JNITest {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(JNITest.class);

    private static void loadNativeLibrary() {
//        String name = SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim();
//        if (!name.startsWith("linux")) {
//            throw new IllegalStateException("Only supported on Linux");
//        }
        String staticLibName = "rsautil";
        String sharedLibName = staticLibName + '_' + PlatformDependent.normalizedArch();
        ClassLoader cl = PlatformDependent.getClassLoader(Native.class);
        try {
            NativeLibraryLoader.load(sharedLibName, cl);
        } catch (UnsatisfiedLinkError e1) {
            try {
                NativeLibraryLoader.load(staticLibName, cl);
                logger.debug("Failed to load {}", sharedLibName, e1);
            } catch (UnsatisfiedLinkError e2) {
                ThrowableUtil.addSuppressed(e1, e2);
                throw e1;
            }
        }
    }

    public static void main(String[] args) {
//        String name = "/Users/tingfeng/work/java/click-licence/src/test/resources/rsautil.so";
        String name = "rsautil";
        loadNativeLibrary();
        LicenseParser licenseParser = Native.loadLibrary(name, LicenseParser.class);
        byte[] bytes = "hello world".getBytes();
        System.out.println(new String(licenseParser.GetValueByName(bytes, bytes.length)));
    }
}
