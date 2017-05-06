#!/bin/sh
echo -e "Content-Type: text/html ; charset=utf-8\n"

read input
eval $(echo ${QUERY_STRING//&/ })
eval $(echo ${input//&/ })
STAT_PATH="/tmp/run.flg"

Get_func() {
	case $method in
		"lock")
			touch $STAT_PATH
			;;
		"unlock")
			rm $STAT_PATH
			;;
		"check")
			if [ -e $STAT_PATH ]; then
				if [ $(grep . $STAT_PATH | wc -l) -ge 1 ]; then
					echo 0
				else
					echo 2
				fi
			else
				echo 1
			fi
			;;
	esac
}

Get_func
