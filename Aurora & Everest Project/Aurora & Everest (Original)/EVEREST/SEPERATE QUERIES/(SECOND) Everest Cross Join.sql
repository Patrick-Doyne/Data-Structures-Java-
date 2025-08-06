
--TOTAL BILLABLE DATA 
SELECT  DISTINCT 
      [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
      ,[Phone_Contact_Type]
      ,[Phone_Number]
	  ,[Bill cycle date]
	  ,Date,Time,
		Duration
  INTO [Everest].[dbo]._CM_CONTACT_VERIZON_
  FROM [Everest].[dbo].[_CM_CONTACT_]
  JOIN [Everest].[dbo].VerizonData$
  ON [CM Phone Number] = [Wireless number] and [Phone_Number] = [In/out number]

  --TOTAL BILLABLE DATA AGGREGATED
  SELECT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(sum(Duration) / 60 AS NUMERIC(18,2)) AS [Total (IN HOURS)], sum(Duration) AS [Total (IN MINUTES)], COUNT(*) AS [Call Counter]
  INTO [Everest].[dbo]._CM_CONTACT_AGGREGATE_
  FROM [Everest].[dbo]._CM_CONTACT_VERIZON_
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]
  ORDER BY [CM Name]


