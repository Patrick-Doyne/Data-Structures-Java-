--UNBILLABLE VERIZON CALL LOGS AT ALL TIMES OF ONLY CM TO CM CALLS 
  SELECT  DISTINCT
	 [CM Name]
      ,[CM Phone Number]
      ,[Phone_Number]
	  ,[Bill cycle date]
	  ,Date,Time,
		Duration
		,[Phone_Contact_Type]
  INTO [Everest].[dbo]._UNBILLABLE_CM_CALLS_CM_VERIZON_
  FROM [Everest].[dbo].[_UNBILLABLE_CM_CALLS_CM_]
  JOIN Everest.dbo.VerizonData$
  ON [CM Phone Number] = [Wireless number] AND [Phone_Number] = [In/out number]

  --Aggregated unbillable calls from cm to another cm at all times
  SELECT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(sum(Duration) / 60 AS NUMERIC(18,2)) AS [Total (IN HOURS)], sum(Duration) AS [Total (IN MINUTES)], COUNT(*) AS [Call Count]
  INTO [Everest].[dbo]._UNBILLABLE_CM_CALLS_CM_AGGREGATE_
  FROM Everest.dbo._UNBILLABLE_CM_CALLS_CM_VERIZON_
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]
  ORDER BY [CM Name]

  -- ALL OTHER UNBILLABLE DATA AT ALL TIMES
  SELECT [User name] AS [CM Name] ,[Wireless number] AS [CM Phone Number] ,[In/out number] AS [Phone_Number],[Bill cycle date], Date, Time, Duration
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_No_CM_to_CM
  FROM Everest.dbo.VerizonData$
  WHERE [In/out number] NOT IN (select Phone_Number from [Everest].[dbo].EverestData$) AND [User name] != 'CHELSEA NETTEY' -- YOU CAN ADD ANY OTHER VOCATIONAL CASEMANAGERS TO EXCLUDE

  --ADD CONTACT TYPE AS A ROW FOR THE NEXT QUERY AFTER THIS ONE
  ALTER TABLE [Everest].[dbo].EVEREST_UNBILLABLE_No_CM_to_CM ADD 
   [Phone_Contact_Type] VARCHAR(20) DEFAULT 'N/A' NOT NULL;

   --TOTAL UNBILLABLE CALLS
  SELECT* 
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_TOTAL
  FROM [Everest].[dbo].EVEREST_UNBILLABLE_No_CM_to_CM
  UNION
  SELECT*
  FROM Everest.dbo._UNBILLABLE_CM_CALLS_CM_VERIZON_

  -- UNBILLABLE CALLS DURING WORK HOURS
  SELECT* 
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_DURING_HOURS
  FROM [Everest].[dbo].EVEREST_UNBILLABLE_TOTAL
  WHERE (Time > '08:00' and Time < '17:00') or Time = '08:00' or Time = '17:00'

    -- UNBILLABLE CALLS OUTSIDE WORK HOURS
  SELECT* 
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_OUTSIDE_HOURS
  FROM [Everest].[dbo].EVEREST_UNBILLABLE_TOTAL
  WHERE Time < '08:00' or Time > '17:00'

  --AGGREGATED UNBILLABLE DURING WORK HOURS
  SELECT [CM Name], [Bill cycle date], [Phone_Contact_Type], CAST(sum(Duration)/60 AS NUMERIC(18,2)) AS [TOTAL (IN HOURS)], sum(Duration) AS [TOTAL (IN MINUTES)], COUNT(*) AS[Call Counter]
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_DURING_HOURS_AGGREGATED
  FROM [Everest].[dbo].EVEREST_UNBILLABLE_DURING_HOURS
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]

  --AGGREGATED UNBILLABLE OUTSIDE WORK HOURS
  SELECT [CM Name], [Bill cycle date], [Phone_Contact_Type], CAST(sum(Duration)/60 AS NUMERIC(18,2)) AS [TOTAL (IN HOURS)], sum(Duration) AS [TOTAL (IN MINUTES)], COUNT(*) AS[Call Counter]
  INTO [Everest].[dbo].EVEREST_UNBILLABLE_OUTSIDE_HOURS_AGGREGATED
  FROM [Everest].[dbo].EVEREST_UNBILLABLE_OUTSIDE_HOURS
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]