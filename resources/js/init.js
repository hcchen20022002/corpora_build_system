function unlockscreen() {
		var loading_overlay_div = document.getElementById("loading-overlay");
		loading_overlay_div.parentNode.removeChild(loading_overlay_div);
		var loading_message_div = document.getElementById("loading-message");
		loading_message_div.parentNode.removeChild(loading_message_div);
}

unlockscreen();
