package vinhnq.listener;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.LogManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class MapListener implements ServletContextListener {
    private final org.apache.log4j.Logger logger = LogManager.getLogger(MapListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Map<String, String> listMap = new HashMap<>();
        String realPath = context.getRealPath("/") + "WEB-INF\\mapping.txt";
        File file = new File(realPath);
        FileReader fileReader = null;
        BufferedReader buffer = null;
        try {
            fileReader = new FileReader(file);
            buffer = new BufferedReader(fileReader);
            String lineDetail;
            while ((lineDetail = buffer.readLine()) != null) {
                StringTokenizer strToken = new StringTokenizer(lineDetail, "=");
                String key = strToken.nextToken();
                String value = strToken.nextToken();
                listMap.put(key, value);
            }
        } 
        catch (FileNotFoundException e) {
            logger.error("\nMapListener Main FileNotFoundException " + e.getMessage());
        } 
        catch (IOException e) {
            logger.error("\nMapListener Main IOException " + e.getMessage());
        } 
        finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    logger.error("\nMapListener BufferClose FileNotFoundException " + e.getMessage());
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    logger.error("\nMapListener FileReaderClose FileNotFoundException " + e.getMessage());
                }
            }
        }
        context.setAttribute("MAP", listMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("MAP");
    }
}
