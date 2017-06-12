#!/bin/bash

echo -e "Content-Type: text/html ; charset=utf-8\n"

mv history.json history.json.old 
cp history.json.empty history.json
