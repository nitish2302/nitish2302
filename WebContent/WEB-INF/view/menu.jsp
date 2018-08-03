<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Kalakaar</title>
</head>
<body>
	<form id="menuForm">



		<div id="selectItemsDiv">
			
			<p
				style="position: absolute; top: 0; width: 100%; text-align: center">
				<font size="6" color="brown" face="Droid Sans"><b><i><u>Kalakaar</u></i></b></font>
			</p>
			<br>
			<h1 class="amountClass">Total Amount</h1>
			<input type="text" disabled style="width: 10%" class="totalAmount"
				id="totalAmount" /><br>
			<br>
			<h1 class="amountClass">Discount</h1>
			<input type="text" style="width: 10%" class="totalAmount"
				id="discount" /> <input id="selectedTable" type="hidden"
				value="${tableNumber}" />
			<table style="width: 70; height: 2% border=" 1" id="selectedmenu">
				<tr>
					<th>NAME</th>
					<th>CODE</th>
					<th>PRICE</th>
					<th>TYPE</th>
					<th>QUANTITY</th>
					<th>TOTAL</th>
					<th>REMOVE</th>
				</tr>
				<tbody>
					<c:forEach items="${orderList}" var="orderItem" varStatus="item">
						<tr id="selectedItem_${item.index}">
							<td id="itemnameadd_${item.index}"
								name="itemnameadd_${item.index}">${orderItem.itemName}</td>
							<td id="itemCodeadd_${item.index}"
								name="itemCodeadd_${item.index}">${orderItem.itemCode}</td>
							<td id="priceadd_${item.index}" name="priceadd_${item.index}">
								${orderItem.price}</td>
							<td id="typeadd_${item.index}" name="typeadd_${item.index}">
								${orderItem.type}</td>
							<td><input type="text" id="quantityadd_${item.index}"
								name="quantityadd_${item.index}" style="width: 20%"
								value="${orderItem.quantity}"
								onblur="updateQuantity('${item.index}')" /></td>
							<td><input type="text" disabled id="total_${item.index}"
								name="total_${item.index}" style="width: 90%"
								value="${orderItem.total}" /></td>
							<td><input type="button" class="button" value="Remove"
								onclick="removeRow('${item.index}')" /></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>

			<span class="custom-dropdown big"> <select id="myselect"
				onchange="submitform()">
					<option value="1">Table 1</option>
					<option value="2">Table 2</option>
					<option value="3">Table 3</option>
					<option value="4">Table 4</option>
					<option value="5">Table 5</option>
					<option value="6">Table 6</option>
			</select>
			</span>

<!-- <input type="button" value="Download Bill" class="button" onclick="generateBill()"/> -->
<a href="#" onclick="downloadfile()">Download Bill</a>
		</div>
		<table id="example" class="display menu" style="width: 40; height: 2%">
			<thead>
				<tr>
					<b>
						<th>NAME</th>
						<th>CODE</th>
						<th>PRICE</th>
						<th>TYPE</th>
						<th>QUANTITY</th>
						<th>ADD ITEM</th>
					</b>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${menuList}" var="menuItem" varStatus="item">
					<tr id="menu_row_${item.index}" name="menu_row_${item.index}"
						height="20">
						<td id="itemname_${item.index}" name="itemname_${item.index}"
							height="20">${menuItem.itemName}</td>
						<td id="itemCode_${item.index}" name="itemCode_${item.index}"
							height="20">${menuItem.itemCode}</td>
						<td id="price_${item.index}" name="price_${item.index}"
							height="20">${menuItem.price}</td>
						<td id="type_${item.index}" name="type_${item.index}" height="20">${menuItem.type}</td>
						<td><input type="text" id="quantity_${item.index}"
							name="quantity_${item.index}" style="width: 50%" value="1"
							height="20" /></td>
						<td><input type="button" class="button"
							id="add_${item.index}" name="add_${item.index}" value="Add"
							onclick="addRow('${item.index}')" height="20" /></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</form>
</body>
</html>

