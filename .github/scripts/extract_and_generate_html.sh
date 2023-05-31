#!/bin/bash

# Destination directory path
destination_dir="doc/gatling"

# Function to copy JS and CSS files
copy_js_and_css() {
  mkdir -p "${destination_dir}/js"
  mkdir -p "${destination_dir}/style"
  cp -rf "./loading/target/gatling/${1}/js" "${destination_dir}"
  cp -rf "./loading/target/gatling/${1}/style" "${destination_dir}"
}

# Function to perform replacements in HTML files
replace_in_html_files() {
  search=$1
  replace=$2
  find "${destination_dir}/${3}" -type f -name "*.html" -exec sed -i "" -e "s|$search|$replace|g" {} \;
}

# Read the contents of lastRun.txt and sort it
last_runs=($(sort -n < loading/target/gatling/lastRun.txt))

# Create the HTML template
template_file="./.github/scripts/template.html"
output_file="loading/target/gatling/summary.html"
cp "$template_file" "$output_file"

# Initialize the content variable
content=""

# Copy JS and CSS for the first run
copy_js_and_css "${last_runs[0]}"

# Iterate through the directories obtained from lastRun.txt
for run in "${last_runs[@]}"; do
  # Extract name and date from the entry
  report_name="${run%-*}"
  timestamp="${run#*-}"
  year="${timestamp:0:4}"
  month="${timestamp:4:2}"
  day="${timestamp:6:2}"
  hour="${timestamp:8:2}"
  minute="${timestamp:10:2}"
  second="${timestamp:12:2}"

  formatted_date="${year}-${month}-${day} ${hour}:${minute}:${second}"

  # Construct the desired output
  content+="            <li><h2>Results for <a href='./$run/index.html'>$report_name</a> at $formatted_date</h2></li>"

  # Copy .html and .log files to the destination directory
  mkdir -p "${destination_dir}/$run"
  cp -rf "./loading/target/gatling/$run"/* "${destination_dir}/$run"

  # Remove unnecessary directories
  rm -rf "${destination_dir}/$run"/js
  rm -rf "${destination_dir}/$run"/style

  # Perform replacements in HTML files
  replace_in_html_files "src=\"js/" "src=\"../js/" "$run"
  replace_in_html_files "\"style/" "\"../style/" "$run"

  # Add "SUMMARY" link back to the Summary
  replace_in_html_files '<div class="sous-menu">' '<div class="sous-menu">\n\t\t\t\t\t\t\t\t<div class="item "><a href="../summary.html">SUMMARY</a></div>' "$run"
done

# Replace {{CONTENT}} in the template with the generated content
sed -i "s|{{CONTENT}}|$content|g" "$output_file"

# Close the HTML tags (if necessary)
# No changes needed

# Copy the summary.html file to the destination directory
cp "$output_file" "${destination_dir}"