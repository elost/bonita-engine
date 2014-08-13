/**
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.engine.core.document.model.impl;

import org.bonitasoft.engine.core.document.model.SDocumentContent;
import org.bonitasoft.engine.core.document.model.SDocumentContentBuilder;

/**
 * @author Zhao Na
 */
public class SDocumentContentBuilderImpl implements SDocumentContentBuilder {

    private final SDocumentContentImpl document;
    
    public SDocumentContentBuilderImpl(final SDocumentContentImpl document) {
        super();
        this.document = document;
    }

    @Override
    public SDocumentContent done() {
        return document;
    }

    @Override
    public SDocumentContentBuilder setContent(byte[] content) {
        document.setContent(content);
        return this;
    }

    @Override
    public SDocumentContentBuilder setStorageId(String documentId) {
        document.setStorageId(documentId);
        return this;
    }

    public static SDocumentContentBuilder getInstance() {
        return new SDocumentContentBuilderImpl(null);
    }

}