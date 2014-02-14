package com.augmentum.ams.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;

import com.augmentum.ams.exception.RemoteException;
import com.augmentum.iaphelper.constans.RequestConstants;
import com.augmentum.iaphelper.exception.NoAppNameException;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;
import com.augmentum.iaphelper.util.IAPDataModelRequestUtil;
import com.augmentum.iaphelper.util.IAPDataModelResponseUtil;
import com.augmentum.iaphelper.util.RequestHelper;

/**
 * Contains the methods to encapsulate some required information into request in
 * order to communicating with IAP. It also contains the methods gains required
 * information from the request.
 * 
 * @author Rudy.Gao
 * @time Sep 12, 2013 1:08:10 PM
 */
public final class RemoteUtil {

	private RemoteUtil() {
		// Noting to do
	}

	private static Logger logger = Logger.getLogger(RemoteUtil.class);

	public static final String PROJECT_NAME = RequestConstants.APP_NAME;

	/**
	 * @description Gain the url contains the ticket and the address of the API
	 *              from IAP or other APP, or the address to send an event.
	 * 
	 * @param httpServletRequest
	 *            contains the ticket. Key is the the key of the API or Event in
	 *            properties you want to use. Object is the other parameters you
	 *            want to transport when calling a certain API. Response should
	 *            be IAPResponseType.APPLICATION_XML or
	 *            IAPResponseType.APPLICATION_JSON. The former one means the
	 *            response from IAP would be XML format, the later one means the
	 *            response from IAP would be JSON format. RoleName is the role
	 *            of the user who attempts to communicate with IAP.
	 * 
	 * @param key
	 * @param roleName
	 * @param responseType
	 * @return The request used to communicate with IAP.
	 * @author Rudy.Gao
	 * @throws DataException
	 * @time Sep 12, 2013 1:10:43 PM
	 */
	public static Request getRequest(HttpServletRequest httpServletRequest,
			String key, String roleName, final String responseType,
			Object... other) {

		// Gain the url with the ticket
		String ticketUrl = RequestHelper.getTicketURLByKey(httpServletRequest,
				key, other);

		Request request = null;
		try {
			// Gain the request that used to communicate with IAP
			request = RequestHelper.getRequest(ticketUrl, key, roleName,
					responseType);

		} catch (NoAppNameException e) {
		    throw new RemoteException(ErrorCodeUtil.DATA_IAP_0204001, "Error from getting data from IAP!");
		}
		return request;
	}

	/**
	 * @description Get the response from IAP according to the model. Invoke
	 *              this method after you configured the search model in the
	 *              service level.
	 * 
	 * @param Model
	 *            is the instance of IAPDataSearchModel contains the conditions
	 *            to get the response from IAP. RequestType is the type of the
	 *            request("xml" or "json"). Request is used for sending the
	 *            information to IAP when communicating with IAP.
	 * @param requestType
	 * @param request
	 * @return IAPDataResponseModel
	 * @author Rudy.Gao
	 * @throws DataException
	 * @time Sep 12, 2013 1:09:29 PM
	 */
	public static IAPDataResponseModel getResponse(IAPDataSearchModel model,
			final ContentType requestType, Request request){
		String requestBody = IAPDataModelRequestUtil.buildSearchString(
				requestType, model);

		// If you use JOSN, requestType should be ContentType.APPLICATION_JSON
		// else ContentType.APPLICATION_XML;
		String responseBody = null;
        try {
            // Get response from IAP
            responseBody = RequestHelper.sendRequest(request, requestBody,
                    requestType);
        } catch (ClientProtocolException e) {
            throw new RemoteException(ErrorCodeUtil.DATA_IAP_0204001, "Error from getting data from IAP!");
        } catch (IOException e) {
            throw new RemoteException(ErrorCodeUtil.DATA_IAP_0204001, "Error from getting data from IAP!");
        }
        IAPDataResponseModel responseModel = IAPDataModelResponseUtil.getResponseModel(responseBody);
        
        if (responseModel.getStatus().getStatusCode() != 200 && 
                responseModel.getStatus().getStatusCode() != 201) {
            throw new RemoteException(ErrorCodeUtil.DATA_IAP_0204001, "Error from getting data from IAP!");
        }
        
        if (responseModel.getStatus().getStatusCode() == 201) {
            logger.info("Get empty set from IAP!");
        }
        
        return responseModel;
	}
}
