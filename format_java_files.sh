#!/bin/bash

trap "echo 'Script interrupted by user'; exit 1" SIGINT

LIST_OF_FILES=$(find . -type f -name "*.java")
ARRAY_OF_CHANGES=()
while IFS= read -r line; do
  ARRAY_OF_CHANGES+=("$line")
done <<< "$LIST_OF_FILES"
for i in "${ARRAY_OF_CHANGES[@]}"
do
  echo "Formatting: $i"
  java -jar google-java-format-1.21.0-all-deps.jar -r "$i"
done