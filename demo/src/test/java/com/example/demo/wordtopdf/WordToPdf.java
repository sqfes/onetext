//package com.example.demo.wordtopdf;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import org.apache.commons.lang3.StringUtils;
//import org.docx4j.Docx4J;
//import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
//import org.docx4j.fonts.IdentityPlusMapper;
//import org.docx4j.fonts.Mapper;
//import org.docx4j.fonts.PhysicalFont;
//import org.docx4j.fonts.PhysicalFonts;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
//import org.junit.jupiter.api.Test;
//import org.apache.xalan.processor.TransformerFactoryImpl;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class WordToPdf {
//
//    @Test
//    public void test() {
//        try {
//            Long startTime = new Date().getTime();
//            InputStream is = new FileInputStream(new File("D:/demo/测试数据/1.doc"));
//            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
//                    .load(is);
//            Mapper fontMapper = new IdentityPlusMapper();
//            //加载字体文件（解决linux环境下无中文字体问题）
//            if(PhysicalFonts.get("SimSun") == null) {
//                System.out.println("加载本地SimSun字体库");
////	          PhysicalFonts.addPhysicalFonts("SimSun", WordUtils.class.getResource("/fonts/SIMSUN.TTC"));
//            }
//            fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
//            fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
//            fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
//            fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
//            fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
//            fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
//            fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
//            fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
//            fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
//            fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
//            fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
//            fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
//            fontMapper.put("等线", PhysicalFonts.get("SimSun"));
//            fontMapper.put("等线 Light", PhysicalFonts.get("SimSun"));
//            fontMapper.put("华文琥珀", PhysicalFonts.get("STHupo"));
//            fontMapper.put("华文隶书", PhysicalFonts.get("STLiti"));
//            fontMapper.put("华文新魏", PhysicalFonts.get("STXinwei"));
//            fontMapper.put("华文彩云", PhysicalFonts.get("STCaiyun"));
//            fontMapper.put("方正姚体", PhysicalFonts.get("FZYaoti"));
//            fontMapper.put("方正舒体", PhysicalFonts.get("FZShuTi"));
//            fontMapper.put("华文细黑", PhysicalFonts.get("STXihei"));
//            fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
//            fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
//            fontMapper.put("新細明體", PhysicalFonts.get("SimSun"));
//            //解决宋体（正文）和宋体（标题）的乱码问题
//            PhysicalFonts.put("PMingLiU", PhysicalFonts.get("SimSun"));
//            PhysicalFonts.put("新細明體", PhysicalFonts.get("SimSun"));
//            //宋体&新宋体
//            PhysicalFont simsunFont = PhysicalFonts.get("SimSun");
//            fontMapper.put("SimSun", simsunFont);
//            //设置字体
//            wordMLPackage.setFontMapper(fontMapper);
//            org.docx4j.convert.out.pdf.PdfConversion conversion = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
//                    wordMLPackage);
//            File file = new File("D:/demo/测试数据/1.pdf");
//            if(!file.exists()) {
//                file.createNewFile();
//            }
//            OutputStream out = new FileOutputStream(file);
//            conversion.output(out,new PdfSettings());
//            System.out.println("转换时间："+(new Date().getTime() - startTime)+" ms");
//        } catch (Exception e) {
//
//        }
//
//    }
//
//
//@Test
//    public void test1(){
//    try {
//        WordprocessingMLPackage pkg = Docx4J.load(new File("D:/demo/测试数据/1.docx"));
//        Mapper fontMapper = new IdentityPlusMapper();
////        fontMapper.put("隶书", PhysicalFonts.get("LiSu"));
////        fontMapper.put("宋体", PhysicalFonts.get("SimSun"));
////        fontMapper.put("微软雅黑", PhysicalFonts.get("Microsoft Yahei"));
////        fontMapper.put("黑体", PhysicalFonts.get("SimHei"));
////        fontMapper.put("楷体", PhysicalFonts.get("KaiTi"));
////        fontMapper.put("新宋体", PhysicalFonts.get("NSimSun"));
////        fontMapper.put("华文行楷", PhysicalFonts.get("STXingkai"));
////        fontMapper.put("华文仿宋", PhysicalFonts.get("STFangsong"));
////        fontMapper.put("仿宋", PhysicalFonts.get("FangSong"));
////        fontMapper.put("幼圆", PhysicalFonts.get("YouYuan"));
////        fontMapper.put("华文宋体", PhysicalFonts.get("STSong"));
////        fontMapper.put("华文中宋", PhysicalFonts.get("STZhongsong"));
////        fontMapper.put("等线", PhysicalFonts.get("SimSun"));
////        fontMapper.put("等线 Light", PhysicalFonts.get("SimSun"));
////        fontMapper.put("华文琥珀", PhysicalFonts.get("STHupo"));
////        fontMapper.put("华文隶书", PhysicalFonts.get("STLiti"));
////        fontMapper.put("华文新魏", PhysicalFonts.get("STXinwei"));
////        fontMapper.put("华文彩云", PhysicalFonts.get("STCaiyun"));
////        fontMapper.put("方正姚体", PhysicalFonts.get("FZYaoti"));
////        fontMapper.put("方正舒体", PhysicalFonts.get("FZShuTi"));
////        fontMapper.put("华文细黑", PhysicalFonts.get("STXihei"));
////        fontMapper.put("宋体扩展", PhysicalFonts.get("simsun-extB"));
////        fontMapper.put("仿宋_GB2312", PhysicalFonts.get("FangSong_GB2312"));
////        fontMapper.put("新細明體", PhysicalFonts.get("SimSun"));
//        pkg.setFontMapper(fontMapper);
//
//        Docx4J.toPDF(pkg, new FileOutputStream("D:/demo/测试数据/1.pdf"));
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (Docx4JException e) {
//        e.printStackTrace();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//}
//
//
//
