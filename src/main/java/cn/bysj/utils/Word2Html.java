package cn.bysj.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem; 

public class Word2Html {

	/*
	 * doc文件转HTML
	 * */
	public static String Doc2Html(String path,String fileName) throws FileNotFoundException {

		InputStream is=new FileInputStream(new File(path+fileName+".doc"));
		StringBuilder sb = new StringBuilder();
		try {
			HWPFDocument wordDocument=new HWPFDocument(is);
			WordToHtmlConverter converter = new WordToHtmlConverter
					(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			converter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    String type = pictureType.name();
                    return "data:image/" + type + ";base64," + new String(Base64.encodeBase64(content));
                }
            });
			converter.processDocument(wordDocument);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
	            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
	            //是否添加空格
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.setOutputProperty(OutputKeys.METHOD, "html");
	            transformer.transform(new DOMSource(converter.getDocument()),result);
	            wordDocument.close();
	            sb.append(writer.toString());
		} catch (IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}


	/*
	 * HTML转word
	 * */
	public static void html2Wrod(String context,String filename,String path) {
		StringBuilder wordSB=new StringBuilder();
		wordSB.append("<html><body>");
		wordSB.append(context);
		wordSB.append("</body></html>");
		try {
			byte[] b=wordSB.toString().getBytes("UTF-8");
			ByteArrayInputStream bais=new ByteArrayInputStream(b);
			POIFSFileSystem poifs = new POIFSFileSystem(); 
			DirectoryEntry directory = poifs.getRoot();
			directory.createDocument("WordDocument", bais); 
			//输出文件
			OutputStream out = new FileOutputStream(path+filename+".doc");
			poifs.writeFilesystem(out);
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}				
}