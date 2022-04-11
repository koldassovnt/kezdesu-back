# KEZDECU-BACK APIs

## Every request starts with: http://{host}:8082


## 1 For Admin

##### For admin part working with JWT token is required. Except 1.1 Authorization part

### 1.1 Authorization:

#### 1.1.1 /admin/auth/login (POST)

- Request body fields: 
    - email (String) - Required, have to be email format
    - password (String) - Required

#### 1.1.2 /admin/auth/register (POST)

- Request body fields: 
    - email (String) - Required, have to be email format
    - password (String) - Required
    - phone (String)
    - displayName (String)
    
### 1.2 Client managing

#### 1.2.1 admin/action/block-client (POST)

- Request body fields:
    - id (String) - Required
    - blockReason (String)
    
#### 1.2.2 admin/action/list-client (GET)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - actual (Boolean)
   - blocked (Boolean)
   
### 1.3 Event managing

#### 1.3.1 admin/action/save-event (POST)

- Request body fields: 
    - label (String) - Required
    - description (String)
    - startedAt (Date) - Required
    - endedAt (Date) - Required
    - latitude (Double) - Required
    - longitude (Double) - Required
    - categoryId (String)
    - creatorId (String) - Required (Will be changed later)
    - images (List of MultipartFile)
    - videos (List of MultipartFile)

#### 1.3.2 admin/action/edit-event (POST)

- Request body fields: 
    - eventId (String) - Required
    - label (String)
    - description (String)
    - startedAt (Date)
    - endedAt (Date)
    - latitude (Double)
    - longitude (Double)
    - categoryId (String)
    - clientId (String) - (Will be changed later)
    - images (List of MultipartFile)
    - videos (List of MultipartFile)
  
#### 1.3.3 admin/action/block-event (POST)

- Request body fields: 
    - id (String) - Required
  
#### 1.3.4 admin/action/delete-event (POST)

- Request body fields: 
    - id (String) - Required
  
#### 1.3.5 admin/action/detail-event (GET)

- Request parameters: 
    - id (String) - Required
  
#### 1.3.6 admin/action/list-event (GET)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - categoryId (String)
   - labelSearch (String)
   - clientId (String)
   - actual (Boolean)
   - blocked (Boolean)
 

## 2 For Client

##### For client part working with JWT token is required. Except 2.1 Authorization part.

### 2.1 Authorization

#### 2.1.1 client/auth/post-phone-number (POST)

- Request body fields:
   - phoneNumber (String) - Required
   
#### 2.1.2 client/auth/post-sms-for-auth (POST)

- Request body fields:
   - phoneNumber (String) - Required
   - smsCode (String) - Required

#### 2.1.3 client/auth/refresh-token (POST)

- Request body fields:
   - refreshToken (String) - Required
   
### 2.2 Client managing


###### Apis will be updated as it will changed
