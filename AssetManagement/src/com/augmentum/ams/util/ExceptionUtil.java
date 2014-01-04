package com.augmentum.ams.util;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.augmentum.ams.exception.AuthorityException;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ParameterException;

/**
 * Used to do some common operations when your handle the exceptions.
 * 
 */
public final class ExceptionUtil {

	private static Logger logger = Logger.getLogger(ExceptionUtil.class);

	private static final String ERROR_CODE_MESSAGE = "errorCodeMessage.xml";

	private static Element rootElement = null;

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param erroCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @throws DataException
	 */
	public static void dataException(String errorCode, String... errroKeys) throws DataException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new DataException(errorCode,errorMessage);
	}

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param errorCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @param e
	 * @throws DataException
	 */
	public static void dataException(Exception e, String errorCode, String... errroKeys)
	        throws DataException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new DataException(e, errorCode,errorMessage);
	}

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param erroCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @throws ParameterException
	 */
	public static void parameterException(String errorCode, String... errroKeys) throws ParameterException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new ParameterException(errorCode,errorMessage);
	}

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param errorCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @param e
	 * @throws ParameterException
	 */
	public static void parameterException(Exception e, String errorCode, String... errroKeys)
	        throws ParameterException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new ParameterException(e, errorCode,errorMessage);

	}

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param erroCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @throws AuthorityException
	 */
	public static void authorityException(String errorCode, String... errroKeys) throws AuthorityException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new AuthorityException(errorCode,errorMessage);
	}

	/**
	 * @param errroKeys
	 *            which causes exception mainly keyword, it can not exist if
	 *            errroKeys keyword does not exist
	 * @param errorCode
	 *            which is defined in @ErrorCodeUtil
	 * @param logger
	 * @param e
	 * @throws AuthorityException
	 */
	public static void authorityException(Exception e, String errorCode, String... errroKeys)
	        throws AuthorityException {
		String errorMessage = processErrorMessageByErrorCode(errorCode, errroKeys);
		throw new AuthorityException(e, errorCode,errorMessage);

	}

	/**
	 * @description user errorCode to locate error message, then replace error
	 *              placeHolder use errorKeys
	 * @author Rudy.Gao
	 * @time Sep 27, 2013 4:48:57 PM
	 * @param errorCode
	 *            which is defined in @ErrorCodeUtil
	 * @param errorKeys
	 *            which is key words of error message which will be used to
	 *            replace error placeHolder
	 * @return String
	 */
	public static String processErrorMessageByErrorCode(String errorCode, String... errorKeys) {

		// If rootElement is null, then reload errorCodeMessage.xml info
		if (rootElement == null) {
			readXML();
		}
		// Use errorCode to load error message from errorCodeMessage.xml
		String errorMessage = rootElement.selectSingleNode("//*[@id='" + errorCode + "']//errorMessage").getText();

		// Use errorKey to replace placeHolder in errorMessage
		for (int i = 0; i < errorKeys.length; i++) {
			errorMessage = errorMessage.replaceAll("%placeHolder" + i + "%", errorKeys[i]);
		}

		return errorMessage;
	}

	/**
	 * @description load error message info from errorCodeMessage.xml
	 * @author Rudy.Gao
	 * @time Sep 27, 2013 4:48:51 PM
	 * @return Element
	 */
	private static Element readXML() {
		InputStream inputStream = ExceptionUtil.class.getClassLoader().getResourceAsStream(ERROR_CODE_MESSAGE);
		SAXReader reader = new SAXReader();
		Document document = null;

		try {
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			String message = "Can not read document, document file:" + ERROR_CODE_MESSAGE;
			logger.error(message, e);
		}
		rootElement = document.getRootElement();
		return rootElement;
	}

	private ExceptionUtil() {

	}

}
