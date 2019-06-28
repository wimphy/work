#!/bin/bash
file=$1
while IFS='' read -r line || [[ -n "$line" ]]; do
  echo "scp /oic/view/pcbpel/$line xiaomiwa@slc11jrq.us.oracle.com:/scratch/xiaomiwa/view_storage/xiaomiwa_view_bug/pcbpel/$line"
  scp /oic/view/pcbpel/$line xiaomiwa@slc11jrq.us.oracle.com:/scratch/xiaomiwa/view_storage/xiaomiwa_view_bug/pcbpel/$line
done < "$file"
echo "... done"
