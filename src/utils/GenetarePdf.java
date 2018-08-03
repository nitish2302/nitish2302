package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import entities.Order;
import entities.TableInfo;

public class GenetarePdf {

	private static final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
	private static final float MM_TO_UNITS = 1 / (15 * 2.54f) * DEFAULT_USER_SPACE_UNIT_DPI;

	public static void generatePdfFromHtml(TableInfo tableInfo) throws Exception {

		Document document = new Document(PageSize.A4);
		
		Rectangle PAGE_SIZE_LANDSCAPE_A5 = new Rectangle(200 * MM_TO_UNITS, 230 * MM_TO_UNITS);
		PAGE_SIZE_LANDSCAPE_A5.setBorder(0);
		document.setPageSize(PAGE_SIZE_LANDSCAPE_A5);
		document.setMargins(0f, 0f, 0f, 0f);

		String upperHtml ="<html><body><h2 style='margin-left: 900px;'><i>Kalakaar</i></h2><div style='margin-left: 550px;'><p>Shop 11,Kamlesh apts,Sher E Punjab</p><p style='margin-left: 20px;'>Andheri West, Mumbai-110092</p><p>Phone No: 9989898989/7474747474</p><p></p></div><div style='margin-left: 550px;'><h3>GST NO : 272942781</h3><h3>SAC NO : 272942781</h3></div><p style='margin-left: 400px;'>-------------------------------------------------------------------------------------</p><h2 style='margin-left: 600px;'>Order#${orderNo}</h2><div style='margin-left: 500px;'><table><tr><th>Qty</th><th>Product</th><th>Rate</th><th>Amount</th></tr><tr>${table}</tr></table></div><p style='margin-left: 400px;'>-------------------------------------------------------------------------------------</p></body></html>"; 
				
				
				/*"<html><head></head>" + "<body>" + "	<h2 style=\"margin-left: 600px;\">" + "		<i>Kalakaar</i>"
				+ "	</h2>" + "	<div style=\"margin-left: 550px;\">"
				+ "		<p>Shop 11,Kamlesh apts,Sher E Punjab</p>"
				+ "		<p style=\"margin-left: 20px;\">Andheri West, Mumbai-110092</p>"
				+ "		<p>Phone No: 9989898989/7474747474</p>" + "		<p></p>" + "	</div>" + ""
				+ "	<div style=\"margin-left: 550px;\">" + "		<h3>GST NO : 272942781</h3>"
				+ "		<h3>SAC NO : 272942781</h3>" + "	</div>"
				+ "	<p style=\"margin-left: 400px;\">---------------------------------------------------------------------------------------------------------</p>"
				+ "" + "	<h2 style=\"margin-left: 600px;\">Order# " + tableInfo.getBillNumber() + "</h2>" + ""
				+ "	<div style=\"margin-left: 500px;\">" + "" + "		<table style=\"height: 2%\">"
				+ "			<tr>------------------------------------------------------------" + "			</tr>"
				+ "			<tr>" + "				<th width=\"20%\">Qty</th>"
				+ "				<th width=\"40%\">Product</th>" + "				<th width=\"20%\">Rate</th>"
				+ "				<th width=\"20%\">Amount</th>" + "" + "			</tr>";

		String lowerHtml = "</table>" + "" + "	</div>"
				+ "	<p style=\"margin-left: 400px;\">---------------------------------------------------------------------------------------------------------</p>"
				+ "</body>" + "</html>";*/

		List<Order> orderList = tableInfo.getOrders();
		StringBuilder stringBuilder = new StringBuilder();

		try {
			for (Order order : orderList) {

				stringBuilder.append("<td>" + order.getQuantity() + "</td><td>" + order.getItemName() + "</td><td>"
						+ order.getPrice() + "</td><td>" + order.getTotal() + "</td>");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		upperHtml = upperHtml.replace("{table}", stringBuilder.toString());
		upperHtml = upperHtml.replace("{orderNo}", tableInfo.getBillNumber());
		

		OutputStream file = new FileOutputStream(new File("C:\\Temp\\" + tableInfo.getBillNumber() + ".pdf"));
		String strHtml = upperHtml;
		System.out.println(strHtml+"strHtml");
		PdfWriter.getInstance(document, file);
		document.open();
		HTMLWorker htmlWorker = new HTMLWorker(document);
		htmlWorker.parse(new StringReader(strHtml));
		document.close();
		file.close();

	}

}
