package licence;

import com.bizsaas.arch.rsa.RsaUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;

public class RsaUtilTest {
    public static void main(String[] args) throws Exception {
        String publicKey = FileUtils.readFileToString(new File("./rsa_public_key.pem"), Charset.forName("UTF-8"));
        String privateKey = FileUtils.readFileToString(new File("./rsa_private_pkcs8.pem"), Charset.forName("UTF-8"));
        privateKey = StringUtils.replace(privateKey, "-----BEGIN PRIVATE KEY-----", "");
        privateKey = StringUtils.replace(privateKey, "-----END PRIVATE KEY-----", "");

        publicKey = StringUtils.replace(publicKey, "-----BEGIN PUBLIC KEY-----", "");
        publicKey = StringUtils.replace(publicKey, "-----END PUBLIC KEY-----", "");

        RsaUtil rsa = new RsaUtil(publicKey, privateKey);
        {
            System.out.println("1-----------------");

            String data = "hello world";
            String encrypted = rsa.publicEncrypt(data);
            System.out.println(encrypted);
            String decrypted = rsa.privateDecrypt(encrypted);
            System.out.println(decrypted);
        }
        {
            System.out.println("2-----------------");
            String data = "hello world2";
            String encrypted = rsa.privateEncrypt(data);
            System.out.println(encrypted);
            String decrypted = rsa.publicDecrypt(encrypted);
            System.out.println(decrypted);
        }
        {
            System.out.println("3-----------------");

            String data = "WW2V-9yebnZcq0kifM1fPF7OOlAjv8_1IRV6Okijx8mUTrRUhPYzRXoSA6ulm1xV_Q5bFiEYx05XLNvF6RxvhIfc7BTz4zAebnkJ5kJC3pJi9Ggsk8hXQJ5Puf6Ncr0aqVuiGYciOlX-QzqI3JMu84lT5UIOVMtO0IxCrKxINX6g1RttTk7IzwNxouBEvJsQRqjXhGlO0a8r3QlsmAMXZUDYJsRzswQ577qbM_SIWdm28jnzCu5PMObrWNV00LJNzqDSDlLZyv56GZgNaxQcr7HHudP-bg0E_ys-DC5o9vC9643DiGKH1Ecm3ZSPLeart0OSW8kg_WQxDri2EcPM5w";
            String s = rsa.privateDecrypt(data);
            System.out.println(s);
        }
        {
            System.out.println("4-----------------");

            String data = "k8745q73StFv/w8JPhWlMQ8rZKfY4rlCUaNU/HhM5xkxdR++EjEEOc/eQc2WZ3m3pfEDrU76+7nom4qG0b4kwVn/fECk6awENScLis0t8kxoAaDJEzEpx+ZlK9UZYElYvo4lZry/9JCGgho7dczvwbLQPQry57jU8Ph/7rQ4vk+HEks5DeoCeH0Ix9TBlXW1rTSpem9iNZD73vg1h1yQ0KbRlFmihDyD2ujEuDSf9pkRIVDSpu+fs2/TNdA24pnzC3u/wpOL74uUvqwFpLK9Jue8C1oyezOBObfc0bZMARCm6VTli+RzjORUUI1xJShlxJcqVp5y7X0YZzUaqxKMlA==";
            String s = rsa.publicDecrypt(data);
            System.out.println(s);
        }
    }
}
