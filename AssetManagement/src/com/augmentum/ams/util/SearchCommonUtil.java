package com.augmentum.ams.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.web.vo.asset.AssetListVo;

public class SearchCommonUtil {

    static Logger logger = Logger.getLogger(SearchCommonUtil.class);

    private static String getAssetListVoValueByProperty(AssetListVo assetListVo, String fieldName) {
        PropertyDescriptor pd = null;
        Object obj = null;
        try {
            pd = new PropertyDescriptor(fieldName, assetListVo.getClass());
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        }
        Method method = pd.getReadMethod();

        try {
            obj = method.invoke(assetListVo);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        }

        if (null == obj) {
            return "";
        } else {
            return obj.toString();
        }
    }

    public static JSONArray formatAssetVoListTOJSONArray(List<AssetListVo> assetVoList,
            List<UserCustomColumn> userCustomColumnList, String uuid) {
        JSONArray arrays = new JSONArray();

        for (AssetListVo assetListVo : assetVoList) {
            JSONArray array = new JSONArray();
            array.add(assetListVo.getId());

            for (UserCustomColumn userCustomColumn : userCustomColumnList) {
                String fieldName = userCustomColumn.getCustomizedColumn().getSortName();
                String value = "";
                if ("assetId".equals(fieldName)) {
                    if (!StringUtils.isBlank(uuid)) {
                        value = "<a href='asset/view/" + assetListVo.getId() + "?uuid=" + uuid
                                + "' style='color: #418FB5'>"
                                + getAssetListVoValueByProperty(assetListVo, fieldName) + "</a>";
                    } else {
                        value = "<a href='asset/view/" + assetListVo.getId()
                                + "' style='color: #418FB5'>"
                                + getAssetListVoValueByProperty(assetListVo, fieldName) + "</a>";
                    }
                } else {
                    value = getAssetListVoValueByProperty(assetListVo, fieldName);
                }
                array.add(value);
            }
            arrays.add(array);
        }
        return arrays;
    }

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 3, 2014 4:04:07 PM
     * @param assetVoList
     * @param userCustomColumnList
     * @return
     */
    public static JSONArray formatLocationListTOJSONArray(List<Location> locationList) {
        JSONArray arrays = new JSONArray();

        for (Location location : locationList) {
            JSONArray array = new JSONArray();
            array.add(location.getId());
            String value = "";
            value = location.getSite();
            array.add(value);
            value = location.getRoom();
            array.add(value);
            value = "<div class='editLocationIcon'></div>/<div class='deleteLocationIcon'></div>";
            array.add(value);
            arrays.add(array);
        }
        return arrays;
    }

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 3, 2014 4:04:07 PM
     * @param assetVoList
     * @param userCustomColumnList
     * @return
     */
    public static JSONArray formatGroupListTOJSONArray(List<CustomerGroup> groupList) {
        JSONArray arrays = new JSONArray();

        for (CustomerGroup group : groupList) {
            JSONArray array = new JSONArray();
            array.add(group.getId());
            String value = "";
            value = group.getGroupName();
            array.add(value);
            value = group.getDescription();
            array.add(value);
            value = group.getProcessType().toString();
            array.add(value);
            StringBuffer sb = new StringBuffer();
            List<Customer> list = group.getCustomers();
            for (Customer customer : list) {
                sb.append(customer.getCustomerName()).append(";");
            }
            array.add(sb.toString());

            value = "<div class='editGroupIcon'></div>/<div class='deleteGroupIcon'></div>";
            array.add(value);
            arrays.add(array);
        }
        return arrays;
    }

