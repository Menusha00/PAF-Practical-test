$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateNotificationForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var method = ($("#hidNotIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "NotificationAPI",
		type : method,
		data : $("#formNotification").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onNotificationSaveComplete(response.responseText, status);
		}
	});
});

function onNotificationSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divNotificationGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidNotIDSave").val("");
	$("#formNotification")[0].reset();
}



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidNotIDSave").val($(this).closest("tr").find('#hidNotIDUpdate').val());
	$("#nuserType").val($(this).closest("tr").find('td:eq(0)').text());
	$("#notifitype").val($(this).closest("tr").find('td:eq(1)').text());
	$("#nstatus").val($(this).closest("tr").find('td:eq(2)').text());
	$("#nofferdesc").val($(this).closest("tr").find('td:eq(3)').text());
});

//REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
		$.ajax(
		{
			url : "NotificationAPI",
			type : "DELETE",
			data : "notID=" + $(this).data("notid"),
			dataType : "text",
			complete : function(response, status)
			{
				onNotificationDeleteComplete(response.responseText, status);
			}
		});
	});

function onNotificationDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divNotificationGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

function validateNotificationForm()
{
	// UserType
	if ($("#nuserType").val().trim() == "")
	{
		return "Insert User Type.";
	}
	
	// Notification Type
	if ($("#notifitype").val().trim() == "")
	{
		return "Insert Notification Type.";
	}
	
	//Status
	if ($("#nstatus").val().trim() == "")
	{
		return "Insert Valid status.";
	}
	
	// Notification Description
	if ($("#nofferdesc").val().trim() == "")
	{
		return "Insert Notification Description.";
	}
	
	return true;
	}
