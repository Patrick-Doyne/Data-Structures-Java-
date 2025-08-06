--CLEAN VERIZON DATA TO COMPARE WITH CAP DATA
update [Everest].[dbo].VerizonDataRaw$
set [Wireless number] = replace([Wireless number],'-','')
where [Wireless number] like '%-%'

SELECT  DISTINCT [Account number]
      ,[Bill cycle date]
      ,CAST([Wireless number] AS FLOAT) AS [Wireless number]
      ,[User name]
      ,[Date]
      ,[Time]
      ,CAST([In/out number] AS FLOAT) AS [In/out number]
      ,[Rate]
      ,[Origination]
      ,[Destination]
      ,CAST(CAST([Duration] AS int) + (6- Duration%6)AS NUMERIC(18, 0)) AS Duration
  INTO [Everest].[dbo].VerizonData$
  FROM [Everest].[dbo].[VerizonDataRaw$]

