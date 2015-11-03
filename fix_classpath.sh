#!/bin/sh
# There was an issue with Eclipse not showing sources if org.eclipse.jst.j2ee.internal.web.container was too high up in the list for some reason.
# (See: http://stackoverflow.com/questions/10156847/how-to-tell-gradle-to-download-all-the-source-jars)
# This script fixes it but relies on some GNU/Linux libraries. You can use Cygwin if you want to run this script on a Windows machine.

grep -v 'path="org.eclipse.jst.j2ee.internal.web.container"' .classpath | sed 's#\(</classpath>\)#\t<classpathentry exported="true" kind="con" path="org.eclipse.jst.j2ee.internal.web.container"/>\n\1#' > .classpath_temp
mv .classpath_temp .classpath
