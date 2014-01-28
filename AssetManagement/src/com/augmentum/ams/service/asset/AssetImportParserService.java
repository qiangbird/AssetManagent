package com.augmentum.ams.service.asset;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.web.vo.asset.ImportVo;


public interface AssetImportParserService {

    /**
     * @author John.Li
     * @param file
     * @param request
     * @param flag
     * @return
     * @throws ExcelException
     * @throws DataException
     */
    ImportVo importAsset(File file, HttpServletRequest request, String flag)
            throws ExcelException, DataException;
    
}
