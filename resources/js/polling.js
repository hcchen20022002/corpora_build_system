//Insert screen lock css style into html document
document.head.innerHTML += '<style type="text/css">#overlay { position: absolute; top: 0; left: 0; right: 0; bottom: 0; background-color: #000; opacity: 0.7; }#message { position: absolute; width: 400px; height: 100px; line-height: 100px; background-color: #fff; text-align: center; font-size: 1.2em; left: 50%; top: 50%; margin-left: -200px; margin-top: -50px; }</style>';
document.body.innerHTML += '<div id="screenDiv"></div>';
//JavaScript variables
var load_str = "Please wait...&nbsp;&nbsp;&nbsp;&nbsp;"
var load_img = '<img src="resources/img/loading.gif" height="40%" align="middle" />'
var check_flag = 0;

//Screen lock/unlock functions
function polling(stat) {
	if ( stat == "lock" ) {
		checking = setInterval(function(){status_check()}, 5000);
	}
	else {
		clearInterval(checking);
	}
}

function screen_lock(divname) {
	var screen_node = document.getElementById('screenDiv');
	var newdiv = document.createElement('div');
	newdiv.setAttribute('class',divname);
	newdiv.setAttribute('id',divname);
	screen_node.appendChild(newdiv);
}

function screen_unlock() {
	check_flag = 0;
	$( ".overlay" ).remove();
	$( ".message" ).remove();
	polling("unlock");
	alert("Operation done.");
	document.location.reload(true)
}

function lock_setting() {
	check_flag = 1;
	screen_lock('overlay');
	screen_lock('message');
	document.getElementById("message").innerHTML = load_str + load_img;
	polling("lock");
}

function status_check() {
	$.ajax({
		type: "GET",
		url: "/cgi-bin/flag.cgi",
		data: "method=check",
		success: function(msg) {
				var flag_status = msg;
				if ( flag_status == 0 ) {
					if ( check_flag == 0 ) {
						lock_setting();
					}
				} else if( flag_status == 2 ) {
					alert('Operation faled.(Error2)');
				} else {
					screen_unlock();
				}
			},
		error: function() {
		  alert( "Query error." );
		}
	});
}

//Html document button associate
//Below is ajax preventDefault function
//$('#submitbutton').click(function (e) {
//	e.preventDefault();

//	lock_setting();
//})

$('#submitbutton').click(function (e) {
	lock_setting()}
)

$('#uninstallbutton').click(function (e) {
	lock_setting()}
)
