package com.example.t5.excel.importexcel;

import com.example.t5.data.Units;
import com.example.t5.entities.ProductEntity;
import com.example.t5.entities.SourceEntity;
import com.example.t5.entities.SourceInProductEntity;
import com.example.t5.util.Helper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelWorker {

    public static void main(String[] args) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setDeteCreate(new Date());
        List<SourceInProductEntity> listsource = new ArrayList<SourceInProductEntity>();
        SourceInProductEntity source1 = new SourceInProductEntity();
        source1.setCount(265.2);
        SourceEntity se1 = new SourceEntity();
        se1.setName("Source 1");
        se1.setCode("0001");
        se1.setUnits(Units.TN);
        source1.setSource(se1);
        listsource.add(source1);
        productEntity.setSourceList(listsource);
//        createXLS(productEntity);
    }


    public static boolean createXLS(ProductEntity productEntity, String dir) {
        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Лист 1");
        // создаем шрифт
        HSSFFont font = workbook.createFont();
        // указываем, что хотим его видеть жирным

        HSSFFont font6 = workbook.createFont();
        font6.setFontHeight((short) (6 * 20));
        HSSFFont font9bold = workbook.createFont();
        font9bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font9bold.setFontHeight((short) (9 * 20));
        HSSFFont font9 = workbook.createFont();
        font9.setFontHeight((short) (9 * 20));
        HSSFFont font8 = workbook.createFont();
        font8.setFontHeight((short) (8 * 20));
        // счетчик для строк
        int rowNum = 0;
        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        for (int i = 1; i < 13; i++) {
            row.createCell(i);
        }
        sheet.setColumnWidth(0, 270);
        sheet.setColumnWidth(1, 2100);
        sheet.setColumnWidth(2, 2100);
        sheet.setColumnWidth(3, 3 * 2050);
        sheet.setColumnWidth(4, 3050);
        sheet.setColumnWidth(5, 3050);
        sheet.setColumnWidth(6, 3050);
        sheet.setColumnWidth(7, 3050);
        sheet.setColumnWidth(8, 3050);
        sheet.setColumnWidth(9, 3050);
        sheet.setColumnWidth(10, 3050);
        sheet.setColumnWidth(11, 3050);
        sheet.setColumnWidth(12, 3050);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));
        row.getCell(9).setCellValue("Типовая межотраслевая форма № М-11\n\r" +
                "Утверждена постановлением Госкомстата России от 30.10.97 № 71а");
        CellStyle rightStyleSmol = workbook.createCellStyle();
        CellStyle styleAlignCenterBold = workbook.createCellStyle();
        CellStyle styleAlignCenter9 = workbook.createCellStyle();
        CellStyle styleBorderButtom9 = workbook.createCellStyle();
        CellStyle style9 = workbook.createCellStyle();
        style9.setFont(font9);
        CellStyle styleBorderCenter8Wrap = workbook.createCellStyle();
        styleBorderCenter8Wrap.setFont(font8);
        styleBorderCenter8Wrap.setWrapText(true);
        styleBorderCenter8Wrap.setAlignment(CellStyle.ALIGN_CENTER);
        setBorderInStyle(styleBorderCenter8Wrap);
        styleBorderButtom9.setBorderBottom(CellStyle.BORDER_THIN);
        styleBorderButtom9.setFont(font9);
        styleAlignCenter9.setAlignment(CellStyle.ALIGN_CENTER);
        styleAlignCenter9.setFont(font9);
        rightStyleSmol.setAlignment(CellStyle.ALIGN_RIGHT);
        styleAlignCenterBold.setAlignment(CellStyle.ALIGN_CENTER);
        styleAlignCenterBold.setFont(font9bold);

        rightStyleSmol.setFont(font6);
        rightStyleSmol.setWrapText(true);
        row.getCell(9).setCellStyle(rightStyleSmol);
        Row row2 = sheet.createRow(1);
        for (int i = 1; i < 13; i++) {
            row2.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 9));
        row2.getCell(1).setCellValue("ТРЕБОВАНИЕ-НАКЛАДНАЯ № ");
        row2.getCell(1).setCellStyle(styleAlignCenterBold);
        row2.getCell(12).setCellValue("Коды");
        CellStyle styleBorderCenter9 = workbook.createCellStyle();
        styleBorderCenter9.setAlignment(CellStyle.ALIGN_CENTER);
        styleBorderCenter9.setFont(font9);
        setBorderInStyle(styleBorderCenter9);
        row2.getCell(12).setCellStyle(styleBorderCenter9);
        Row row3 = sheet.createRow(2);
        for (int i = 1; i < 13; i++) {
            row3.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 10, 11));
        row3.getCell(10).setCellValue("Форма по ОКУД");
        row3.getCell(10).setCellStyle(styleAlignCenter9);

        row3.getCell(12).setCellValue(315006);
        row3.getCell(12).setCellStyle(styleBorderCenter9);

        Row row4 = sheet.createRow(3);
        for (int i = 1; i < 13; i++) {
            row4.createCell(i);
        }
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 9));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 11));

        row4.getCell(1).setCellValue("ООО \"КРЫМТЕХКАРКАС\"");
        for (int i = 1; i < 10; i++) {
            row4.getCell(i).setCellStyle(styleBorderButtom9);
        }
        row4.getCell(10).setCellValue("по ОКПО");
        row4.getCell(10).setCellStyle(styleAlignCenter9);
        row4.getCell(12).setCellStyle(styleBorderCenter9);

        Row row5 = sheet.createRow(4);
        for (int i = 1; i < 13; i++) {
            row5.createCell(i);
        }
        Row row6 = sheet.createRow(5);
        for (int i = 1; i < 13; i++) {
            row6.createCell(i);
        }
        Row row7 = sheet.createRow(6);
        for (int i = 1; i < 13; i++) {
            row7.createCell(i);
        }
        Row row8 = sheet.createRow(7);
        for (int i = 1; i < 13; i++) {
            row8.createCell(i);
        }
        Row row9 = sheet.createRow(8);
        for (int i = 1; i < 13; i++) {
            row9.createCell(i);
        }
        Row row10 = sheet.createRow(9);
        for (int i = 1; i < 13; i++) {
            row10.createCell(i);
        }
        Row row11 = sheet.createRow(10);
        for (int i = 1; i < 13; i++) {
            row11.createCell(i);
        }
        Row row12 = sheet.createRow(11);
        for (int i = 1; i < 13; i++) {
            row12.createCell(i);
        }
        Row row13 = sheet.createRow(12);
        for (int i = 1; i < 13; i++) {
            row13.createCell(i);
        }
        Row row14 = sheet.createRow(13);
        for (int i = 1; i < 13; i++) {
            row14.createCell(i);
        }
        Row row15 = sheet.createRow(14);
        for (int i = 1; i < 13; i++) {
            row15.createCell(i);
        }
        row.setHeight((short) 400);
        row6.setHeight((short) 500);
        row13.setHeight((short) 500);
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 1, 1));//дата составления
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));//код вида операции
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 5)); //отправитель
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 5)); //вид деятельности
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 5)); // пусто под верхним
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 9));//получатель
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 7));//структурное подразделение
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 7));//администрация
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 9));//вид деятельности
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 8, 9));//пусто под верхним
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 10, 11));//корреспондирующий счет
        sheet.addMergedRegion(new CellRangeAddress(5, 6, 12, 12));//учетная еденица выпуска
        sheet.addMergedRegion(new CellRangeAddress(12, 12, 1, 2));//кореспонд счет
        sheet.addMergedRegion(new CellRangeAddress(12, 12, 3, 5));//мат ценности
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 3, 4));//наименование
        sheet.addMergedRegion(new CellRangeAddress(12, 12, 6, 7));//еденица изм
        sheet.addMergedRegion(new CellRangeAddress(12, 12, 8, 9));//количество
        sheet.addMergedRegion(new CellRangeAddress(12, 13, 10, 10));//цена руб
        sheet.addMergedRegion(new CellRangeAddress(12, 13, 11, 11));//сумма без ндс
        sheet.addMergedRegion(new CellRangeAddress(12, 13, 12, 12));//порядковый номер по складской картотеке

        for (int i = 1; i < 13; i++) {
            row6.getCell(i).setCellStyle(styleBorderCenter8Wrap);
        }
        for (int i = 1; i < 13; i++) {
            row7.getCell(i).setCellStyle(styleBorderCenter8Wrap);
        }
        for (int i = 1; i < 13; i++) {
            row8.getCell(i).setCellStyle(styleBorderCenter8Wrap);
        }


        row6.getCell(1).setCellValue("Дата\n" + "состав-\n" + "ления");
        row6.getCell(2).setCellValue("Код вида\n" + "операции");
        row6.getCell(3).setCellValue("Отправитель");
        row6.getCell(6).setCellValue("Получатель");
        row6.getCell(10).setCellValue("Корреспондирующий\n" + "счет");
        row6.getCell(12).setCellValue("Учетная\n" + "единица\n" + "выпуска\n" + "продукции\n" + "(работ,\n" + "услуг)");

        row7.getCell(3).setCellValue("структурное\n" + "подразделение");
        row7.getCell(4).setCellValue("вид\n" + "деятельности");
        row7.getCell(6).setCellValue("структурное\n" + "подразделение");
        row7.getCell(8).setCellValue("вид\n" + "деятельности");
        row7.getCell(10).setCellValue("счет, субсчет");
        row7.getCell(11).setCellValue("код аналити- ческого учета");

        row8.getCell(1).setCellValue(Helper.getStringFromData(productEntity.getDeteCreate()));
        row8.getCell(3).setCellValue("Основной склад");
        row8.getCell(6).setCellValue("Администрация");
        row8.getCell(6).setCellValue("20.01");
        row10.getCell(1).setCellStyle(style9);
        row11.getCell(1).setCellStyle(style9);
        row11.getCell(6).setCellStyle(style9);
        row10.getCell(1).setCellValue("Через кого________________________________________________________________________________________");
        row11.getCell(1).setCellValue("Затребовал___________________________________");
        row11.getCell(6).setCellValue("Разрешил___________________________________________");

        for (int i = 1; i < 13; i++) {
            row13.getCell(i).setCellStyle(styleBorderCenter8Wrap);
        }
        for (int i = 1; i < 13; i++) {
            row14.getCell(i).setCellStyle(styleBorderCenter8Wrap);
        }
        for (int i = 1; i < 13; i++) {
            row15.getCell(i).setCellStyle(styleBorderCenter8Wrap);
            row15.getCell(i).setCellValue(i);
        }

        row13.getCell(1).setCellValue("Корреспондирующий\n" + "счет");
        row13.getCell(3).setCellValue("Материальные ценности");
        row13.getCell(6).setCellValue("Единица\n" + "измерения");
        row13.getCell(8).setCellValue("Количество");
        row13.getCell(10).setCellValue("Цена,\n" + "руб. коп.");
        row13.getCell(11).setCellValue("Сумма без\n" + "учета НДС,\n" + "руб. коп.");
        row13.getCell(12).setCellValue("Порядковый номер по складской картотеке");
        row14.getCell(1).setCellValue("счет, субсчет");
        row14.getCell(2).setCellValue("код аналити- ческого учета");
        row14.getCell(3).setCellValue("наименование");
        row14.getCell(5).setCellValue("номенк-\n" + "латурный номер");
        row14.getCell(6).setCellValue("код");
        row14.getCell(7).setCellValue("наиме-\n" + "нование");
        row14.getCell(8).setCellValue("затре-\n" + "бова-\n" + "но");
        row14.getCell(9).setCellValue("отпу-\n" + "щено");
        int count = 15;
        for (SourceInProductEntity sipe : productEntity.getSourceList()) {
            Row rowX = sheet.createRow(count);
            for (int i = 1; i < 13; i++) {
                rowX.createCell(i);
                rowX.getCell(i).setCellStyle(styleBorderCenter8Wrap);
            }
            rowX.getCell(1).setCellValue("10.01");
            sheet.addMergedRegion(new CellRangeAddress(count, count, 3, 4));
            rowX.getCell(3).setCellValue(sipe.getSource().getName());
            rowX.getCell(5).setCellValue(sipe.getSource().getCode());
            rowX.getCell(7).setCellValue(Helper.UnitsToString(sipe.getUnits()));
            rowX.getCell(6).setCellValue(Helper.getCodeFromUnits(sipe.getUnits()));
            rowX.getCell(8).setCellValue(sipe.getCount());
            rowX.getCell(9).setCellValue(sipe.getCount());
            count++;

        }

        // записываем созданный в памяти Excel документ в файл
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(dir+ "Акт " + productEntity.getName()
                    + " для " + productEntity.getBuyerEntity().getNameOrganization()
                    + " от " + Helper.getStringFromData(productEntity.getDateShipment()) + ".xls"));
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
           return false;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                //NOP
            }
        }


        return true;
    }



    public static void setBorderInStyle(CellStyle styleBorder) {
        styleBorder.setBorderBottom(CellStyle.BORDER_THIN);
        styleBorder.setBorderTop(CellStyle.BORDER_THIN);
        styleBorder.setBorderLeft(CellStyle.BORDER_THIN);
        styleBorder.setBorderRight(CellStyle.BORDER_THIN);
    }

}