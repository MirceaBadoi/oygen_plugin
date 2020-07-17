package com.oxygenxml.translate.plugin;
/**
 * Class used as a pair in order to check whitespace
 * @author Badoi Mircea
 *
 */
public class SegmentInfo {

	private boolean startWithWhitespace = false;
	private boolean endWithWhitespace = false;

	public SegmentInfo() {

	}

	public SegmentInfo(boolean startWithWhitespace, boolean endWithWhitespace) {
		this.startWithWhitespace = startWithWhitespace;
		this.endWithWhitespace = endWithWhitespace;
	}

	public boolean isStartWithWhitespace() {
		return startWithWhitespace;
	}

	public void setStartWithWhitespace(boolean startWithWhitespace) {
		this.startWithWhitespace = startWithWhitespace;
	}

	public boolean isEndWithWhitespace() {
		return endWithWhitespace;
	}

	public void setEndWithWhitespace(boolean endWithWhitespace) {
		this.endWithWhitespace = endWithWhitespace;
	}

}
