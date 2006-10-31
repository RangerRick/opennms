package org.opennms.web.map;

/*
 * Created on 8-giu-2005
 *
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.web.map.view.*;

import java.text.SimpleDateFormat;

/**
 * @author mmigliore
 * 
 * this class provides to create, manage and delete 
 * proper session objects to use when working with maps
 * 
 */
public class OpenMapServlet extends HttpServlet {
	
	static final long serialVersionUID = 2006102300; 
	
	Category log;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedWriter bw = null;
		String strToSend=null;
		try {
			ThreadCategory.setPrefix(MapsConstants.LOG4J_CATEGORY);
			log = ThreadCategory.getInstance(this.getClass());
			bw = new BufferedWriter(new OutputStreamWriter(response
					.getOutputStream()));

			String action = request.getParameter("action");

			strToSend = action+"OK";
			HttpSession session = request.getSession(false);
			if (session != null) {
				String lastModTime = "";
				String createTime = "";
				float widthFactor = 1;
				float heightFactor =1;
				int mapId = Integer.parseInt(request.getParameter("MapId"));
				int mapWidth = Integer.parseInt(request
						.getParameter("MapWidth"));
				int mapHeight = Integer.parseInt(request
						.getParameter("MapHeight"));
				log.debug("Current mapWidth=" + mapWidth
						+ " and MapHeight=" + mapHeight);
				Manager m = new Manager();
				m.startSession();
				
				VMap map = null;
				
				if (action.equals(MapsConstants.OPENMAP_ACTION)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
					"HH.mm.ss dd/MM/yy");

					map = m.getMap(mapId);
					int oldMapWidth = map.getWidth();
					int oldMapHeight = map.getHeight();
					widthFactor = (float) mapWidth / oldMapWidth;
					heightFactor = (float) mapHeight / oldMapHeight;
					log.debug("Old saved mapWidth=" + oldMapWidth
							+ " and MapHeight=" + oldMapHeight);
					log.debug("widthFactor=" + widthFactor);
					log.debug("heightFactor=" + heightFactor);
					if (map.getCreateTime() != null)
						createTime = formatter.format(map.getCreateTime());
					if (map.getLastModifiedTime() != null)
						lastModTime = formatter.format(map
								.getLastModifiedTime());
					strToSend += map.getId() + "+" + map.getBackground();
				}else{
					strToSend=MapsConstants.OPENMAP_ACTION+"Failed";
				}
				
				if(map!=null){
					strToSend +="+" + map.getAccessMode() + "+"
						+ map.getName() + "+" + map.getOwner() + "+"
						+ map.getUserLastModifies() + "+" + createTime
						+ "+" + lastModTime;
					
					VElement[] elems = map.getAllElements();
					if (elems != null) {
						for (int i = 0; i < elems.length; i++) {
							int x = (int) (elems[i].getX() * widthFactor);
							int y = (int) (elems[i].getY() * heightFactor);
		
							strToSend += "&" + elems[i].getId()
									+ elems[i].getType() + "+" + x + "+"
									+ y + "+" + elems[i].getIcon() + "+"
									+ elems[i].getLabel();
							strToSend += "+" + elems[i].getRtc() + "+"
									+ elems[i].getStatus() + "+"
									+ elems[i].getSeverity();
						}
					}
					VLink[] links = map.getAllLinks();
					if (links != null) {
						for (int i = 0; i < links.length; i++) {
							strToSend += "&" + links[i].getFirst().getId()
									+ links[i].getFirst().getType() + "+"
									+ links[i].getSecond().getId()
									+ links[i].getSecond().getType();
						}
					}
				}

				m.endSession();
				
				session.setAttribute("sessionMap", map);
				log.info("Sending response to the client '" + strToSend
						+ "'");

			} else {
				strToSend=MapsConstants.OPENMAP_ACTION + "Failed";
				log.error("HttpSession not initialized");
			}
		} catch (Exception e) {
			strToSend=MapsConstants.OPENMAP_ACTION + "Failed";
			if (bw == null) {
				bw = new BufferedWriter(new OutputStreamWriter(response
						.getOutputStream()));
			}
			log.error(this.getClass().getName()+" Error: "+e);
		}finally{
			bw.write(strToSend);
			bw.close();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}