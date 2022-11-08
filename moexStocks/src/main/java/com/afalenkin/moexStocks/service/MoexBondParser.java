package com.afalenkin.moexStocks.service;

import com.afalenkin.moexStocks.dto.Bond;
import com.afalenkin.moexStocks.exception.ParsingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
@Service
@Slf4j
public class MoexBondParser implements BondParser {
    @Override
    public List<Bond> parse(String xml) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            try (StringReader reader = new StringReader(xml)) {
                Document doc = documentBuilder.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("row");

                return IntStream.range(0, list.getLength())
                        .mapToObj(list::item)
                        .map(this::parseNode)
                        .filter(Objects::nonNull)
                        .toList();
            }
        } catch (Exception ex) {
            log.error("xml parsing error, xml:{}", xml, ex);
            throw new ParsingException(ex);
        }
    }

    private Bond parseNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String ticker = element.getAttribute("SECID");
            String price = element.getAttribute("PREVADMITTEDQUOTE");
            String name = element.getAttribute("SHORTNAME");
            if (!ticker.isEmpty() && !price.isEmpty() && !name.isEmpty()) {
                return new Bond(ticker, name, new BigDecimal(price));
            }
        }
        return null;
    }
}
