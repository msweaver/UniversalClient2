<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
    
    <html>
    <body>
		<h2>Validator: <xsl:value-of select="Report/ReportHeader/ServiceName" /></h2>
        <h2>Status: <xsl:value-of select="Report/ReportHeader/ValidationStatus" /></h2>
        <br/>
        
        <h3>DocumentType: <xsl:value-of select="Report/ReportHeader/TypeOfDocument" /></h3>
        <h3>Date of Test: <xsl:value-of select="Report/ReportHeader/DateOfTest" /></h3>
        <h3>Time of Test: <xsl:value-of select="Report/ReportHeader/TimeOfTest" /></h3>
        <h3 style="color:red;">Result of Test: <xsl:value-of select="Report/ReportHeader/ResultOfTest" /></h3>
        <h3 style="font-weight:bold">Error Count: <xsl:value-of select="Report/ReportHeader/ErrorCount" /></h3>
        <br/>
        
        <xsl:for-each select="Report/Results">
        
            <h4>Specification: <xsl:value-of select="current()/@specification" /></h4>
            <br/>
            <table border="1">
            <tr bgcolor="#OOFFFFF">
                <th style="text-align:left">Issue No.</th>
                <th style="text-align:left">Message</th>
                <th style="text-align:left">Context</th>
                <th style="text-align:left">Test</th>
            </tr>
            <xsl:for-each select="current()/issue">
                <tr>
                    <td>
                        <xsl:number value="position()" format="1" />
                    </td>
                    <td>
                        <xsl:value-of select="current()/message" />
                    </td>
                    <td>
                        <xsl:value-of select="current()/context" />
                    </td>
                    <td>
                        <xsl:value-of select="current()/test" />
                    </td>
                </tr>
            </xsl:for-each>
            </table>
        </xsl:for-each>
        
        </body>
        </html>
        
	</xsl:template>
</xsl:stylesheet>
