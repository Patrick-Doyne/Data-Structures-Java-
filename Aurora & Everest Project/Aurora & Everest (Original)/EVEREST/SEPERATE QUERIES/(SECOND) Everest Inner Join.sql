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

