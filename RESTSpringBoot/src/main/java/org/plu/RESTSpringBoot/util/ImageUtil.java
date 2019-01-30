package org.plu.RESTSpringBoot.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.UUID;

public class ImageUtil {

    public static String saveImage(MultipartFile file) throws IOException {
        String path = "C:\\instagramPhotos\\" + String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        File convertFile = new File(path);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        return path;
    }

    public static byte[] getFile(String fileName) {
        if (fileName == null) {
            return null;
        }
        try {
            // Retrieve image from the classpath.
            InputStream is = new BufferedInputStream(new FileInputStream(fileName));

            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
