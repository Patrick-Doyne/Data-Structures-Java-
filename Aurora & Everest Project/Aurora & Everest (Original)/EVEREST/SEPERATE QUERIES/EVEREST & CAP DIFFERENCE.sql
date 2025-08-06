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
