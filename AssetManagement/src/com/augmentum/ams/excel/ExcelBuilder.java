package com.augmentum.ams.excel;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;


/**
 * Build excel document using the data array. The first row of the data array is
 * title.
 * 
 * @author john.li
 */
public class ExcelBuilder {
    public WritableWorkbook createWritableWorkbookByFileName(String fileName) throws ExcelException {
        try {
            return Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            throw new ExcelException("Cannot create excel file: " + fileName);
        }
    }

    public static Workbook getWorkbookByTemplate(String fileName) throws ExcelException {
        try {
            return Workbook.getWorkbook(new File(fileName));
        } catch (Exception e) {
            throw new ExcelException(e, "Cannot create workbook from file: " + fileName);
        }
    }

    public static WritableWorkbook createWritableWorkbook(String fileName, Workbook in) throws ExcelException {
        try {
            return Workbook.createWorkbook(new File(fileName), in);
        } catch (IOException e) {
            throw new ExcelException("Cannot create excel file by workbook: " + fileName);
        }
    }

    public static void closeWritableWorkbook(WritableWorkbook writableWorkbook) throws ExcelException {
        try {
            writableWorkbook.write();
            writableWorkbook.close();
        } catch (Exception e) {
            throw new ExcelException("Exception when closing WritableWorkbook: " + e);
        }
    }

    public static void mergeCells(int startC, int startR, int endC, int endR, WritableSheet writableSheet)
            throws ExcelException {
        try {
            writableSheet.mergeCells(startC, startR, endC, endR);
        } catch (Exception e) {
            throw new ExcelException(e, "Cannot merge: start [row, column] : [" + startR + ", " + startC + "]"
                    + " and end [row, column] : [" + endR + ", " + endC + "]");
        }
    }

    /**
     * Generate excel parser by giving entity.
     * 
     * @param list
     * @param entityType
     * @return
     * @throws ExcelException
     */
    public static ExcelParser getParser(Class<?> entity) throws ExcelException {
        if (Asset.class.equals(entity)) {
            return new AssetTemplateParser();
        } /*else if (Offer.class.equals(entity)) {
            return new OfferTemplateParser();
        } else if (RecruitingPlan.class.equals(entity)) {
            return new RecruitingPlanTemplateParser();
        } else if (Job.class.equals(entity)) {
            return new JobTemplateParser();
        } else if (Candidate.class.equals(entity)) {
            return new CandidateTemplateParser();
        } else if (RecruitingProgress.class.equals(entity) || CandidateRecruitingProgress.class.equals(entity)) {
            return new RecruitingProgressTemplateParser();
        } else if (SalaryInvestigation.class.equals(entity)) {
            return new SalaryInvestigationTemplateParser();
        } else if (Interviewer.class.equals(entity)) {
            return new InterviewerTemplateParser();
        } else if (RecruitingChannel.class.equals(entity)) {
            return new RecruitingChannelTemplateParser();
        }*/ else {
            throw new ExcelException("Unknown entity type when generate excel parser: " + entity);
        }
    }
}
