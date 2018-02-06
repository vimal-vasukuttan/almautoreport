package gen.rep.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import gen.rep.model.Defect;

/**
 * Service class for excel related implementations
 * @author Vimal V
 *
 */
@Service
public class CreateExcelService {
	
	private static final String FILE_NAME = "locationtofile/Defects.xlsx";
	
	
	
	public void createSheet(List<Defect> defects) {

		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Defects");

        int rowNum = 0;

        for (Defect defect : defects) {
            Row row = sheet.createRow(rowNum++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(defect.getId());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(defect.getOwner());
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(defect.getPriority());
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(defect.getSeverity());
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(defect.getName());
            Cell cell5 = row.createCell(5);
            cell5.setCellValue(defect.getStatus());
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }		
		
	}

}
