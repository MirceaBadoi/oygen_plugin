package test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	public void testIncreasePriority() {

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