    public static JSONArray formatTransferLogListTOJSONArray(List<TransferLog> result,
            List<UserCustomColumn> userCustomColumnList, String clientTimeOffset) {

        JSONArray arrays = new JSONArray();

        for (TransferLog transferLog : result) {
            JSONArray array = new JSONArray();

            array.add(transferLog.getId());

            for (UserCustomColumn userCustomColumn : userCustomColumnList) {
                String fieldName = userCustomColumn.getCustomizedColumn().getSortName();
                String value = "";

                if ("time".equals(fieldName)) {
                    value = UTCTimeUtil.utcToLocalTime(transferLog.getTime(), clientTimeOffset,
                            SystemConstants.TIME_SECOND_PATTERN);
                } else if ("asset.checkInTime".equals(fieldName)) {
                    if (null == transferLog.getAsset().getCheckInTime()) {
                        value = "";
                    } else {
                        value = UTCTimeUtil.utcToLocalTime(transferLog.getAsset().getCheckInTime(),
                                clientTimeOffset, SystemConstants.DATE_DAY_PATTERN);
                    }
                } else {

                    try {
                        value = BeanUtils.getProperty(transferLog, fieldName);
                    } catch (IllegalAccessException e) {
                        throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                                "IllegalAccessException when get property from transferlog");
                    } catch (InvocationTargetException e) {
                        throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                                "InvocationTargetException when get property from transferlog");
                    } catch (NoSuchMethodException e) {
                        throw new SystemException(ErrorCodeUtil.SYSTEM_ERROR,
                                "NoSuchMethodException when get property from transferlog");
                    }
                }

                array.add(value);
            }
            arrays.add(array);
        }
        return arrays;
    }

    public static JSONArray formatReturnedAndReceivedAssetTOJSONArray(List<ToDo> todoList,
            List<UserCustomColumn> userCustomColumnList, String timeOffset) {
        JSONArray arrays = new JSONArray();

        for (ToDo todo : todoList) {

            JSONArray array = new JSONArray();
            array.add(todo.getId());

            for (UserCustomColumn column : userCustomColumnList) {

                String columnName = column.getCustomizedColumn().getSortName();
                String value = "";
                try {

                    Object obj = BeanUtils.getProperty(todo, columnName);

                    if (null == obj) {
                        value = "";
                    } else {
                        if ("asset.customer".equals(columnName)) {
                            value = BeanUtils.getProperty(todo, "asset.customer.customerName");
                        } else if ("asset.project".equals(columnName)) {
                            value = BeanUtils.getProperty(todo, "asset.project.projectName");
                        } else if ("asset.user".equals(columnName)) {
                            value = BeanUtils.getProperty(todo, "asset.user.userName");
                        } else if ("assigner".equals(columnName)) {
                            value = BeanUtils.getProperty(todo, "assigner.userName");
                        } else if ("returner".equals(columnName)) {
                            value = BeanUtils.getProperty(todo, "returner.userName");
                        } else if ("returnedTime".equals(columnName)
                                || "receivedTime".equals(columnName)) {
                            value = UTCTimeUtil.formatUTCStringToLocalString((String) obj,
                                    timeOffset, SystemConstants.TIME_SECOND_PATTERN);
                        } else {
                            value = (String) obj;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                            "IllegalAccessException when get property from todo asset");
                } catch (InvocationTargetException e) {
                    throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                            "InvocationTargetException when get property from todo asset");
                } catch (NoSuchMethodException e) {
                    throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                            "NoSuchMethodException when get property from todo asset");
                }
                array.add(value);
            }
            arrays.add(array);
        }
        return arrays;
    }

    public static JSONArray formatOperationLogListTOJSONArray(List<OperationLog> result,
            List<UserCustomColumn> userCustomColumnList, String clientTimeOffset) {

        JSONArray arrays = new JSONArray();

        for (OperationLog operationLog : result) {
            JSONArray array = new JSONArray();
            array.add(operationLog.getId());
            array.add(operationLog.getOperatorName());
            array.add(operationLog.getOperatorID());
            array.add(operationLog.getOperation());
            array.add(operationLog.getOperationObject());
            array.add(operationLog.getOperationObjectID());
            array.add(UTCTimeUtil.utcToLocalTime(operationLog.getUpdatedTime(), clientTimeOffset,
                    "yyyy-MM-dd HH:mm:ss"));

            arrays.add(array);
        }
        return arrays;

    }
}
