//package com.example.demo.wordtopdf;
//
//import com.itextpdf.text.Paragraph;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.common.PDRectangle;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.docx4j.openpackaging.exceptions.Docx4JException;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
//import org.docx4j.wml.Document;
//import org.docx4j.wml.Text;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class utileswordtopdf {
//    public static byte[] convert(String wordFilePath) throws IOException, Docx4JException {
//        // 读取Word文档
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new FileInputStream(wordFilePath));
//
//        Document document = wordMLPackage.getMainDocumentPart().getJaxbElement();
//        List<Paragraph> paragraphs= document.getBody().getContent().stream()
//                .filter(obj -> obj instanceof Paragraph)
//                .map(obj -> (Paragraph) obj)
//                .collect(Collectors.toList());
//
//        // 创建PDF文档
//        PDDocument pdfDocument = new PDDocument();
//        PDPage page = new PDPage(PDRectangle.A4);
//        pdfDocument.addPage(page);
//        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);
//
//        // 设置字体
//        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//
//        // 遍历Word文档的段落并将其添加到PDF文档中
//        for (Paragraph paragraph : paragraphs) {
//            List<Object> texts = Collections.singletonList(paragraph.getContent());
//            for (Object text : texts) {
//                if (text instanceof Text) {
//                    contentStream.beginText();
//                    contentStream.newLineAtOffset(100, 700);
//                    contentStream.showText(((Text) text).getValue());
//                    contentStream.endText();
//                }
//            }
//        }
//
//
//        // 关闭内容流并保存PDF文档
//        contentStream.close();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        pdfDocument.save(outputStream);
//        pdfDocument.close();
//
//        return outputStream.toByteArray();
//    }
//}
