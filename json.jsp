<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%
	String callback = request.getParameter("callback");
	System.out.println(callback);
	String json = "{\"name\":\"leopold_json\",\"age\":26}";
	out.println(callback+"("+json+")");
%>