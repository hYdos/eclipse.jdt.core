/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * This is an implementation of an early-draft specification developed under the Java
 * Community Process (JCP) and is made available for testing and evaluation purposes
 * only. The code is not compatible with any specification of the JCP.
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.internal.core.dom.util;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;

public class DOMASTUtil {

	/**
	 * Validates if the given <code>AST</code> supports the provided <code>nodeType</code>. This API checks for node
	 * types supported from JLS 14 onwards and will return <code>true></code> for nodes added before JLS14.
	 * 
	 * @param ast
	 *            the AST to be evaluated
	 * @param nodeType
	 *            the node type constant indicating a node of type to be evaluated
	 * @return <code>true</code> if the given <code>AST</code> supports the provided <code>nodeType</code> else
	 *         <code>false</code>
	 * @see ASTNode#getNodeType()
	 * @since 3.20 BETA_JAVA
	 */
	private static boolean isNodeTypeSupportedinAST(AST ast, int nodeType) {
		return isNodeTypeSupportedinAST(ast.apiLevel(), ast.isPreviewEnabledSet(), nodeType);
	}

	/**
	 * Validates if the given <code>apiLevel</code> and <code>previewEnabled</code> supports the provided
	 * <code>nodeType</code>. This API checks for node types supported from JLS 14 onwards and will return
	 * <code>true></code> for nodes added before JLS14.
	 * 
	 * @param apiLevel
	 *            the level to be checked
	 * @param previewEnabled
	 *            the preview feature to be considered
	 * @param nodeType
	 *            the node type constant indicating a node of type to be evaluated
	 * @return <code>true</code> if the given <code>AST</code> supports the provided <code>nodeType</code> else
	 *         <code>false</code>
	 * @see ASTNode#getNodeType()
	 * @since 3.20 BETA_JAVA
	 */
	private static boolean isNodeTypeSupportedinAST(int apiLevel, boolean previewEnabled, int nodeType) {
		switch (nodeType) {
			case ASTNode.SWITCH_EXPRESSION:
			case ASTNode.YIELD_STATEMENT:
				return apiLevel >= AST.JLS14;
			case ASTNode.TEXT_BLOCK:
			case ASTNode.RECORD_DECLARATION:
				return isPreviewEnabled(apiLevel, previewEnabled);
		}
		return false;
	}

	private static boolean isPreviewEnabled(int apiLevel, boolean previewEnabled) {
		return (apiLevel == AST.JLS14 && previewEnabled);
	}

	public static boolean isSwitchExpressionSupported(AST ast) {
		return isNodeTypeSupportedinAST(ast, ASTNode.SWITCH_EXPRESSION);
	}

	public static boolean isYieldStatementSupported(AST ast) {
		return isNodeTypeSupportedinAST(ast, ASTNode.YIELD_STATEMENT);
	}

	public static boolean isTextBlockSupported(AST ast) {
		return isNodeTypeSupportedinAST(ast, ASTNode.TEXT_BLOCK);
	}

	public static boolean isRecordDeclarationSupported(AST ast) {
		return isNodeTypeSupportedinAST(ast, ASTNode.RECORD_DECLARATION);
	}

	public static boolean isRecordDeclarationSupported(int apiLevel, boolean previewEnabled) {
		return isNodeTypeSupportedinAST(apiLevel, previewEnabled, ASTNode.RECORD_DECLARATION);
	}

}