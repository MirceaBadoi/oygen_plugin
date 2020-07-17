package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

<<<<<<< HEAD
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> parent of 9d09272... update

import org.junit.Test;

import com.oxygenxml.translate.plugin.Languages;

import ro.sync.exml.workspace.api.options.WSOptionsStorage;

/**
 * Test class for translation increase priority and apply priority
 * @author BadoiMircea
 */
public class TranslateTest {
	
	/**
	 * 
	 * 
	 */
	@Test	
	public void testGetLanguagesISOCodes() {
		String[] test= {"ro","en","fr"};
		WSOptionsStorage store = mock(WSOptionsStorage.class);
		when(store.getStringArrayOption(Languages.ID_LANG, null)).thenReturn(test);
		Set<String> langs = new HashSet<String>();
		langs.add("fr");
		langs.add("de");
		langs.add("en");
		langs.add("ro");
		langs.add("es");
		String []testResult=Languages.getLanguagesISOCodes(store, langs);
		assertEquals("[ro, en, fr, de, es]", Arrays.toString(testResult));
	}
	
	@Test
<<<<<<< HEAD
	public void testIncreasePriority() {
=======
	public void testLanguagesSelect() {
		Languages lang = new Languages();
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
>>>>>>> parent of 9d09272... update

		StringBuilder sb = new  StringBuilder();
		WSOptionsStorageAdapter store = new WSOptionsStorageAdapter() {
			@Override
			public void setStringArrayOption(String key, String[] values) {
				if (Languages.ID_LANG.equals(key)) {
					sb.append(Arrays.toString(values));
				}
			}

			@Override
			public String[] getStringArrayOption(String key, String[] defaultValues) {
				// initial values.
				if (Languages.ID_LANG.contentEquals(key)) {
					return new String[] {"ro","en","fr"};
				}
				return null;
			}
		};

		Languages.increasePriorityLanguage(store, "de");
		assertEquals("[de, ro, en, fr]", sb.toString());
		
	}
}
