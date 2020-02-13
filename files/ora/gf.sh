#!/bin/bash
file=$1
while IFS='' read -r line || [[ -n "$line" ]]; do
  scp xiaomiwa@slc00bpt.us.oracle.com:/scratch/xiaomiwa/view_storage/xiaomiwa_adp_code/$line /Users/simwang/sim/oic/view/$line
done < "$file"
echo "... done"
