package com.acme.craft.fixme.commons.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class PropertyService {

	public List<String> defaultProperties() {
		List<String> properties = new ArrayList<>(Arrays.asList("property1", "property2", "property3", "property4"));
		// properties = Lists.newArrayList("","","");
		// properties = Collections.singleton("p1");
		return properties;
	}

	public boolean valid(List<String> properties) {
		if (CollectionUtils.isNotEmpty(properties)) {
			boolean isValid = true;
			for (String property : properties) {
				isValid = isValid && valid(property);
			}
		}
		return false;
	}

	private boolean valid(String property) {
		return property != null && !property.isEmpty();
	}
}
