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
	
	public void generateErstgutachten(Topic topic, String projectType, String path){
		Map root = new HashMap();
        root.put("user", projectType);

		try {
			Template temp = cfg.getTemplate("erstgutachten.ftlh");
			/* Merge data-model with template */
	        Writer out = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream(path), "utf-8"));
	        temp.process(root, out);
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
