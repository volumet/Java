/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Admin
 */
public class ConvertInputStreamToByteArrayHelpers {
    public static byte[] convertISToByteArr(InputStream input) throws IOException {
        byte[] result;
        try (ByteArrayOutputStream bo = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4000];
            int len;
            while ((len = input.read(buffer)) != -1) {
                bo.write(buffer, 0, len);
            }   
            result = bo.toByteArray();
            bo.close();
        }
        
        return result;
    }
}
