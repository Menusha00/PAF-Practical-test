package com;

import java.sql.*;


public class Notification {
	
	private Connection connect(){
		
		Connection con = null;
				try
					{
					Class.forName("com.mysql.jdbc.Driver");
					//Provide the correct details: DBServer/DBName, username, password
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test1","root","Mlu1996@");
					}
				catch (Exception e){
					e.printStackTrace();
				}
		return con;
		
		}
	
	
	//insert
	public String insertNotification(String userType, String notificationType, String  status, String offerDesc)
    {
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{    
					return "Error while connecting to the database for inserting.";
				}
				
				// create a prepared statement
				String query = "insert into notifications"
						+"(`notID`,`nuserType`,`notifitype`,`nstatus`,`nofferdesc`)"
						 + " values (?, ?, ?, ?, ?)";
				
				 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, userType);
				 preparedStmt.setString(3, notificationType);
				 preparedStmt.setString(4, status);
				 preparedStmt.setString(5, offerDesc); 
	
				// execute the statement
				preparedStmt.execute();
				con.close();
				
				String newNotifications = readNotifications();
				output = "{\"status\":\"success\", \"data\": \"" +newNotifications + "\"}";
			}
				catch (Exception e)
			{
					output = "{\"status\":\"error\", \"data\": \"Error while inserting the notifications.\"}";
					System.err.println(e.getMessage());
			}
			return output;
    		}
  
	 //View
      public String readNotifications(){
		
		String output = "";
			try{
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database for reading."; 
			}
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>User Type</th>"
					+ "<th>Notification Type</th>"
					+ "<th>Status</th>"
					+ "<th>Notification Description</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
			
			String query = "select * from notifications";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				 String notID = Integer.toString(rs.getInt("notID"));
				 String nuserType = rs.getString("nuserType");
				 String notifitype = rs.getString("notifitype");
				 String nstatus = rs.getString("nstatus");
				 String nofferdesc = rs.getString("nofferdesc");
				 
				// Add into the html table
				output += "<tr><td><input id='hidNotIDUpdate'name='hidNotIDUpdate' type='hidden' value='" + notID+ "'>" + nuserType + "</td>";
				output += "<td>" + notifitype + "</td>";
				output += "<td>" + nstatus + "</td>";
				output += "<td>" + nofferdesc + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-notid='"+ notID + "'>" + "</td></tr>";
							
			}
			
			con.close();
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e){
				output = "Error while reading the notifications.";
				System.err.println(e.getMessage());
			}
		
		return output;	
      }
      
      
      //Update
      public String updateNotification(String ID, String userType, String notificationType, String status, String offerDesc) {
  		String output = "";
  		try {
  			Connection con = connect();
  			if (con == null) {
  				return "Error while connecting to the database for updating.";

  			}
  		    // create a prepared statement
  					String query = "UPDATE notifications SET nuserType=?,notifitype=?,nstatus=?,nofferdesc=? WHERE notID=?";
  					
  					 
  					PreparedStatement preparedStmt = con.prepareStatement(query);
  					 // binding values
  					 preparedStmt.setString(1, userType);
  					 preparedStmt.setString(2, notificationType);
  					 preparedStmt.setString(3, status);
  					 preparedStmt.setString(4, offerDesc);
  					 preparedStmt.setInt(5, Integer.parseInt(ID));
  				     // execute the statement
  					 preparedStmt.execute();
  					 con.close();
  					 
  					String newNotifications = readNotifications();
  					output = "{\"status\":\"success\", \"data\": \"" + newNotifications + "\"}";;
  				    } catch (Exception e) {
  					output = "{\"status\":\"error\", \"data\": \"Error while updating the notification.\"}";
  					System.err.println(e.getMessage());
  				    }
  				return output;
  			}
      
      
      //Delete
      public String deleteNotification(String notID) {
  		String output = "";
  		try {
  			Connection con = connect();
  			if (con == null) {

  				return "Error while connecting to the database for deleting.";
  			}
			// create a prepared statement
			String query = "delete from notifications where notID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(notID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newNotifications = readNotifications();
			output = "{\"status\":\"success\", \"data\": \"" + newNotifications + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the notification.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}
