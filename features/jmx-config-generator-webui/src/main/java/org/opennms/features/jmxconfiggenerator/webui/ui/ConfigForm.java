/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.jmxconfiggenerator.webui.ui;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import java.util.HashMap;
import java.util.Map;
import org.kohsuke.args4j.Option;
import org.opennms.features.jmxconfiggenerator.webui.Config;
import org.opennms.features.jmxconfiggenerator.webui.JmxConfigGeneratorApplication;
import org.opennms.features.jmxconfiggenerator.webui.data.ConfigModel;
import org.opennms.features.jmxconfiggenerator.webui.data.MetaConfigModel;
import org.opennms.features.jmxconfiggenerator.Starter;

/**
 * This form handles editing of a
 * <code>ConfigModel</code>.
 *
 * @author m.v.rueden
 * @see ConfigModel
 */
public class ConfigForm extends Form implements ClickListener {

	private Button next = new Button("fetch Mbeans", (ClickListener) this);
	//TODO test button remove it
	private Button window = new Button("Test Window", (ClickListener) this);
	private JmxConfigGeneratorApplication app;

	public ConfigForm(JmxConfigGeneratorApplication app) {
		this.app = app;
		setCaption("config of jmx config generator");
		setItemDataSource(new BeanItem<ConfigModel>(new ConfigModel()));
		setVisibleItemProperties(createVisibleItemProperties());
		setFooter(createFooter());
		setImmediate(true);
		setSizeFull();
		updateAuthenticationFields(false); //default -> hide those fields
		initFields();
		updateDescriptions();
		window.setVisible(Config.DEBUG);
	}

	private Object[] createVisibleItemProperties() {
		return new Object[]{
					MetaConfigModel.JMXMP,
					MetaConfigModel.HOST,
					MetaConfigModel.PORT,
					MetaConfigModel.AUTHENTICATE,
					MetaConfigModel.USER,
					MetaConfigModel.PASSWORD,
					MetaConfigModel.SKIP_DEFAULT_VM,
					MetaConfigModel.RUN_WRITABLE_MBEANS};
	}

	private Layout createFooter() {
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(next);
		footer.addComponent(window);
		return footer;
	}

	/**
	 * Toggles the visibility of user and password fields. The fields are shown if "authenticate" checkbox is presssed.
	 * Otherwise they are not shown.
	 */
	private void updateAuthenticationFields(boolean visible) throws ReadOnlyException, ConversionException {
		getField(MetaConfigModel.USER).setVisible(visible);
		getField(MetaConfigModel.PASSWORD).setVisible(visible);
		if (!visible) {
			getField(MetaConfigModel.USER).setValue(null);
			getField(MetaConfigModel.PASSWORD).setValue(null);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getSource() == next) app.findMBeans();
		if (event.getSource() == window) app.showTestWindow();
	}

	/**
	 * DefaultFieldFactory works for us, we only add some additional stuff to each field -> if needed.
	 *
	 */
	private void initFields() {
		getField(MetaConfigModel.AUTHENTICATE).addListener(new ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				updateAuthenticationFields((Boolean) event.getProperty().getValue());
			}
		});
		((TextField) getField(MetaConfigModel.USER)).setNullRepresentation("");
		((TextField) getField(MetaConfigModel.PASSWORD)).setNullRepresentation("");
		((TextField) getField(MetaConfigModel.PASSWORD)).setSecret(true); //use PasswordField instead
	}

	/**
	 * Updates the descriptions (tool tips) of each field in the form using {@link #getOptionDescriptions() }
	 */
	private void updateDescriptions() {
		Map<String, String> optionDescriptions = getOptionDescriptions();
		Starter.class.getAnnotation(org.kohsuke.args4j.Option.class);
		for (Object property : getVisibleItemProperties()) {
			String propName = property.toString();
			if (getField(propName) != null && optionDescriptions.get(propName) != null)
				getField(propName).setDescription(optionDescriptions.get(propName));
		}
	}

	/**
	 * In class {@link org.opennms.tools.jmxconfiggenerator.Starter} are several command line options defined. Each
	 * option has a name (mandatory) and a description (optional). This method gets all descriptions and assign each
	 * description to the name. If the option starts with at least one '-' all '-' are removed. Therefore the builded
	 * map looks like:
	 * <pre>
	 *      {key} -> {value}
	 *      "force" -> "this option forces the deletion of the file"
	 * </pre>
	 *
	 * @return a Map containing a description for each option defined in
	 * {@link org.opennms.tools.jmxconfiggenerator.Starter}
	 * @see org.opennms.tools.jmxconfiggenerator.Starter
	 */
	private Map<String, String> getOptionDescriptions() {
		Map<String, String> optionDescriptions = new HashMap<String, String>();
		for (java.lang.reflect.Field f : Starter.class.getDeclaredFields()) {
			Option ann = f.getAnnotation(Option.class);
			if (ann == null || ann.usage() == null) continue;
			optionDescriptions.put(ann.name().replaceAll("-", ""), ann.usage());
		}
		return optionDescriptions;
	}
}
