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

 

