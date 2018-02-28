package com.rocoinfo.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 描述：任务上传 任务导入存入表中
 *
 * @author tony
 * @创建时间 2017-07-07 15:39
 */
@SuppressWarnings("all")
public class UploadTaskExcel {

    public static List<Map<String, String >> parserTaskExcel(MultipartFile file) throws Exception {
        List<Map<String, String >> list = new ArrayList<Map<String, String >> ();
        Map<String, String> map = null;

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        int rowTotal = sheet.getLastRowNum() > 0 ? sheet.getLastRowNum() : 0;

        // 如果 超过1000行则返回
        if (rowTotal > 1000) throw new Exception("行数太多了");

        for (int i = 1; i <= rowTotal; i++) {
            Row row = sheet.getRow(i);
            int colTotal = row.getLastCellNum();
            map = new HashMap<String, String>();
            for (int j = 0; j < colTotal; j++) {
                Cell cell = row.getCell(j);

                String tmpVal = "";
                if (cell != null && !"".equals(cell)) {
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        tmpVal = String.valueOf(cell.getRichStringCellValue());
                    }
                    else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        DecimalFormat df = new DecimalFormat("0");
                        tmpVal = df.format(cell.getNumericCellValue());
                    }

                    tmpVal = tmpVal.trim();
                }

                switch (j) {
                    case 0:
                        if (tmpVal == null || "".equals(StringUtils.trim(tmpVal))) {
                            throw new Exception("第" + i + "行的名字为空");
                        }
                        map.put("name", tmpVal);
                        break;
                    case 1:
                        if (tmpVal == null || "".equals(StringUtils.trim(tmpVal))) {
                            throw new Exception("第" + i + "行的手机号为空");
                        }

                        if (!Pattern.matches("^1[345678]\\d{9}$", tmpVal)) {
                            throw new Exception("第" + i + "行的手机号格式不正确");
                        }

                        map.put("mobile", tmpVal);
                        break;
                    case 2:
                        if (tmpVal == null || "".equals(StringUtils.trim(tmpVal))) {
                            throw new Exception("第" + i + "行的推广来源为空");
                        }
                        String[] ds = tmpVal.split("-");
                        if (ds.length != 2) {
                            throw new Exception("第" + i + "行的推广来源不正确");
                        }

                        map.put("dataSource", ds[0]);
                        list.add(map);
                }
            }
        }

        return list;
    }
}
