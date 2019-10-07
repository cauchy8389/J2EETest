package testspringcloud.cloud.poi;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Log4j2
public class ExcelRepBuilder {
    public static ResponseEntity<byte[]> entity(String fileName, byte[] bytes){
        HttpHeaders headers = new HttpHeaders();
        String downloadFileName = "";
        try {
            downloadFileName = URLEncoder.encode(fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            downloadFileName = fileName;
        }
        headers.add("Content-Disposition", "attchement;filename=" + downloadFileName);
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.add("Content-Type","application/octet-stream;charset=utf-8");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(bytes, headers, statusCode);
        return entity;
    }

    public static byte[] GuestTemplateBytes(String sheetName) {

        ByteArrayOutputStream baos = null;
        byte[] bytes =null;
        try{
            Workbook xssfWorkbook = WorkbookFactory.create(true);
            //HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            Sheet sheet  = xssfWorkbook.createSheet(sheetName);
            int basic = 2560;
            sheet.setColumnWidth(0,basic);
            sheet.setColumnWidth(1,basic*2);
            sheet.setColumnWidth(2,basic*3);
            sheet.setColumnWidth(3,basic*3);
            sheet.setColumnWidth(4,basic*3);
            sheet.setColumnWidth(5,basic*3);
            sheet.setColumnWidth(6,basic*3);
            sheet.setColumnWidth(7,basic*3);
            sheet.setColumnWidth(8,basic*5);
            Row headRow = sheet.createRow(0);
            String[] headArray = new String[]{"公司名称","社会信用代码","注册地址",
                    "联系方式","开户许可证","开户人姓名","开户银行","银行账号","登陆人手机号"};
            CellStyle cellStyle = xssfWorkbook.createCellStyle();
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            int cellIndex = 0;
            for(String value : headArray){
                Cell cell = headRow.createCell(cellIndex++);
                cell.setCellValue(value);
                cell.setCellStyle(cellStyle);
            }
            baos = new ByteArrayOutputStream();
            //IOUtils.copcop
            xssfWorkbook.write(baos);
            bytes =baos.toByteArray();
        }catch (Exception e){
            log.error("bomTemplate download error:",e);
            //throw e;
        }finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            }catch (Exception ex){

            }
        }
        return bytes;
    }
}
