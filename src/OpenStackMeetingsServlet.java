import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OpenStackMeetingsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	JSoupHandler jsoupHandler;
	String history = "";
	LinkedList<String> History = new LinkedList<String>();

	public OpenStackMeetingsServlet() {
		if (jsoupHandler == null) {
			jsoupHandler = new JSoupHandler();
		}
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                    throws ServletException, IOException
    {
		PrintWriter w = response.getWriter();
		String project = request.getParameter("project");
		String year = request.getParameter("year");
		w.println("project: " + project);	
		w.println("year: " + year);
		try {		    
			String source = "http://eavesdrop.openstack.org/meetings/" + project + "/" + year ;	
			Elements links = jsoupHandler.getElements(source);
			
		    if (links != null) {
			    ListIterator<Element> iter = links.listIterator();
				w.println(history);
				w.println();
				w.println("Data");		    	
			    while(iter.hasNext()) {
		    			Element e = (Element) iter.next();
		    			String s = e.html();
		    			if ( s != null) {
		    				w.println(s);		    			
		    			}
			    }	
			    history += source + "\n";
		    }
		    else {
		    		w.println("Unknown project " + project);
		    		w.println(history);
					w.println();
					w.println("Data");	
		    }
		} catch (Exception exp) {
			exp.printStackTrace();
		}	
    }
	
	public void setJSoupHandler(JSoupHandler jsoupHandler) {
		this.jsoupHandler = jsoupHandler;
	}

}