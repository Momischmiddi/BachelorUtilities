package generator;

import freemarker.core.HTMLOutputFormat;
import freemarker.template.*;
import java.util.*;

import backend.database.dbClasses.Topic;

import java.io.*;

public class Generator {
	
	
	private Configuration cfg;
	
	private static Generator instance;
	public static Generator getInstance () {
	    if (Generator.instance == null) {
	    	Generator.instance = new Generator ();
	    }
	    return Generator.instance;
	  }
	
	private Generator(){
        cfg = new Configuration(Configuration.VERSION_2_3_25);
        try {
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"\\src\\Templates"));
			cfg.setDefaultEncoding("UTF-8");
	        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	        cfg.setOutputFormat(HTMLOutputFormat.INSTANCE);
	        cfg.setLogTemplateExceptions(false);
		} catch (IOException e) {
			cfg = null;
			e.printStackTrace();
		}
    }
	
	public void generateErstgutachten(Topic topic, String projectType, String path,String place, String course){
		Map root = new HashMap();
        root.put("ProjectType", projectType);
        root.put("ProjectTitle", "" + topic.getTitle());
        root.put("AuthorLastName", topic.getAuthor().getName());
        root.put("AuthorFirstName", topic.getAuthor().getForename());
        root.put("AuthorMatrNr", ""+ topic.getAuthor().getMatriculationNumber());
        root.put("course", course);
        root.put("SuperVisorLastName", topic.getExpertOpinion().getName());
        root.put("SuperVisorFirstName", topic.getExpertOpinion().getForename());
        root.put("SuperVisorOpinionText", topic.getExpertOpinion().getOpinion()
        		+ topic.getSecondOpinion().getOpinion());
        root.put("Place", place);
        
        Writer out = null;
		try {
			Template temp = cfg.getTemplate("erstgutachten.ftlh");
			/* Merge data-model with template */
	        out = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream(path), "utf-8"));
	        temp.process(root, out);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
