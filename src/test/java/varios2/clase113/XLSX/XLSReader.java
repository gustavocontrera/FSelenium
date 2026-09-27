package varios2.clase113.XLSX;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class XLSReader {


    public static void main(String arg[]) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("dataloader/sample.xlsx");
        XSSFSheet hoja1 = workbook.getSheet("Hoja1");

        for (int fila = 0; fila < 9; fila++){
            String persona = "";
            for (int col = 0; col < 8; col++){
                try {
                    persona += hoja1.getRow(fila).getCell(col).getStringCellValue() + ";";
                } catch (Exception ex) {
                    double num = hoja1.getRow(fila).getCell(col).getNumericCellValue();
                    persona += String.valueOf(num) + ";";
                }
            }

            System.out.println(persona);
        }
    }
}

