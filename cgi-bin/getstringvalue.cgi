#!/bin/bash

echo -e "Content-Type: text/html ; charset=utf-8\n"

underline_article(){
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
            # TODO if article have & would crash    
            eval $(printf "%s" "${QUERY_STRING}&" | sed 's/\([^&]*\)=\([^&]*\)&/get_\1="\2" /g')
            ;;                                                        
        [pP][oO][sS][tT])
            # not support yet
            ;;
esac                                                                      

all_key=$(echo $get_key)
file_name=${get_text_name}

stop_word=${get_stop_word}
stemming=${get_stemming}
symbol=${get_symbol}
tagger=${get_tagger}

if [ $all_key == '' ]; then
	echo "Please input string key you want to search"
	exit
fi

if [ ! -z ${file_name} ] ;then
    tmp_text_name="tmp_text/${get_text_name}_${REMOTE_ADDR}"
    time=$(date +%-m_%-d_%Y_%H%M%S)
    orig_text_name="orig_text/${get_text_name}_${REMOTE_ADDR}"
    corpora_text_name="corpora_text/${get_text_name}_${REMOTE_ADDR}_${time}"

    cp ${orig_text_name} ${tmp_text_name}
    #cut line and search keyword
    /usr/bin/java split_string "${all_key}" ${orig_text_name} > ${tmp_text_name}
    #create corpora
    if [ 'true' == "${stop_word}" ] ;then
        echo "Remove stop words!<br>"
        /usr/bin/java NLP_FactoryMain 100 ${tmp_text_name} > ${tmp_text_name}.tmp
        mv ${tmp_text_name}.tmp ${tmp_text_name}
    fi
    if [ 'true' == "${stemming}" ] ;then
        echo "Stemming words!<br>"
        /usr/bin/java NLP_FactoryMain 001 ${tmp_text_name} > ${tmp_text_name}.tmp
        mv ${tmp_text_name}.tmp ${tmp_text_name}
    fi
    if [ 'true' == "${symbol}" ] ;then
        echo "Remove symbol!<br>"
        /usr/bin/java NLP_FactoryMain 010 ${tmp_text_name} > ${tmp_text_name}.tmp
        mv ${tmp_text_name}.tmp ${tmp_text_name}
    fi
    if [ 'true' == "${tagger}" ] ;then
        echo "Tagging word!<br>"
        /usr/bin/python3 create_corpus/stanford_parser_tool.py tagger -I ${tmp_text_name} -O ${tmp_text_name}
        mv ${tmp_text_name}.tmp ${tmp_text_name}
    fi

    mv ${tmp_text_name} ${corpora_text_name}
    java -jar JsonFileCreater.jar ${orig_text_name} ${corpora_text_name} "${all_key}" 1 > /dev/null

    cat << EOF
<h3>
Download: <a href=${corpora_text_name}>Corpus File</a><br>
<br>
<br>
EOF
fi

cat << EOF
Keys which be search:<br>
$(echo ${all_key} | sed "s/\([a-zA-Z0-9_]*\)/\<a class=\"string_link\" href\=\"\#\1\"\>\1\<\/a\> \ <br>/g" | sed "s/,//g")
</h3>
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
