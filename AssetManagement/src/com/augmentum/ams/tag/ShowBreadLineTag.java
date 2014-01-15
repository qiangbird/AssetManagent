package com.augmentum.ams.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

/**
 * Define the performance of the breadline tag
 * @author John.Li
 * @time 2014-01-10
 *
 */
public class ShowBreadLineTag extends BodyTagSupport{

    private static final long serialVersionUID = 4396973854432182189L;

    private Logger logger = Logger.getLogger(ShowBreadLineTag.class);
    //The property of the breadline tag
    private String href;
    
    private String style;

    public void setHref(String href) {
        this.href = href;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {

        logger.info("doStartTag start...");

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String basePath = (String) request.getAttribute("basePath");
        JspWriter out = pageContext.getOut();
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("<a ");
        if (null != href) {
            stringBuffer.append("href='" + basePath + href + "' ");
        }
        if (null != style) {
            stringBuffer.append("class='" + style + "' ");
        }
        stringBuffer.append(">");
        
        try {
            out.print(stringBuffer.toString());
        } catch (IOException e) {
            logger.error("IO Error in doStartTag() " + e.getMessage());
            throw new JspTagException("IO Error:" + e.getMessage());
        }
        logger.info("doStartTag end...");

        return EVAL_PAGE;
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        
        logger.info("doEndTag() start...");
        
        JspWriter out = pageContext.getOut();
        StringBuffer stringBuffer = new StringBuffer();
        
        try{
            if(null != bodyContent){
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            }
            stringBuffer.append("</a>");
            out.print(stringBuffer.toString());
        } catch (IOException e){
            logger.error("IO Error in doEndTag() " + e.getMessage());
            //TODO throw a checked exception
            //throw new SystemException("");
        }
        logger.info("doEndTag() end...");
        
        return EVAL_BODY_INCLUDE;
    }
    
    public int doAfterBody() throws JspException {
        
        logger.info("doAfterBody()...");
        return EVAL_BODY_INCLUDE;
    }
}
