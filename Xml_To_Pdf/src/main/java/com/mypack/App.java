package com.mypack;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class App {
	public static void main(String[] args) {
		try {
			File xmlfile=new File("resources/organization.xml");
			File xsltfile=new File("resources/organization.xsl");
			File pdfDir=new File("/home/jitu/Documents/new Folder/pdfs");
			pdfDir.mkdir();
			File pdfFile=new File(pdfDir,"org.pdf");
			System.out.println(pdfFile.getAbsolutePath());
			
			final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			FOUserAgent fOUserAgent =fopFactory.newFOUserAgent();
			
			OutputStream out=new FileOutputStream(pdfFile);
			out=new BufferedOutputStream(out);
			try {
				//contructor fop with desire output formate 
				Fop fop;
				fop=fopFactory.newFop(MimeConstants.MIME_PDF, fOUserAgent,out);
				//setup xslt
				
				TransformerFactory factory=TransformerFactory.newInstance();
				Transformer transformer =factory.newTransformer(new StreamSource(xsltfile));
				
				//setup input for XSLT Transformation
				Source src=new StreamSource(xmlfile);
				
				javax.xml.transform.Result res=new SAXResult(fop.getDefaultHandler());
				
				transformer.transform(src, res);
				
				
			} catch (FOPException | TransformerException e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally {
				out.close();
			}
			
		} catch (IOException exp) {
			exp.printStackTrace();
			// TODO: handle exception
		}
	}

}
