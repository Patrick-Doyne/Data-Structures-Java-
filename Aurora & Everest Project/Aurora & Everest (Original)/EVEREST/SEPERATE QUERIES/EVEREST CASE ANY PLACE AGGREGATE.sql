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