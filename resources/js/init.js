function unlockscreen() {
		var loading_overlay_div = document.getElementById("loading-overlay");
		loading_overlay_div.parentNode.removeChild(loading_overlay_div);
		var loading_message_div = document.getElementById("loading-message");
		loading_message_div.parentNode.removeChild(loading_message_div);
}

function genoption(array) {
	var option_obj=document.getElementById("category")
    //Create and append select list
    var selectList = document.createElement("select");
    selectList.id = "methodGetText";
    option_obj.appendChild(selectList);
    //Create and append the options
    for (var i = 0; i < array.length; i++) {
        var option = document.createElement("option");
        option.value = array[i][0];
        option.text = array[i][1];
        selectList.appendChild(option);
    }
    unlockscreen();
}

//var option_array = [['upload', 'Upload your own text'], ['ncbi', 'Search text from NCBI by keywords']]

//genoption(option_array);
unlockscreen();
