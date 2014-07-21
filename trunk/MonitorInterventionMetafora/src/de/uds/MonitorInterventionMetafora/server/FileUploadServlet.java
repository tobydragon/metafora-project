package de.uds.MonitorInterventionMetafora.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import de.uds.MonitorInterventionMetafora.server.utils.GeneralUtil;

/* tdragon 6/21/2014
 * Server side of simple file upload, mostly taken from:
 * http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/FileUpload.html
 * and
 * http://www.jroller.com/hasant/entry/fileupload_with_gwt
 */

public class FileUploadServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = GeneralUtil.getRealPath("upload");

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException {
       super.doGet(req, resp);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException {
       
       // process only multipart requests
       if (ServletFileUpload.isMultipartContent(req)) {

           // Create a factory for disk-based file items
           FileItemFactory factory = new DiskFileItemFactory();

           // Create a new file upload handler
           ServletFileUpload upload = new ServletFileUpload(factory);

           // Parse the request
           try {
               List<FileItem> items = upload.parseRequest(req);
               for (FileItem item : items) {
                   // process only file upload - discard other form item types
                   if (item.isFormField()) continue;
                   
                   String fileName = item.getName();
                   // get only the file name not whole path
                   if (fileName != null) {
                       fileName = FilenameUtils.getName(fileName);
                   }

                   File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
                   if (uploadedFile.createNewFile()) {
                       item.write(uploadedFile);
                       resp.setStatus(HttpServletResponse.SC_CREATED);
                       resp.getWriter().print(uploadedFile.getName());
                       resp.flushBuffer();
                   } else
                       throw new IOException("The file already exists in repository.");
               }
           } catch (Exception e) {
               resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                       "An error occurred while creating the file : " + e.getMessage());
           }

       } else {
           resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                            "Request contents type is not supported by the servlet.");
        }
    }
}