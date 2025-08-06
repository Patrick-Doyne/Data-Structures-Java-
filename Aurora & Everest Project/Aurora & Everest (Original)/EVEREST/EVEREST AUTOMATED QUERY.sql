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

--CREATE A TABLE FOR EACH CONTACT TYPE
DELETE FROM [Everest].[dbo].EverestData$
WHERE caseid IS NULL
   OR Case_Number IS NULL
   OR Claim_Number IS NULL
   OR Phone_Contact_Type IS NULL
   OR Phone_Number IS NULL
   OR Phone_Type IS NULL
   OR Name IS NULL

SELECT 
       [caseid] AS [CM caseid]
      ,[Phone_Contact_Type] AS [CM Contact]
      ,[Phone_Number] AS [CM Phone Number]
       ,[Name] as [CM Name]
  INTO [Everest].[dbo].CASE_MANAGERS
  FROM [Everest].[dbo].[EverestData$]
  Where Phone_Contact_Type = 'Case_manager' and Name != 'Chelsey Nettey' -- OR ANY OTHER VOCATIONAL MANAGERS


  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Everest].[dbo].CLAIMANTS
  FROM [Everest].[dbo].[EverestData$]
  WHERE Phone_Contact_Type = 'Claimant'
  

  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Everest].[dbo].ADJUSTERS
  FROM [Everest].[dbo].[EverestData$]
  WHERE Phone_Contact_Type = 'Adjuster'
  

    SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number]
  INTO [Everest].[dbo].PROVIDERS
  FROM [Everest].[dbo].[EverestData$]
  WHERE Phone_Contact_Type = 'Provider'


  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number]
  INTO [Everest].[dbo].ATTORNIES
  FROM [Everest].[dbo].[EverestData$]
  WHERE Phone_Contact_Type = 'Attorney'
  

  SELECT 
       [caseid] 
      ,[Phone_Contact_Type]
      ,[Phone_Number]
  INTO [Everest].[dbo].EMPLOYERS
  FROM [Everest].[dbo].[EverestData$]
  WHERE Phone_Contact_Type = 'Employer'

 

--LINKS CASE MANAGERS TO ALL CONTATCS WITH THE SAME CASE ID UNDER ONE TABLE
  SELECT [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  INTO [Everest].[dbo].CM_CONTACT
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  JOIN [Everest].[dbo].CLAIMANTS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  JOIN [Everest].[dbo].ADJUSTERS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  JOIN [Everest].[dbo].ATTORNIES 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  JOIN [Everest].[dbo].PROVIDERS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  JOIN [Everest].[dbo].EMPLOYERS 
  ON [CM caseid] = [caseid]

--CREATES ONE LARGE TABLE WITH ALL MATCHING EVEREST CONTACT LIST DATA AND VERIZON DATA
SELECT 
      [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
      ,[Phone_Contact_Type]
      ,[Phone_Number]
	  ,[Bill cycle date],Date,Time,
		Duration 
  INTO [Everest].[dbo].CM_Aggregated
  FROM [Everest].[dbo].[CM_Contact]
  JOIN [Everest].[dbo].VerizonData$
  ON [CM Phone Number] = [Wireless number] and [Phone_Number] = [In/out number]

--CREATES TABLE OF ALL POSSIBLE CM TO CM INTERACTIONS
SELECT DISTINCT 
      a.[CM Contact]
      ,a.[CM Phone Number]
      ,a.[CM Name]
      ,b.[CM Contact] AS [Phone_Contact_Type]
      ,b.[CM Phone Number] AS [Phone_Number]
  INTO [Everest].[dbo]._UNBILLABLE_CM_CALLS_CM_
  FROM [Everest].[dbo].[CASE_MANAGERS] a
  INNER JOIN [Everest].[dbo].CASE_MANAGERS b
  ON a.[CM Phone Number] != b.[CM Phone Number]
--CREATES ON LARGE TABLE OF ALL POSSIBLE CM TO CONTACT RELATIONSHIPS EVEN IF THEY DON'T HAVE MATCHING CASE ID'S	  
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Everest].[dbo]._CM_CONTACT_ 
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Everest].[dbo].CLAIMANTS 
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number] 
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number] 
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Everest].[dbo].ADJUSTERS
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Everest].[dbo].ATTORNIES 
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Everest].[dbo].PROVIDERS
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Everest].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Everest].[dbo].EMPLOYERS

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


  --Billable activity for contatcs not within case managers assigned cases
  SELECT*
  INTO [Everest].[dbo].EVEREST_BILLABLE_NOT_CM_CASE
  FROM [Everest].[dbo]._CM_CONTACT_VERIZON_
  EXCEPT 
  SELECT*
  FROM [Everest].[dbo].CM_Aggregated

  --Billable activity for contatcs not within case managers assigned cases AGGREGATED per CM/ Contact/Bill cycle
  SELECT DISTINCT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(SUM(Duration)/60 AS NUMERIC(18,2))AS [TOTAL BILLABLE HOURS], SUM(Duration) AS [TOTAL BILLABLE MINUTES], COUNT(*) AS [Call Count] 
  INTO [Everest].[dbo].Everest_BILLABLE_NOT_CM_CASE_AGGREGATED
  FROM [Everest].[dbo].Everest_BILLABLE_NOT_CM_CASE
  GROUP BY
  [CM Name],[Phone_Contact_Type], [Bill cycle date]
  ORDER BY [CM Name], [Bill cycle date]

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

  --SEPERATE DATA BY MONTH THEN AGGREGATE
