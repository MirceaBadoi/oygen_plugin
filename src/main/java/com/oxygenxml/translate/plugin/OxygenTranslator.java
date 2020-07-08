package com.oxygenxml.translate.plugin;

import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;

/**
 * Implement internationalization using PluginResourceBundle
 *
 */
public class OxygenTranslator implements Translator {

	@Override
	public String getTranslation(String key) {
		String toRet = key;
	  StandalonePluginWorkspace pluginWorkspace = (StandalonePluginWorkspace)PluginWorkspaceProvider.getPluginWorkspace();
	  if(pluginWorkspace != null) {
	    toRet = pluginWorkspace.getResourceBundle().getMessage(key);
	  }
	  return toRet;
	}
}
