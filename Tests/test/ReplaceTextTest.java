package test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.transform.stream.StreamSource;

import org.w3c.css.sac.InputSource;
import org.xml.sax.SAXException;

import com.oxygenxml.translate.plugin.ReplaceWordsUtil;
import com.oxygenxml.translate.plugin.SegmentInfo;

import junit.framework.TestCase;
import ro.sync.ecss.component.CSSInputSource;
import ro.sync.ecss.component.CSSSourceTypes;
import ro.sync.ecss.css.csstopdf.facade.AuthorDocumentFacadeImpl;
import ro.sync.ecss.css.csstopdf.facade.PrettyPrinterFacade;
import ro.sync.ecss.dom.builder.h;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.node.AuthorDocumentFragment;
import ro.sync.ecss.extensions.api.node.AuthorElement;
import ro.sync.ecss.extensions.api.node.AuthorNode;

public class ReplaceTextTest extends TestCase {
	
	
	
	private AuthorDocumentController createAuthorDocument(String xmlDoc) throws IOException, SAXException, h {
		StreamSource streamSource = new StreamSource(new StringReader(xmlDoc));
		InputSource inputSource = new InputSource(new StringReader("*{display: block;}"));
		inputSource.setURI("file://"+inputSource.hashCode()+"/fake.css");
		CSSInputSource css = new CSSInputSource(inputSource, CSSSourceTypes.SOURCE_DOCUMENT_TYPE);
		CSSInputSource[] arg1 = new CSSInputSource[] {css};
		
		AuthorDocumentFacadeImpl authorDocumentFacadeImpl = new AuthorDocumentFacadeImpl(streamSource, arg1, null);
		return authorDocumentFacadeImpl.getController();
	}
	
	public void testReplace1() throws Exception {
		String pattern = " _7561_\n";
		String xmlDoc = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<!DOCTYPE concept PUBLIC \"-//OASIS//DTD DITA Concept//EN\" \"concept.dtd\">\n" + 
				"<concept id=\"autumnFlowers\">\n" + 
				"    <title>Flowers</title>\n" + 
				"    <conbody>\n" + 
				"        <p>Some of the flowers blooming in\n" + 
				"                autumn<indexterm>flowers<indexterm>autumn</indexterm></indexterm> are: Acashi.</p>\n" + 
				"    </conbody>\n" + 
				"</concept>\n" + 
				"";
		
		String replacementText = 
				"Algunas de las flores que florecen en otoño " + pattern + " flor "+ pattern + " otoño " + pattern + " son: Acashi.";
		
		
		AuthorDocumentController controller = createAuthorDocument(xmlDoc);
		
		AuthorElement rootElement = controller.getAuthorDocumentNode().getRootElement();
		AuthorNode conbody = rootElement.getContentNodes().get(1);
		AuthorNode p = ((AuthorElement)conbody).getContentNodes().get(0);
		StringBuilder textSelectedTransformed = new StringBuilder();
		ArrayList<SegmentInfo> whiteSpaces = new ArrayList<SegmentInfo>();
		whiteSpaces = ReplaceWordsUtil.transformSelectedText(controller.getTextContentIterator(p.getStartOffset(),
				p.getEndOffset()), textSelectedTransformed, pattern);
		ReplaceWordsUtil.replaceText(controller, p.getStartOffset(), p.getEndOffset(), replacementText, pattern, whiteSpaces);
		
		AuthorDocumentFragment frag = controller.createDocumentFragment(controller.getAuthorDocumentNode().getRootElement(), true);
		String xml = controller.serializeFragmentToXML(frag);
		String prettyPrint = PrettyPrinterFacade.prettyPrint(xml);
		System.err.println(prettyPrint);
		
		assertEquals(
				"<concept\n" + 
				"  id=\"autumnFlowers\">\n" + 
				"  <title>Flowers</title>\n" + 
				"  <conbody>\n" + 
				"    <p>Algunas de las flores que florecen en\r\n" + 
				"        otoño<indexterm>flor<indexterm>otoño</indexterm></indexterm> son: Acashi.</p>\n" + 
				"  </conbody>\n" + 
				"</concept>\n" + 
				"", prettyPrint);
	}

}
