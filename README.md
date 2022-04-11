# KEZDECU-BACK APIs

## Every request starts with: http://{host}:8082


## 1 For Admin

### 1.1 Authorization:

- /admin/auth/login (POST)
Request body fields: 
    email (String) - Required, have to be email format
    password (String) - Required

- /admin/auth/register (POST)
Request body fields: 
    email (String) - Required, have to be email format
    password (String) - Required
    phone (String)
    displayName (String)
    
### 1.2 Client managing

- admin/action/block-client (POST)
Request body fields:
    id (String) - Required
    blockReason (String)
    
- admin/action/list-client (GET)
Request parameters:
   limit (Integer)
   offset (Integer)
   actual (Boolean)
   blocked (Boolean)
   
### 1.3 Event managing

- admin/action/save-event (POST)
Request body fields: 
    label (String) - Required
    description (String)
    startedAt (Date) - Required
    endedAt (Date) - Required
    latitude (Double) - Required
    longitude (Double) - Required
    categoryId (String)
    creatorId (String) - Required (Will be changed later)
    images (List<MultipartFile>)
    videos (List<MultipartFile>)
  
- admin/action/edit-event (POST)
Request body fields: 
    eventId (String) - Required
    label (String)
    description (String)
    startedAt (Date)
    endedAt (Date)
    latitude (Double)
    longitude (Double)
    categoryId (String)
    clientId (String) - (Will be changed later)
    images (List<MultipartFile>)
    videos (List<MultipartFile>)
  
- admin/action/block-event (POST)
Request body fields: 
    id (String) - Required
  
- admin/action/delete-event (POST)
Request body fields: 
    id (String) - Required
  
- admin/action/detail-event (GET)
Request parameters: 
    id (String) - Required
  
- admin/action/list-event (GET)
Request parameters:
   limit (Integer)
   offset (Integer)
   categoryId (String)
   labelSearch (String)
   clientId (String)
   actual (Boolean)
   blocked (Boolean)
 

## 2 For Client


###### Apis will be updated as it will changed
