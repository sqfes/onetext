//package com.example.demo;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.example.demo.mapper.UserMapper;
//import com.example.demo.pojo.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Base64;
//import java.util.List;
//
//@SpringBootTest
//public class WrapperTest {
//    @Autowired
//    private UserMapper userMapper;
//    //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
////SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE
//    //is_deleted=0 AND (username LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
//    static String base64Image ="";
//    @Test
//    public void test01() {
//
//        File imageFile = new File("D:\\demo\\测试数据\\数字签名\\抠图电子签\\周进-removebg-preview.jpg");
//        try {
//
//            byte[] imageBytes = readImageFile(imageFile);
//           base64Image = encodeImageToBase64(imageBytes);
//            System.out.println(base64Image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public void test02() {
//        // 读取txt文档中的内容
//        String base64Data = "";
//        try {
//            base64Data = new String(Files.readAllBytes(Paths.get("D:\\demo\\测试数据\\数字签名\\a.txt")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 将Base64编码的数据解码为字节数组
//        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
//
//        // 将字节数组写入到本地文件
//        try (FileOutputStream fos = new FileOutputStream(new File("D:\\demo\\测试数据\\数字签名\\tp\\image.png"))) {
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
//            ImageIO.write(image, "png", fos);
//            System.out.println("图片已成功保存到：" + "D:\\demo\\测试数据\\数字签名\\");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static byte[] readImageFile(File file) throws IOException {
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//            byte[] buffer = new byte[(int) file.length()];
//            fis.read(buffer);
//            return buffer;
//        } finally {
//            if (fis != null) {
//                fis.close();
//            }
//        }
//    }
//
//    private static String encodeImageToBase64(byte[] imageBytes) {
//        return Base64.getEncoder().encodeToString(imageBytes);
//    }
//
//    }
//
//
