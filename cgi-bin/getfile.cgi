#!/bin/bash

echo -e "Content-Type: text/html ; charset=utf-8\n"

case "$REQUEST_METHOD" in    
       [gG][eE][tT])
            # TODO if article have & would crash    
            eval $(printf "%s" "${QUERY_STRING}&" | sed 's/\([^&]*\)=\([^&]*\)&/get_\1="\2" /g')
            ;;                                                        
        [pP][oO][sS][tT])
            # not support yet
            eval $(printf "%s" "${QUERY_STRING}&" | sed 's/\([^&]*\)=\([^&]*\)&/get_\1="\2" /g')
            QUERY_STRING_POST=`dd count=$CONTENT_LENGTH bs=1 2> /dev/null`"&"
            ;;
esac                                                                      

file_name="orig_text/${get_text_name}_${REMOTE_ADDR}"
article=${QUERY_STRING_POST}

echo ${article} > ${file_name}
