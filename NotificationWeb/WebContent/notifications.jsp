<%@page import="com.Notification"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Notification Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/notifications.js"></script>

</head>
<body>
<div class="container">
	<div class="row" >
		<div class="col-6">
			<h1>Notification Management</h1>
			
			<form id="formNotification" name="formNotification" method="post" action="notifications.jsp">
			
			  User Type:
			  <input id="nuserType" name="nuserType" type="text" class="form-control form-control-sm">
			  <br>
				
			  Notification Type:
			  <input id="notifitype" name="notifitype" type="text" class="form-control form-control-sm">
			  <br>
			
			  Status:
			  <input id="nstatus" name="nstatus" type="text" class="form-control form-control-sm">
			  <br>
			
			  Notification Description:
			  <input id="nofferdesc" name="nofferdesc" type="text" class="form-control form-control-sm">
			  <br>
			  
			  <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
			  <input type="hidden" id="hidNotIDSave" name="hidNotIDSave" value="">
		  </form>
		  
		  <div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>
			
			<div id="divNotificationGrid">
				<%
					Notification notificationObj = new Notification();
					out.print(notificationObj.readNotifications());
				%>
			</div>
		</div>
	</div>
	</div>	
</body>
</html>