--FORMAT PHONE NUMBERS 
update [Everest].[dbo].EVEREST_TEXTS_VERIZON$
set [Wireless number] = replace([Wireless number],'-','')
where [Wireless number] like '%-%'

update [Everest].[dbo].EVEREST_TEXTS_VERIZON$
set [To/from wireless number] = replace([To/from wireless number],'-','')
where [To/from wireless number] like '%-%'

--ALL BILLABLE TEXTS FROM VERIZON LOGS
SELECT DISTINCT [User name] AS [CM Name]
      ,CAST([Wireless number] AS FLOAT) AS [CM Phone Number]
	  ,Phone_Contact_Type
      ,CAST([To/from wireless number] AS FLOAT) AS [Contact Phone Number] , [Bill cycle date], [Message date/time] AS [Date/Time]
  INTO [Everest].[dbo].EVEREST_TEXTS_BILLABLE_VERIZON
  FROM [Everest].[dbo].[EVEREST_TEXTS_VERIZON$]
  JOIN [Everest].[dbo]._CM_CONTACT_
  ON [Wireless Number] = [CM Phone Number] and [To/from wireless number] = Phone_Number

--AGGREGATED COUNT OF TEXTS FROM VERIZON LOGS PER CM PER BILL CYCLE
SELECT [CM Name], [Bill cycle date], COUNT(*) AS [Number of Texts]
INTO [Everest].[dbo].EVEREST_TEXTS_BILLABLE_VERIZON_AGGREGATED
FROM [Everest].[dbo].EVEREST_TEXTS_BILLABLE_VERIZON
GROUP BY [CM Name], [Bill cycle date]
ORDER BY [CM Name]
