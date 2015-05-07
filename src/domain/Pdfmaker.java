/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

/**
 *
 * @author bremme windows
 */
public class Pdfmaker {
    private List<Exercise> ex;
    private Test test;
    private int counter=1;
    public Pdfmaker(List<Exercise> e, Test t)
    {
        this.ex = e;
        this.test = t;
    }
    public void makePdf()
    {
        Document doc = new Document();
        doc.setPageSize(PageSize.A4);
        try{
            PdfWriter writer =PdfWriter.getInstance(doc,new FileOutputStream(test.getTitle()+".pdf"));
            writer.setPdfVersion(PdfWriter.VERSION_1_7);
            doc.open();
            Font f = new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD,new BaseColor(0,0,0));
            Paragraph title = new Paragraph("TEST TITLE",f);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.addCreator("Arne De Bremme");
            //make table
            PdfPTable table = new PdfPTable(4);
            table.setTotalWidth(550f);
            table.setLockedWidth(true);
            table.setHeaderRows(1);
            Paragraph p =new Paragraph(test.getTitle());
            p.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cell = new PdfPCell(p);
            cell.setGrayFill(0.7f);
            cell.setColspan(4);
            table.addCell(cell);
            table.addCell(test.getClassGroup().getGroupName());
            PdfPCell cell2 =new PdfPCell(new Paragraph(test.getDescription()));
            cell2.setColspan(2);
            cell2.setRowspan(2);
            table.addCell(cell2);
            PdfPCell cell3 = new PdfPCell(new Paragraph("DD "+test.getStartDate().getDay()+"/"+test.getStartDate().getMonth()+"/"+test.getStartDate().getYear()));
            cell3.setRowspan(2);
            table.addCell(cell3);
            table.addCell(new Paragraph("Test " +test.getTestId()));
            doc.add(table);
            doc.add(Chunk.NEWLINE);
            for(Exercise e : ex)
            {
            doc.add(new Paragraph("Vraag " +counter +" Punten/"+e.getPunten()));
            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("Klimatogram "+e.getClimateChart().getLocation()+" "+e.getPunten()));
            Robot r = new Robot();
            Desktop.getDesktop().browse(new URI("http://climatechart.azurewebsites.net/ClimateChart/ShowExercises?selectedYear=1&continentId="+e.getClimateChart().getCountry().getContinent().getId()+"&countryId="+e.getClimateChart().getCountry().getContinent().getId()+"&climateId="+e.getClimateChart().getId()));
            TimeUnit.SECONDS.sleep(4);
            
            BufferedImage bi=r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width*7/10,Toolkit.getDefaultToolkit().getScreenSize().width*8/14));
            File file = new File("screenCapture.jpg");
            ImageIO.write(bi,"jpg", file);
            Image img = Image.getInstance("screenCapture.jpg");
            img.scaleAbsolute(400, 300);
            System.out.println("adding image");
            doc.add(img);
            System.out.println("adding par " +e.getNaam());
            doc.add(new Paragraph(e.getNaam()));
            counter++;
            }
           
            doc.close();
        }catch(Exception e)
        {
            
        }
    }
}
