#!/bin/sh

echo -e "Content-Type: text/html ; charset=utf-8\n"

underline_artical(){
    art=$1
    keys=$2
    for key in `echo ${keys}` ;do
        art=$(echo ${art} | sed -e "s/\([ |\.|,|\(]\)\(${key}\)\([ |\.|,|\)]\\)/\<a class=\"string_link\" href\=\"\#\2\"\>\1\2\3\<\/a\>/g")
    done
    echo ${art}
}

wordnetSearch() {
	local search_key=$1

	for Type in 'n' 'v' 'a' 'r' ; do
        result=$(wordnet ${search_key} -syns${Type} | sed 's/$/<br>/g')
        if [ -z ${result} ]; then
            continue
        fi
        cat << EOF
		<tr class="string_row" onmouseover="changeBackgroundColor(this)" onmouseout="restoreBackgroundColor(this)" onclick="markRow(this)" ><td class="key">
        $(echo ${result})
        </td></tr>
EOF
	done
}

case "$REQUEST_METHOD" in    
       [gG][eE][tT])
            # TODO if artical have & would crash    
            eval $(printf "%s" "${QUERY_STRING}&" | sed 's/\([^&]*\)=\([^&]*\)&/get_\1="\2" /g')
            ;;                                                        
       [pP][oO][sS][tT])                                                  
            # not supported                                            
            ;;                                                        
esac                                                                      

all_key=$(echo $get_key | sed 's/,/ /g')
artical=${get_artical}
if [ $all_key == '']; then
	echo "Please input string key you want to search"
	exit
fi

cat << EOF
<h3>
Keys which be search:<br>
$(echo ${all_key} | sed "s/\([a-zA-Z0-9_]*\)/\<a class=\"string_link\" href\=\"\#\1\"\>\1\<\/a\> \ <br>/g")
Input article: <br>
</h3>
$(underline_artical "${artical}" "${all_key}")
<br>
EOF

IFS=${IFS}','
for search_key in $all_key ; do
	cat << EOF
<table class="tableborder">
	<caption class="key_title"><h4><a name="$search_key">$search_key</a></h4></caption>
	<thead><tr class="table border"><th class="table_thead_value" width="500">value</th></tr></thead>
	<tbody>
$(wordnetSearch $search_key)
	<tr class="top_link"><td colspan=3><a href='javascript:scroll(0,0)' title='Top'><br>Go Top<br></a></td></tr>
	</tbody>
</table>
EOF
done
unset IFS
