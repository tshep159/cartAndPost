package org.kamogelofoundations.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.kamogelofoundations.dto.CartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.stripe.model.Customer;

public class PDFGenerator {


	  private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
	  
	  public static ByteArrayInputStream customerPDFReport(List<CartLine> lines) {
	    Document document = new Document();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        
	        try {
	          
	          PdfWriter.getInstance(document, out);
	            document.open();
	          
	            // Add Text to PDF file ->
	          Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
	          Paragraph para = new Paragraph( "Customer Table", font);
	          para.setAlignment(Element.ALIGN_CENTER);
	          document.add(para);
	          document.add(Chunk.NEWLINE);
	          
	          PdfPTable table = new PdfPTable(3);
	          // Add PDF Table Header ->
	            Stream.of("ID", "First Name", "Last Name")
	              .forEach(headerTitle -> {
	                  PdfPCell header = new PdfPCell();
	                  Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	                  header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	                  header.setHorizontalAlignment(Element.ALIGN_CENTER);
	                  header.setBorderWidth(2);
	                  header.setPhrase(new Phrase(headerTitle, headFont));
	                  table.addCell(header);
	              });
	            
	            for (CartLine line : lines) {
	              PdfPCell ProductName = new PdfPCell(new Phrase(line.getProduct().getName().toString()));
	              ProductName.setPaddingLeft(4);
	              ProductName.setVerticalAlignment(Element.ALIGN_MIDDLE);
	              ProductName.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table.addCell(ProductName);
	 

		              PdfPCell Price = new PdfPCell(new Phrase(String.valueOf(line.getProduct().getUnitPrice())));
	                Price.setPaddingLeft(4);
	                Price.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                Price.setHorizontalAlignment(Element.ALIGN_LEFT);
	                table.addCell(Price);
	 
	                PdfPCell Total = new PdfPCell(new Phrase(String.valueOf(line.getCart().getGrandTotal())));
	                Total.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                Total.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                Total.setPaddingRight(4);
	                table.addCell(Total);
	            }
	            document.add(table);
	            
	            document.close();
	        }catch(DocumentException e) {
	          logger.error(e.toString());
	        }
	        
	    return new ByteArrayInputStream(out.toByteArray());
	  }
	}

