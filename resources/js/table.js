function changeBackgroundColor(row) {
	row.style.backgroundColor = "#CAE4D7" ;
}

function restoreBackgroundColor(row) {
	row.style.backgroundColor = "#F2F8F5" ;
}

function markRow(row) {
	clickobj = row.getElementsByClassName("checkbox")[0] ;
	if (clickobj.checked) {
		clickobj.checked = false ;
	}
	else {
		clickobj.checked = true ;
	}
}
