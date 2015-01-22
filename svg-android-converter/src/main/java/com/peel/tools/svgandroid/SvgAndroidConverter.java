// Copyright (C) 2015 Peel Inc.
package com.peel.tools.svgandroid;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * SVG to Android Vector Drawable converter.
 *
 * @author Inderjeet Singh
 */
public class SvgAndroidConverter {
    // Global value so it can be ref'd by the tree-adapter
    static Document document;

    public static void main(String[] argv) throws Exception {
        if (argv.length != 2) {
            System.err.println("Usage: java App svgfile outputfile");
            System.exit(1);
        }
        InputStream input = new FileInputStream(argv[0]);
        FileWriter output = new FileWriter(argv[1]);
        stylize(input, output);
        input.close();
        output.close();
    }

    public static void stylize(InputStream input, Writer output) throws IOException {
        InputStream stylesheet = SvgAndroidConverter.class.getResourceAsStream("/svg-android.xslt");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);  // factory.setNamespaceAware(true);
        factory.setValidating(false);  // factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(input);

            // Use a Transformer for output
            TransformerFactory tFactory = TransformerFactory.newInstance();
            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer = tFactory.newTransformer(stylesource);

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException tce) {
            // Error generated by the parser
            System.out.println("\n** Transformer Factory error");
            System.out.println("   " + tce.getMessage());

            // Use the contained exception, if any
            Throwable x = tce;

            if (tce.getException() != null) {
                x = tce.getException();
            }

            x.printStackTrace();
        } catch (TransformerException te) {
            // Error generated by the parser
            System.out.println("\n** Transformation error");
            System.out.println("   " + te.getMessage());

            // Use the contained exception, if any
            Throwable x = te;

            if (te.getException() != null) {
                x = te.getException();
            }

            x.printStackTrace();
        } catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            x.printStackTrace();
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}