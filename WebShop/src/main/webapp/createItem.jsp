<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>

<html lang="en">

<head>
	
</head>

<body>
	<form action='/WebShop/api/item' method='post'>
		<select name="categoryId">
			<option value="null">Select Category</option>
			<%
				for (Category c : DBManager.getInstance().getCategories()) {
					out.println("<option value='" + c.getId() + "'>" + c.getName() + "</option>");
				}
			%>
		</select>
		<br />
		<input type='text' name='title' placeholder='Title' />
		<br />
		<textarea name='description' placeholder='Description'></textarea>
		<br />
		<input type='text' name='price' placeholder='Price' />
		<br />
		<button type='submit'>Save</button>
	</form>
</body>

</html>