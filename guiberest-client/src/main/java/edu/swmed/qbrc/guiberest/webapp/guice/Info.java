package edu.swmed.qbrc.guiberest.webapp.guice;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.swmed.qbrc.guiberest.shared.rest.GuiberestRestService;

@Singleton
public class Info extends HttpServlet {
	
	@Inject
	GuiberestRestService guibRestService;

	private static final long serialVersionUID = -5697849610754976017L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		StringBuilder out = new StringBuilder();
		out.append("<p>");
		out.append("<h2>Print CAS Attributes and Test Fetching New Proxy Tickets</h2>");
		
		// Attributes:
	    if (req.getUserPrincipal() != null) {
	        AttributePrincipal principal = (AttributePrincipal) req.getUserPrincipal();
	        
	        final Map<String, Object> attributes = principal.getAttributes();
	        
	        if (attributes != null) {
	          Iterator<String> attributeNames = attributes.keySet().iterator();
	          out.append("<b>Attributes:</b>");
	          
	          if (attributeNames.hasNext()) {
	            out.append("<hr><table border='3pt' width='100%'>");
	            out.append("<th colspan='2'>Attributes</th>");
	            out.append("<tr><td><b>Key</b></td><td><b>Value</b></td></tr>");

	            for (; attributeNames.hasNext();) {
	              out.append("<tr><td>");
	              String attributeName = (String) attributeNames.next();
	              out.append(attributeName);
	              out.append("</td><td>");
	              Object attributeValue = attributes.get(attributeName);
	              out.append(attributeValue);
	              out.append("</td></tr>");
	            }
	            out.append("</table>");
	          } else {
	            out.append("No attributes are supplied by the CAS server.</p>");
	          }
	        } else {
	          out.append("<pre>The attribute map is empty. Review your CAS filter configurations.</pre>");
	        }
	      } else {
	          out.append("<pre>The user principal is empty from the request object. Review the wrapper filter configuration.</pre>");
	      }
		
	    
	    /* ====================================================================== */

	    out.append("<h3>Get PGT from Session, Method 1</h3>");
    	Assertion assertion1 = (Assertion) req.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
    	if (assertion1 != null) {
	    	out.append("<dl>");
	    		out.append("<dt>Principal:</dt>");
	    		out.append("<dd>" +  assertion1.getPrincipal().getName() + "</dd>");
		    	out.append("<dt>Valid from:</dt>");
		    	out.append("<dd>" + assertion1.getValidFromDate().toString() + "</dd>");
		    	out.append("<dt>Attributes:</dt>");
		    	out.append("<dd>");
		    		out.append("<dl>");
		    			Iterator<Map.Entry<String, Object>> it1 = assertion1.getAttributes().entrySet().iterator();
		    			while (it1.hasNext()) {
		    				Map.Entry<String, Object> entry = it1.next();
		    				out.append("<dt>"+entry.getKey()+"</dt>");
		    				out.append("<dd>"+entry.getValue()+"</dd>");
		    			}
		    		out.append("</dl>");
		    	out.append("</dd>");
		    out.append("</dl>");
    	}
    	else {
    		out.append("<p>Assertion assertion1 is NULL.</p>");
    	}
	    
	    /* ====================================================================== */
	    out.append("<h3>Get PGT from Session, Method 2</h3>");
    	Assertion assertion2 = AssertionHolder.getAssertion();
    	out.append("<dl>");
    		out.append("<dt>Principal:</dt>");
    		out.append("<dd>" + assertion2.getPrincipal().getName() + "</dd>");	
    		out.append("<dt>Valid from:</dt>");
    		out.append("<dd>" + assertion2.getValidFromDate().toString() + "</dd>");
	    	out.append("<dt>Attributes:</dt>");
    		out.append("<dd>");
    			out.append("<dl>");
	    			Iterator<Map.Entry<String, Object>> it2 = assertion2.getAttributes().entrySet().iterator();
	    			while (it2.hasNext()) {
	    				Map.Entry<String, Object> entry = it2.next();
	    				out.append("<dt>"+entry.getKey()+"</dt>");
	    				out.append("<dd>"+entry.getValue()+"</dd>");
	    			}
	    		out.append("</dl>");
	    	out.append("</dd>");
	    out.append("</dl>");

	    /* ====================================================================== */
	    out.append("<h3>Fetch new proxy ticket from CAS server</h3>");
	    	String targetService = "http://otherserver/legacy/service";
	    	String result1 = assertion1.getPrincipal().getProxyTicketFor(targetService);
	    	String result2 = assertion2.getPrincipal().getProxyTicketFor(targetService);
	    out.append("<dl>");
	    	out.append("<dt>Valid for service:</dt>");
	    	out.append("<dd>" + targetService + "</dd>");
	    	
	    	out.append("<dt>PT (from assertion 1):</dt>");
	    	out.append("<dd>" + result1 + "</dd>");
	    	
	    	out.append("<dt>PT (from assertion 2):</dt>");
	    	out.append("<dd>" + result2 + "</dd>");

	    out.append("</dl>");

	    /* ====================================================================== */
	    out.append("<h3>Fetch new proxy ticket from CAS server (Jon)</h3>");
	        AttributePrincipal principal = (AttributePrincipal) req.getUserPrincipal();
	    	String targetService2 = "http://otherserver/legacy/service";
	    	String result3 = principal.getProxyTicketFor(targetService);
	    	String result4 = principal.getProxyTicketFor(targetService);
	    out.append("<dl>");
	    	out.append("<dt>Valid for service:</dt>");
	    	out.append("<dd>" + targetService2 + "</dd>");
	    	
	    	out.append("<dt>PT (from assertion 1):</dt>");
	    	out.append("<dd>" + result3 + "</dd>");
	    	
	    	out.append("<dt>PT (from assertion 2):</dt>");
	    	out.append("<dd>" + result4 + "</dd>");

	    out.append("</dl>");
	    
		// Send to browser
		out.append("</p>");
		resp.getOutputStream().print(out.toString());
	}
	
}