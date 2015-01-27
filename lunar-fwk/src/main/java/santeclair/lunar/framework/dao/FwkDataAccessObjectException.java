/*
 * $Id: FwkDataAccessObjectException.java,v 1.1 2006-09-01 15:36:31 brey Exp $
 *
 * Copyright 2006 Santeclair. All rights reserved.
 */
package santeclair.lunar.framework.dao;

public class FwkDataAccessObjectException extends RuntimeException {
    
	private static final long serialVersionUID = 4118293526873970509L;

	public FwkDataAccessObjectException() {
        super();
    }

    public FwkDataAccessObjectException(String arg0) {
        super(arg0);
    }

    public FwkDataAccessObjectException(Throwable arg0) {
        super(arg0);
    }

    public FwkDataAccessObjectException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
