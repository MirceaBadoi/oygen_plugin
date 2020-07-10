package test;

import static org.junit.Assert.*;


import java.util.ArrayList;

import org.junit.Test;

import com.oxygenxml.translate.plugin.Languages;

import static org.mockito.Mockito.*;

public class TranslateTest {

	@SuppressWarnings({ "serial", "unchecked" })
	@Test
	public void testLanguagesSelect() {
		ArrayList<String> mockList = mock(ArrayList.class);
		mockList.add("ro");
		mockList.add("es");
		mockList.add("en");
		mockList.add("zh-CH");
		mockList.add("nl");
		mockList.add("ja");
		mockList.add("hu");
		when(mockList.get(0)).thenReturn(Languages.getLanguageName("ro"));
		when(mockList.get(1)).thenReturn(Languages.getLanguageName("es"));
		when(mockList.get(2)).thenReturn(Languages.getLanguageName("en"));
		when(mockList.get(3)).thenReturn(Languages.getLanguageName("zh-CN"));
		when(mockList.get(4)).thenReturn(Languages.getLanguageName("nl"));
		when(mockList.get(5)).thenReturn(Languages.getLanguageName("ja"));
		when(mockList.get(6)).thenReturn(Languages.getLanguageName("hu"));

		ArrayList<String> response = new ArrayList<String>() {{
	        add("Romanian");
	        add("Spanish");
	        add("English");
	        add("Chinese");
	        add("Dutch");
	        add("Japanese");
	        add("Hungarian");
	        
	    }};
	    ArrayList<String> result = new ArrayList<String>();
	    for(int it=0 ;it<response.size();it++) {
	    	result.add(mockList.get(it));
	    }
	    
	    assertEquals(result,response);

	}

}
