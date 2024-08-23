package com.projectharpseal.APIcall.service;

import com.projectharpseal.APIcall.Entity.Location;
import com.projectharpseal.APIcall.repository.LocationRepository;
import jakarta.annotation.PostConstruct;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Location_CSV {

    private final LocationRepository locationRepository;
    private static final Logger logger = LoggerFactory.getLogger(Location_CSV.class);

    public Location_CSV(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    //Todo
    // 나중에 Admin 계정으로 데이터를 업로드 할 수 있도록 바꾸기
    // 공격 취약점으로 사용될 수 있음.
    @PostConstruct
    public void init() {
        ZipSecureFile.setMinInflateRatio(0);
    }

    public void ExcelData(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        List<Location> locationList = new ArrayList<>();

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // 첫 번째 행은 헤더로 간주하고 건너뜁니다
            Row row = sheet.getRow(i);

            Location location = Location.builder()
                    .locationCity(parseStringCell(row.getCell(2))) // 1단계
                    .locationCountry(parseStringCell(row.getCell(3))) // 2단계
                    .locationTown(parseStringCell(row.getCell(4))) // 3단계
                    .locationX(parseDoubleCell(row.getCell(13))) // 경도(초/100)
                    .locationY(parseDoubleCell(row.getCell(14))) // 위도(초/100)
                    .build();

            locationList.add(location);
        }

        // 데이터베이스에 일괄 저장
        locationRepository.saveAll(locationList);

        workbook.close(); // 수동으로 리소스 해제
    }

    private String parseStringCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        else {
            return cell.getStringCellValue();}

    }

    private double parseDoubleCell(Cell cell) {

        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            String cellValue = cell.getStringCellValue();
            if (cellValue.isEmpty() || cellValue.equals("0")) {
                return 0.0;
            }
            return Double.parseDouble(cellValue);
        }
    }
}
