package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ast.NodeVisitor;
import com.vladsch.flexmark.internal.util.ast.VisitHandler;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkPostProcessor extends DocumentPostProcessor {
    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();
    
    private final NodeVisitor myVisitor;

    public AutolinkPostProcessor(Document document) {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, AutolinkPostProcessor.this::visit)
        );

    }

    public Document processDocument(Document document) {
        myVisitor.visit(document);
        return document;
    }

    private void visit(Text text) {
        if (!text.isOrDescendantOfType(DoNotLinkify.class)) {
            processTextNode(text);
        }
    }

    private void processTextNode(Text text) {
        BasedSequence original = text.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);

        Node lastNode = text;
        int lastEscaped = 0;
        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            int index = textMapper.originalOffset(link.getBeginIndex());

            if (index != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, index);
                Node node = new Text(escapedChars);
                lastNode.insertAfter(node);
                lastNode = node;
            }

            Text contentNode = new Text(linkText);
            LinkNode linkNode;

            if (link.getType() == LinkType.EMAIL) {
                linkNode = new MailLink();
                ((MailLink) linkNode).setText(linkText);
            } else {
                linkNode = new AutoLink();
                ((AutoLink) linkNode).setText(linkText);
            }

            linkNode.setCharsFromContent();
            linkNode.appendChild(contentNode);
            lastNode.insertAfter(linkNode);
            lastNode = linkNode;

            lastEscaped = textMapper.originalOffset(link.getEndIndex());
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            Node node = new Text(escapedChars);
            lastNode.insertAfter(node);
        }
        text.unlink();
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new AutolinkPostProcessor(document);
        }
    }
}