<script>
	$(document).ready(function() {
		$('#example').DataTable({
			"pagingType" : "full_numbers",
			"pageLength" : 8
		});
		var tbl = $('#selectedTable').val();
		$("#myselect option").each(function(i) {
			if ($(this).val() == tbl) {
				$(this).attr("selected", true);
			}
		});

	});

	function addRow(index) {

		var codeAlreadyPresent = false;

		$('#selectedmenu > tbody  > tr').each(function() {

			var $tds = $(this).find('td');
			var itemcode = $('#itemCode_' + index).text();
			if ($tds.eq(1).text() == itemcode) {
				codeAlreadyPresent = true;
			}
		});

		if (codeAlreadyPresent) {
			alert("Item already added. Modify it's quatity");

		} else {

			var quantity = $('#quantity_' + index).val();
			var price = $('#price_' + index).text();

			var total = quantity * price;

			var item = {
				table_number : $("#myselect").val(),
				orders : [ {
					item_name : $('#itemname_' + index).text(),
					item_code : $('#itemCode_' + index).text(),
					price : $('#price_' + index).text(),
					type : $('#type_' + index).text(),
					quantity : $('#quantity_' + index).val(),
					total : total,

				} ]
			};

			$.ajax({
				url : 'addOrder',
				type : 'post',
				contentType : 'application/json',
				dataType : 'json',
				data : JSON.stringify(item),
				success : function(data) {

				},
			});

			if ($('#quantity_' + index).val() == 0) {
				alert("Quantity cannot be 0");
				return;
			}

			$('#selectedmenu')
					.append(
							'<tr id="selectedItem_'+index+'"><td id="itemnameadd_'+index+'" name="itemnameadd_'+index+'">'
									+ $('#itemname_' + index).text()
									+ '</td><td id="itemCodeadd_'+index+'" name="itemCodeadd_'+index+'">'
									+ $('#itemCode_' + index).text()
									+ '</td><td id="priceadd_'+index+'" name="priceadd_'+index+'">'
									+ $('#price_' + index).text()
									+ '</td><td id="typeadd_'+index+'" name="typeadd_'+index+'">'
									+ $('#type_' + index).text()
									+ '</td><td ><input type="text" id="quantityadd_'
									+ index
									+ '" name="quantityadd_'
									+ index
									+ '" style="width: 20%" value="'
									+ $('#quantity_' + index).val()
									+ '" onblur="updateQuantity('
									+ index
									+ ')" />'
									+ '<td><input type="text" disabled id="total_'
									+ index
									+ '"'
									+ '	name="total_'
									+ index
									+ '" style="width: 90%" /></td>'
									+ '</td><td ><input type="button" class="button" value="Remove" onclick="removeRow('
									+ index + ')"/></td></tr></table>');

			$('#total_' + index).val(total);

		}
	}

	function removeRow(index) {

		var item = {
			table_number : $("#myselect").val(),
			orders : [ {
				item_name : $('#itemnameadd_' + index).text(),
				item_code : $('#itemCodeadd_' + index).text(),
				price : $('#priceadd_' + index).text(),
				type : $('#typeadd_' + index).text(),
				quantity : $('#quantityadd_' + index).val()
			} ]
		};

		$.ajax({
			url : 'removeOrder',
			type : 'post',
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify(item),
			success : function(data) {

			},
		});

		$('#selectedItem_' + index).remove();
	}

	function updateQuantity(index) {

		var quantity = $('#quantityadd_' + index).val();
		var price = $('#priceadd_' + index).text();

		var total = quantity * price;
		$('#total_' + index).val(total);

		var item = {
			table_number : $("#myselect").val(),
			orders : [ {
				item_name : $('#itemnameadd_' + index).text(),
				item_code : $('#itemCodeadd_' + index).text(),
				price : $('#priceadd_' + index).text(),
				type : $('#typeadd_' + index).text(),
				quantity : $('#quantityadd_' + index).val(),
				total : total,
			} ]
		};

		$.ajax({
			url : 'modifyOrderQuatity',
			type : 'post',
			contentType : 'application/json',
			dataType : 'json',
			data : JSON.stringify(item),
			success : function(data) {

			},
		});
	}

	function openPop() {

		// Get the modal
		var modal = document.getElementById('myModal');

		// Get the button that opens the modal
		var btn = document.getElementById("myBtn");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];
		// When the user clicks the button, open the modal 

		modal.style.display = "block";

	}
	function closePop() {
		// Get the modal
		var modal = document.getElementById('myModal');

		// Get the button that opens the modal
		var btn = document.getElementById("myBtn");

		// Get the <span> element that closes the modal
		var span = document.getElementsByClassName("close")[0];
		modal.style.display = "none";
	}
	window.onclick = function(event) {
		var modal = document.getElementById('myModal');
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
	
 	window.downloadfile = function(e){
 		
 		var rowCount = $('#selectedmenu tr').length;
 		
 		
 		
 		if(rowCount==1){
 			
 			alert("Add items to generate bill");
 			return ;
 		}
 		 
 		if(confirm("Print bill and close table?")){
 		var tableNumber = $("#myselect").val();
 		
		window.location = 'generatePdf?tableNumber='+tableNumber;
		} 
 	}
	


	function submitform() {

		var tableNumber = $("#myselect").val();
		window.location.href = 'menu?tableNumber=' + tableNumber;
		window.form[0].submit();

	}
</script>