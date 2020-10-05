package org.kamogelofoundations.controller;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.CartOrder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceHelper {

	public void generatePDF(List<CartLine> namesList) {

		
				
		try {
			String file_name = "src\\main\\resources\\static\\pdfs\\test2.pdf";

			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));
			document.open();
			
			Paragraph para =new Paragraph("Invoice");
			
			
			
			
			document.add(para);

			Paragraph para2 =new Paragraph(" ");
			document.add(para2);
			PdfPTable table = new PdfPTable(3);
			PdfPCell cl = new PdfPCell(new Phrase("Product Name "));
			table.addCell(cl);
			
		
			
			cl = new PdfPCell(new Phrase("Product Price"));
			table.addCell(cl);
			
			cl = new PdfPCell(new Phrase("Total"));
			table.addCell(cl);
			table.setHeaderRows(1);
			
		
            // foreach loop
            for (CartLine item: namesList) {
               System.out.println(item);
               table.addCell(String.valueOf(item.getProduct().getName()));
   			table.addCell("R "+String.valueOf(item.getProduct().getUnitPrice()));
   				table.addCell("R "+String.valueOf(item.getCart().getGrandTotal()));
   				
   		   		//table.addCell("src\\main\\resources\\static\\media"+item.getProduct().getImage());		
   		 		
            }

			
			document.add(table);			
			document.close();
			System.out.println("Done generating pdf");
		} catch (Exception e) {
			e.getMessage();

		}

	}

}