Select *
INTO [Everest].[dbo].Everest_Case_Any_Place_July
FROM [Everest].[dbo].EverestCaseAnyPlace$
Where Activity_Date >= '2024-07-01' and Activity_date <='2024-07-31'
Order by Activity_Date


ALTER TABLE [Everest].[dbo].Everest_Case_Any_Place_July ADD [Bill cycle date] VARCHAR(20) 

UPDATE [Everest].[dbo].Everest_Case_Any_Place_July
set [Bill cycle date] = '08/01/2024'

Select *
INTO [Everest].[dbo].Everest_Case_Any_Place_June
FROM [Everest].[dbo].EverestCaseAnyPlace$
Where Activity_Date >= '2024-06-01' and Activity_date <='2024-06-30'
Order by Activity_Date

ALTER TABLE [Everest].[dbo].Everest_Case_Any_Place_June ADD [Bill cycle date] VARCHAR(20) 

UPDATE [Everest].[dbo].Everest_Case_Any_Place_June
set [Bill cycle date] = '07/01/2024'

SELECT * 
INTO [Everest].[dbo].Everest_Case_Any_place_June_and_July
FROM [Everest].[dbo].Everest_Case_Any_Place_June 
UNION 
SELECT * 
FROM [Everest].[dbo].Everest_Case_Any_Place_July 

SELECT Manager, [Bill Cycle date],
    CAST(sum(TotalMinutes)/60 AS NUMERIC(18,2)) AS [TIME(IN HOURS)],
	sum(TotalMinutes) AS [TOTAL(IN MINUTES)]
INTO [Everest].[dbo].Everest_Case_Any_place_June_and_July_Aggregated
FROM [Everest].[dbo].Everest_Case_Any_place_June_and_July
GROUP BY Manager, [Bill cycle date]
ORDER BY Manager

-- AGGREGATE DATA BY CM AND BILL CYCLE
SELECT [CM Name], [Bill cycle date], SUM([Total (IN HOURS)]) AS [Total (IN HOURS)], SUM([Total (IN MINUTES)]) AS [Total (IN MINUTES)]
INTO [Everest].[dbo].TOTAL_EVEREST_BILLABLE_AGGREGATED
FROM [Everest].[dbo]._CM_CONTACT_AGGREGATE_
GROUP BY [CM Name], [Bill cycle date]

--CREATE VARIANCE TABLE [CAP - VERIZON] (POSITIVE VALUES INDICATE OVERBILLING)
SELECT DISTINCT b.[Manager], b.[Bill cycle date], b.[TIME(IN HOURS)] - a.[Total (IN HOURS)] AS [DIFFERENCE(HOURS)], b.[TOTAL(IN MINUTES)] - a.[Total (IN MINUTES)] AS [DIFFERENCE(MINUTES)]
INTO [Everest].[dbo].EVEREST_CAP_DIFFERENCE
FROM [Everest].[dbo].TOTAL_EVEREST_BILLABLE_AGGREGATED a
JOIN [Everest].[dbo].Everest_Case_Any_place_June_and_July_Aggregated b
ON [CM Name] = b.Manager and a.[Bill cycle date] = b.[Bill Cycle date]
ORDER BY  [Bill cycle date], [Manager]

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
