/*******************************************************************************
 * (c) Copyright 2014 Hewlett-Packard Development Company, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package org.eclipse.score.engine.data;

import java.io.Serializable;

/**
 * Date: 12/23/13
 *
 * @author
 */
public interface Identifiable extends Serializable {
	/**
	 * Returns the database id.
	 *
	 * @return the database id, or <code>null</code> for a transient entity.
	 */
	Serializable getId();
}