<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="mediateka">
	<xsl:output method="html" encoding="UTF-8"/>

	<xsl:template match="/">
		<html>
			<body>
				<h2>Черный список</h2>
				<table border="1">
					<tr>
						<th>Номер</th>
						<th>Комментарий</th>
					</tr>
					<xsl:for-each select="a:blackList/a:record">
					<tr>
					<td><xsl:value-of select="a:personID"/></td>
					<td><xsl:value-of select="a:comment"/></td>
					</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>