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
