package com.augmentum.ams.service.asset;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.service.BaseCaseTest;

public class AssetServiceImplTest extends BaseCaseTest {

    @Autowired
    private AssetService assetService;

    @Test
    public void findAssetsByIdsForExportTest(HttpServletRequest request) throws ExcelException,
            SQLException {

        String assetIds = "4028960f430345e70143034aade2000e";

        assetService.exportAssetsByIds(assetIds, request);
    }
}
