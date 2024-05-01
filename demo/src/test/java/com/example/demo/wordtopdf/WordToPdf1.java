//package com.example.demo.wordtopdf;
//
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.ComThread;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//
//import java.io.File;
//
//public class WordToPdf1 {
//    private static final int wdFormatPDF = 17;// PDF 格式
//
//
//    public static void wordToPDF(String startFile, String overFile){
//        ActiveXComponent app = null;
//        Dispatch doc = null;
//        try {
//            app = new ActiveXComponent("Word.Application");
//            app.setProperty("Visible", new Variant(false));
//            Dispatch docs = app.getProperty("Documents").toDispatch();
//            doc = Dispatch.call(docs,  "Open" , startFile).toDispatch();
//            File tofile = new File(overFile);
//            if (tofile.exists()) {
//                tofile.delete();
//            }
//            Dispatch.call(doc,"SaveAs", overFile, wdFormatPDF);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            Dispatch.call(doc,"Close",false);
//            if (app != null)
//                app.invoke("Quit", new Variant[] {});
//        }
//        //结束后关闭进程
//        ComThread.Release();
//    }
//
//    /**
//     * 根据传入文件将word转为pdf
//     * @param wordFile
//     * @return
//     */
//    public static File wordToPDFByFile(File wordFile){
//        File returnFile = null;
//        try {
//            String inputFilePath = wordFile.getAbsolutePath();
//            System.out.println("生成传入临时文件路径: "+inputFilePath);
//            String pdfFilePath = inputFilePath.substring(0,inputFilePath.lastIndexOf("."))+1 + "pdf";
//            System.out.println("生成pdf文件路径: "+pdfFilePath);
//            wordToPDF(inputFilePath, pdfFilePath);
//            returnFile = new File(pdfFilePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnFile;
//    }
//    public static void main(String[] args) {
//        String path = "D:\\demo\\测试数据\\1.doc";
//        String newPath = "D:\\demo\\测试数据\\1.pdf";
//        wordToPDF(path,newPath);
////      String path = "C://Users//zrc//Desktop//新建 DOCX 文档.docx";
////      File file = new File(path);
////      File pdf = wordToPDFByFile(file);
//    }
//
//
//}
