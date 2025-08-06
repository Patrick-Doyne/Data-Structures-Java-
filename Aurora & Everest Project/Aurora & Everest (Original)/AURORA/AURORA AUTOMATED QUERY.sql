--CLEAN VERIZON DATA TO COMPARE WITH CAP DATA
update [Aurora].[dbo].VerizonDataRaw$
set [Wireless number] = replace([Wireless number],'-','')
where [Wireless number] like '%-%'

SELECT  DISTINCT [Column1]
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
  INTO [Aurora].[dbo].VerizonData$
  FROM [Aurora].[dbo].[VerizonDataRaw$]
 

--CREATE A TABLE FOR EACH CONTACT TYPE
DELETE FROM [Aurora].[dbo].AuroraData$
WHERE caseid IS NULL
   OR Case_Number IS NULL
   OR Claim_Number IS NULL
   OR Phone_Contact_Type IS NULL
   OR Phone_Number IS NULL
   OR Phone_Type IS NULL
   OR Name IS NULL;

SELECT 
       [caseid] AS [CM caseid]
      ,[Phone_Contact_Type] AS [CM Contact]
      ,[Phone_Number] AS [CM Phone Number]
       ,[Name] as [CM Name]
  INTO [Aurora].[dbo].CASE_MANAGERS
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Case_manager' AND Name != 'Nettey, Chelsey' -- OR ANY OTHER VOCATIONAL MANAGER


  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Aurora].[dbo].CLAIMANTS
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Claimant'
  

  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Aurora].[dbo].ADJUSTERS
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Adjuster'
  

    SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number]
  INTO [Aurora].[dbo].PROVIDERS
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Provider'


  SELECT 
       [caseid] 
      ,[Phone_Contact_Type] 
      ,[Phone_Number]
  INTO [Aurora].[dbo].ATTORNIES
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Attorney'
  

  SELECT 
       [caseid] 
      ,[Phone_Contact_Type]
      ,[Phone_Number]
  INTO [Aurora].[dbo].EMPLOYERS
  FROM [Aurora].[dbo].[AuroraData$]
  WHERE Phone_Contact_Type = 'Employer'

 
