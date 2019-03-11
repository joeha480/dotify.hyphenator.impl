package org.daisy.dotify.hyphenator.impl;

import org.daisy.dotify.api.hyphenator.HyphenatorConfigurationException;

class LatexHyphenator extends AbstractHyphenator {
	private final HyphenationConfig hyphenator;
	private final CacheMap<String, String> cache;
	
	LatexHyphenator(HyphenationConfig hyphenator) throws HyphenatorConfigurationException {
		this.hyphenator = hyphenator;
		this.beginLimit = this.hyphenator.getDefaultBeginLimit();
		this.endLimit = this.hyphenator.getDefaultEndLimit();
		this.cache = new CacheMap<>();
	}

	@Override
	public void setBeginLimit(int beginLimit) {
		cache.clear();
		super.setBeginLimit(beginLimit);
	}

	@Override
	public void setEndLimit(int endLimit) {
		cache.clear();
		super.setEndLimit(endLimit);
	}

	@Override
	public String hyphenate(String phrase) {
		String ret = cache.get(phrase);
		if (ret==null) {
			ret = hyphenator.getHyphenator().hyphenate(phrase, getBeginLimit(), getEndLimit());
			cache.put(phrase, ret);
		}
		return ret;
	}

}
