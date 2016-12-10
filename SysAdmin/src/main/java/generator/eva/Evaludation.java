package generator.eva;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import backend.database.dbClasses.Topic;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;

public class Evaludation {
	public Evaludation(Topic toppic) {
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
		try {
		DocumentTemplate template = documentTemplateFactory.getTemplate(new File("DocumentTemplates/my-template.ott"));
		Map<String, String> data = new HashMap<String, String>();
		data.put("var", "value");
		//...
			template.createDocument(data, new FileOutputStream("my-output.odt"));
		} catch (IOException | DocumentTemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