--LINKS CASE MANAGERS TO ALL CONTATCS WITH THE SAME CASE ID UNDER ONE TABLE
  SELECT [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  INTO [Aurora].[dbo].CM_CONTACT
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  JOIN [Aurora].[dbo].CLAIMANTS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  JOIN [Aurora].[dbo].ADJUSTERS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  JOIN [Aurora].[dbo].ATTORNIES 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  JOIN [Aurora].[dbo].PROVIDERS 
  ON [CM caseid] = [caseid]
  UNION
  SELECT  [CM caseid]
      ,[CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number]
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  JOIN [Aurora].[dbo].EMPLOYERS 
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
  INTO [Aurora].[dbo].CM_Aggregated
  FROM [Aurora].[dbo].[CM_CONTACT]
  JOIN [Aurora].[dbo].VerizonData$
  ON [CM Phone Number] = [Wireless number] and [Phone_Number] = [In/out number]

  --CREATES TABLE OF ALL POSSIBLE CM TO CM INTERACTIONS
SELECT DISTINCT 
      a.[CM Contact]
      ,a.[CM Phone Number]
      ,a.[CM Name]
      ,b.[CM Contact] AS [Phone_Contact_Type]
      ,b.[CM Phone Number] AS [Phone_Number]
	  
  INTO [Aurora].[dbo]._UNBILLABLE_CM_CALLS_CM_
  FROM [Aurora].[dbo].[CASE_MANAGERS] a
  INNER JOIN [Aurora].[dbo].CASE_MANAGERS b
  ON a.[CM Phone Number] != b.[CM Phone Number]
 
 --CREATES ON LARGE TABLE OF ALL POSSIBLE CM TO CONTACT RELATIONSHIPS EVEN IF THEY DON'T HAVE MATCHING CASE ID'S	  
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  INTO [Aurora].[dbo]._CM_CONTACT_ 
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  CROSS JOIN CLAIMANTS 
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number] 
      ,[CM Name]
	  ,[Phone_Contact_Type]
      ,[Phone_Number] 
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Aurora].[dbo].ADJUSTERS
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Aurora].[dbo].ATTORNIES 
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Aurora].[dbo].PROVIDERS
  UNION
  SELECT DISTINCT [CM Contact]
      ,[CM Phone Number]
      ,[CM Name]
	  ,[Phone_Contact_Type] 
      ,[Phone_Number] 
  FROM [Aurora].[dbo].[CASE_MANAGERS]  
  CROSS JOIN [Aurora].[dbo].EMPLOYERS

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
  INTO [Aurora].[dbo]._CM_CONTACT_VERIZON_
  FROM [Aurora].[dbo].[_CM_CONTACT_]
  JOIN [Aurora].[dbo].VerizonData$
  ON [CM Phone Number] = [Wireless number] AND [Phone_Number] = [In/out number]

  --TOTAL BILLABLE DATA AGGREGATED
  SELECT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(sum(Duration) / 60 AS NUMERIC(18,2)) AS [Total (IN HOURS)], sum(Duration) AS [Total (IN MINUTES)], COUNT(*) AS [Call Counter]
  INTO [Aurora].[dbo]._CM_CONTACT_AGGREGATE_
  FROM [Aurora].[dbo]._CM_CONTACT_VERIZON_
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]
  ORDER BY [CM Name]


  --Billable activity for contatcs not within case managers assigned cases
  SELECT*
  INTO [Aurora].[dbo].AURORA_BILLABLE_NOT_CM_CASE
  FROM [Aurora].[dbo]._CM_CONTACT_VERIZON_
  EXCEPT 
  SELECT*
  FROM [Aurora].[dbo].CM_Aggregated

  --Billable activity for contatcs not within case managers assigned cases AGGREGATED per CM
  SELECT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(SUM(Duration)/60 AS NUMERIC(18,2))AS [TOTAL BILLABLE HOURS], SUM(Duration) AS [TOTAL BILLABLE MINUTES], COUNT(*) AS [Call Count]
  INTO [Aurora].[dbo].AURORA_BILLABLE_NOT_CM_CASE_AGGREGATED
  FROM [Aurora].[dbo].AURORA_BILLABLE_NOT_CM_CASE
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
  INTO [Aurora].[dbo]._UNBILLABLE_CM_CALLS_CM_VERIZON_
  FROM [Aurora].[dbo].[_UNBILLABLE_CM_CALLS_CM_]
  JOIN [Aurora].[dbo].VerizonData$
  ON [CM Phone Number] = [Wireless number] and [Phone_Number] = [In/out number]
  
  --Aggregated unbillable calls from cm to another cm at all times
  SELECT [CM Name], [Bill cycle date],[Phone_Contact_Type], CAST(sum(Duration)/60 AS NUMERIC(18,2)) AS [TOTAL (IN HOURS)], sum(Duration) AS [Total (IN MINUTES)]
  INTO [Aurora].[dbo]._UNBILLABLE_CM_CALLS_CM_AGGREGATE_
  FROM [Aurora].[dbo]._UNBILLABLE_CM_CALLS_CM_VERIZON_
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]
  ORDER BY [CM Name]

  -- ALL OTHER UNBILLABLE DATA AT ALL TIMES
  SELECT [User name] AS [CM Name] ,[Wireless number] AS [CM Phone Number] ,[In/out number] AS [Phone_Number],[Bill cycle date], Date, Time, Duration--sum(Duration) AS [Total Unbillable Duration]
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_No_CM_to_CM
  FROM [Aurora].[dbo].VerizonData$
  WHERE [In/out number] NOT IN (select Phone_Number from [Aurora].[dbo].AuroraData$) AND [User name] != 'CHELSEA NETTEY'

  --ADD CONTACT TYPE AS A ROW FOR THE NEXT QUERY AFTER THIS ONE
  ALTER TABLE [Aurora].[dbo].AURORA_UNBILLABLE_No_CM_to_CM ADD 
   [Phone_Contact_Type] VARCHAR(20) DEFAULT 'N/A' NOT NULL;

  --TOTAL UNBILLABLE CALLS
  SELECT* 
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_TOTAL
  FROM [Aurora].[dbo].AURORA_UNBILLABLE_No_CM_to_CM
  UNION
  SELECT*
  FROM [Aurora].[dbo]._UNBILLABLE_CM_CALLS_CM_VERIZON_

  -- UNBILLABLE CALLS DURING WORK HOURS
  SELECT* 
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_DURING_HOURS
  FROM [Aurora].[dbo].AURORA_UNBILLABLE_TOTAL
  WHERE (Time > '08:00' AND Time < '17:00') OR Time = '08:00' or Time = '17:00'

  -- UNBILLABLE CALLS OUTSIDE WORK HOURS
  SELECT* 
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_OUTSIDE_HOURS
  FROM [Aurora].[dbo].AURORA_UNBILLABLE_TOTAL
  WHERE Time < '08:00' OR Time > '17:00'
   
  --AGGREGATED UNBILLABLE DURING WORK HOURS
  Select [CM Name], [Bill cycle date], [Phone_Contact_Type], CAST(sum(Duration)/60 AS NUMERIC(18,2)) AS [TOTAL (IN HOURS)], sum(Duration) AS [TOTAL (IN MINUTES)]
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_DURING_HOURS_AGGREGATED
  FROM [Aurora].[dbo].AURORA_UNBILLABLE_DURING_HOURS
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]
   
  --AGGREGATED UNBILLABLE OUTSIDE WORK HOURS
  Select [CM Name], [Bill cycle date], [Phone_Contact_Type], CAST(sum(Duration)/60 AS NUMERIC(18,2)) AS [TOTAL (IN HOURS)], sum(Duration) AS [TOTAL (IN MINUTES)]
  INTO [Aurora].[dbo].AURORA_UNBILLABLE_OUTSIDE_HOURS_AGGREGATED
  FROM [Aurora].[dbo].AURORA_UNBILLABLE_OUTSIDE_HOURS
  GROUP BY [CM Name], [Bill cycle date], [Phone_Contact_Type]

  --SEPERATE DATA BY MONTH THEN AGGREGATE
