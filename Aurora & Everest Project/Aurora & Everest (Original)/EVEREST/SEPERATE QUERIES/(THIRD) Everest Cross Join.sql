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

