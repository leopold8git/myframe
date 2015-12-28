/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package x.y.web.tags.authz;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import x.y.subject.Subject;

/**
 * @since 0.1
 */
public abstract class SecureTag extends TagSupport {

	private static final Log log = LogFactory.getLog(SecureTag.class);
	
	protected PageContext pageContext;

    public SecureTag() {
    }

    protected Subject getSubject() {
    	HttpSession s = pageContext.getSession();
    	return  s==null?null:(Subject)s.getAttribute(Subject.sessionKey);
    }

    protected void verifyAttributes() throws JspException {
    }

    public int doStartTag() throws JspException {

        verifyAttributes();

        return onDoStartTag();
    }

    public abstract int onDoStartTag() throws JspException;

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}
    
}
