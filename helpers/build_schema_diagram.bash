#!/bin/bash
# You need to install http://www.graphviz.org and add its bin directory to PATH for this work. There's also some Cygwin magic to move the index.html file ;)
rm -r schema/*
java -jar lib/schemaSpy_5.0.0.jar -I "(spatial_ref_sys)|(geography_columns)|(geometry_columns)|(raster_columns)|(raster_overviews)" -norows -t pgsql -host localhost -o schema -db curtin_lims -u curtin_lims_user -p password -dp lib/postgresql-9.3-1103.jdbc3.jar -s public
sed "s/'\([a-zA-Z_\.\/]\+\)\.\(js\|css\|html\|xml\|txt\)/'schema\/\1.\2/g" schema/index.html > index.html
sed -i -e's/index\.html/..\/index.html/' $(find schema -name '*.html')
sed -i -e's/span> on [A-Z][a-z]\{2\} [A-Z][a-z]\{2\} [0-9]\+ [0-9:]\+ [A-Z]\+ [0-9]\{4\}</span> on DATE REMOVED</' index.html schema/*.html
sed -i -e's/>Analyzed at [A-Z][a-z]\{2\} [A-Z][a-z]\{2\} [0-9]\+ [0-9:]\+ [A-Z]\+ [0-9]\{4\}/>Analyzed at DATE REMOVED/' schema/tables/*.html
rm schema/index.html