Select *
INTO [Aurora].[dbo].Aurora_Case_Any_Place_July
FROM [Aurora].[dbo].AuroraCaseAnyPlace$
Where Activity_Date >= '2024-07-01' and Activity_date <='2024-07-30'
Order by Activity_Date


ALTER TABLE [Aurora].[dbo].Aurora_Case_Any_Place_July ADD [Bill cycle date] VARCHAR(20) 

UPDATE [Aurora].[dbo].Aurora_Case_Any_Place_July
set [Bill cycle date] = '07/30/2024'

Select *
INTO [Aurora].[dbo].Aurora_Case_Any_Place_August
FROM [Aurora].[dbo].AuroraCaseAnyPlace$
Where Activity_Date >= '2024-07-31' and Activity_date <='2024-08-30'
Order by Activity_Date

ALTER TABLE [Aurora].[dbo].Aurora_Case_Any_Place_August ADD [Bill cycle date] VARCHAR(20) 

UPDATE [Aurora].[dbo].Aurora_Case_Any_Place_August
set [Bill cycle date] = '08/30/2024'

SELECT * 
INTO [Aurora].[dbo].Aurora_Case_Any_place_July_and_August
FROM [Aurora].[dbo].Aurora_Case_Any_Place_July 
UNION 
SELECT * 
FROM [Aurora].[dbo].Aurora_Case_Any_Place_August 

SELECT Manager, [Bill Cycle date],
    CAST(sum(TotalMinutes)/60 AS NUMERIC(18,2)) AS [TIME(IN HOURS)],
	sum(TotalMinutes) AS [TOTAL(IN MINUTES)]
INTO [Aurora].[dbo].Aurora_Case_Any_place_July_and_August_Aggregated
FROM [Aurora].[dbo].Aurora_Case_Any_place_July_and_August
GROUP BY Manager, [Bill cycle date]
ORDER BY Manager

--
-- AGGREGATE DATA BY CM AND BILL CYCLE
SELECT [CM Name], [Bill cycle date], SUM([Total (IN HOURS)]) AS [Total (IN HOURS)], SUM([Total (IN MINUTES)]) AS [Total (IN MINUTES)]
INTO [Aurora].[dbo].TOTAL_AURORA_BILLABLE_AGGREGATED
FROM [Aurora].[dbo]._CM_CONTACT_AGGREGATE_
GROUP BY [CM Name], [Bill cycle date]

--CREATE VARIANCE TABLE [CAP - VERIZON] (POSITIVE VALUES INDICATE OVERBILLING)
SELECT DISTINCT b.[Manager], b.[Bill cycle date], b.[TIME(IN HOURS)] - a.[Total (IN HOURS)] AS [DIFFERENCE(HOURS)], b.[TOTAL(IN MINUTES)] - a.[Total (IN MINUTES)] AS [DIFFERENCE(MINUTES)]
INTO [Aurora].[dbo].AURORA_CAP_DIFFERENCE
FROM [Aurora].[dbo].TOTAL_AURORA_BILLABLE_AGGREGATED a
JOIN [Aurora].[dbo].Aurora_Case_Any_place_July_and_August_Aggregated b
ON [CM Name] = b.Manager and a.[Bill cycle date] = b.[Bill Cycle date]
ORDER BY  [Bill cycle date], [Manager]

--FORMAT PHONE NUMBERS
update [Aurora].[dbo].AURORA_TEXTS_VERIZON$
set [Wireless number] = replace([Wireless number],'-','')
where [Wireless number] like '%-%'

update [Aurora].[dbo].AURORA_TEXTS_VERIZON$
set [To/from wireless number] = replace([To/from wireless number],'-','')
where [To/from wireless number] like '%-%'

--ALL BILLABLE TEXTS FROM VERIZON LOGS
SELECT DISTINCT [User name] AS [CM Name]
      ,CAST([Wireless number] AS FLOAT) AS [CM Phone Number]
	  ,Phone_Contact_Type
      ,CAST([To/from wireless number] AS FLOAT) AS [Contact Phone Number] , [Bill cycle date], [Message date/time] AS [Date/Time]
  INTO [Aurora].[dbo].AURORA_TEXTS_BILLABLE_VERIZON
  FROM [Aurora].[dbo].[AURORA_TEXTS_VERIZON$]
  JOIN [Aurora].[dbo]._CM_CONTACT_
  ON [Wireless Number] = [CM Phone Number] and [To/from wireless number] = Phone_Number

--AGGREGATED COUNT OF TEXTS FROM VERIZON LOGS PER CM PER BILL CYCLE
SELECT [CM Name], [Bill cycle date], COUNT(*) AS [Number of Texts]
INTO [Aurora].[dbo].AURORA_TEXTS_BILLABLE_VERIZON_AGGREGATED
FROM [Aurora].[dbo].AURORA_TEXTS_BILLABLE_VERIZON
GROUP BY [CM Name], [Bill cycle date]
ORDER BY [CM Name]
