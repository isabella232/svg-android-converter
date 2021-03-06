/*
 * Copyright (C) 2015 Peel Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peel.tools.svgandroid;

import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

/**
 * Interface to convert SVG to Android.
 *
 * @author Inderjeet Singh
 */
public class SvgToAndroidStrategy {
    public Document read(InputStream input) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);  // factory.setNamespaceAware(true);
        factory.setValidating(false);  // factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(input);
    }

    public void convert(Document input, Writer output) throws Exception {
    }
}
