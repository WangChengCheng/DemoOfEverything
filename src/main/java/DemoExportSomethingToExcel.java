import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.net.URI;

/**
 * @author wangchengcheng
 * created at 2018-08-17 17:43
 */

public class DemoExportSomethingToExcel {

    private static Logger LOG = LoggerFactory.getLogger(DemoExportSomethingToExcel.class);

    public static void main(String[] args) throws Exception{
        //源文件路径
        URI inputFilePath = DemoExportSomethingToExcel.class.getResource("/File4DemoExportSomethingToExcel.txt").toURI();
        File inputFile = new File(inputFilePath);
        //创建Excel文件
        String xlsFilename = "ExcelExportDemo.xls";
        File xlsFile = new File(xlsFilename);

        WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
        WritableSheet sheet = workbook.createSheet("Sheet1", 0);//创建名为Sheet1的工作表
        WritableCellFormat wcf = new WritableCellFormat();//定义单元格格式
        wcf.setAlignment(Alignment.CENTRE);//单元格内容水平居中
        wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//单元格内容垂直居中

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile));
            BufferedReader bfr = new BufferedReader(reader);
            /*
            示例文件标题（单元格合并）
            NAME  TEL  AGE
            Ann  157****6706  18
            Bob  188****6666  19
            Cow  174****5555  16
             **/

            //第一行和第二行
            String title = null;
            String attr = null;
            title = bfr.readLine();
            attr = bfr.readLine();

            int currRow = 0;//当前正在写入的行
            //title行占3列、2行，且内容水平居中垂直居中
            if (null != title) {
                sheet.addCell(new Label(0, currRow, title, wcf));//new Label(col, row, cont, format)
                sheet.mergeCells(0, currRow, 2, currRow + 1);
            }
            currRow += 2;//换到下一行
            //NAME  TEL  AGE
            String[] attrs = attr.split("  ");
            for (int i = 0; i < attrs.length; ++i) {
                sheet.addCell(new Label(i, currRow, attrs[i]));
            }
            currRow += 1;
            //表格的3行内容
            String cont = bfr.readLine();
            while (null != cont) {
                String[] conts = cont.split("  ");
                for (int i = 0; i < conts.length; ++i) {
                    sheet.addCell(new Label(i, currRow, conts[i]));
                }
                currRow += 1;
                cont = bfr.readLine();
            }
            //写入数据
            workbook.write();
            //关闭文件
            workbook.close();
        } catch (FileNotFoundException e) {
            LOG.error("Can't find the file ../resources/File4DemoExportSomethingToExcel.txt, please check the resources directory.");
            e.printStackTrace();
        }

    }

}
