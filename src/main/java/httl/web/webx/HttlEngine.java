/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package httl.web.webx;

import httl.web.WebEngine;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

import com.alibaba.citrus.service.template.TemplateContext;
import com.alibaba.citrus.service.template.TemplateEngine;
import com.alibaba.citrus.service.template.TemplateException;

/**
 * HttlTemplateEngine. (Integration, Singleton, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class HttlEngine implements TemplateEngine {

	private final Properties properties = new Properties();

	private String path;

	private String templateEncoding;

	public void setPath(String path) {
		this.path = path;
	}

	public void setTemplateEncoding(String templateEncoding) {
		this.templateEncoding = templateEncoding;
	}

    public void setAdvancedProperties(Map<String, String> configuration) {
        this.properties.clear();
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            this.properties.setProperty(entry.getKey(), entry.getValue());
        }
    }

	public String[] getDefaultExtensions() {
		return new String[] { "httl" };
	}

	public boolean exists(String templateName) {
		try {
			if (path != null && path.length() > 0) {
				templateName = path + templateName;
			}
			return WebEngine.getTemplate(templateName) != null;
		} catch (Exception e) {
			return true;
		}
	}

	public String getText(String templateName, TemplateContext context)
			throws TemplateException, IOException {
		try {
			if (path != null && path.length() > 0) {
				templateName = path + templateName;
			}
			return WebEngine.getTemplate(templateName, templateEncoding).render(new ContextMap(context));
		} catch (ParseException e) {
			throw new TemplateException(e.getMessage(), e);
		}
	}

	public void writeTo(String templateName, TemplateContext context,
			OutputStream ostream) throws TemplateException, IOException {
		try {
			if (path != null && path.length() > 0) {
				templateName = path + templateName;
			}
			WebEngine.getTemplate(templateName, templateEncoding).render(new ContextMap(context), ostream);
		} catch (ParseException e) {
			throw new TemplateException(e.getMessage(), e);
		}
	}

	public void writeTo(String templateName, TemplateContext context,
			Writer writer) throws TemplateException, IOException {
		try {
			if (path != null && path.length() > 0) {
				templateName = path + templateName;
			}
			WebEngine.getTemplate(templateName, templateEncoding).render(new ContextMap(context), writer);
		} catch (ParseException e) {
			throw new TemplateException(e.getMessage(), e);
		}
	}

}