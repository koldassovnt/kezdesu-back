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

#### 1.1.3 /admin/action/reset-password (POST) (NEED token)

- Request body fields: 
    - oldPassword (String) - Required
    - newPassword (String) - Required
    
### 1.2 Client managing

#### 1.2.1 admin/action/block-client (POST) (NEED token)

- Request body fields:
    - id (String) - Required
    - blockReason (String)
    
#### 1.2.2 admin/action/list-client (GET) (NEED token)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - actual (Boolean)
   - blocked (Boolean)

#### 1.2.3 admin/action/unblock-client (POST) (NEED token)

- Request body fields:
    - id (String) - Required
   
### 1.3 Event managing

#### 1.3.1 admin/action/save-event (POST) (NEED token)

- Request body fields: 
    - label (String) - Required
    - description (String)
    - startedAt (Date) - Required
    - endedAt (Date) - Required
    - latitude (Double) - Required
    - longitude (Double) - Required
    - categoryId (String)
    - address (String)

#### 1.3.2 admin/action/edit-event (POST) (NEED token)

- Request body fields: 
    - eventId (String) - Required
    - label (String)
    - description (String)
    - startedAt (Date)
    - endedAt (Date)
    - latitude (Double)
    - longitude (Double)
    - categoryId (String)
    - address (String)
  
#### 1.3.3 admin/action/block-event (POST) (NEED token)

- Request body fields: 
    - id (String) - Required
  
#### 1.3.4 admin/action/delete-event (POST) (NEED token)

- Request body fields: 
    - id (String) - Required
  
#### 1.3.5 admin/action/detail-event (GET) (NEED token)

- Request parameters: 
    - id (String) - Required
  
#### 1.3.6 admin/action/list-event (GET) (NEED token)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - categoryId (String)
   - labelSearch (String)
   - clientId (String)
   - actual (Boolean)
   - blocked (Boolean)
 
### 1.4 City managing

#### 1.4.1 /admin/action/save-city (POST) (NEED token)

- Request body fields: 
    - cityLabel (String) - Required
    - latitude (Double) - Required
    - longitude (Double) - Required

#### 1.4.2 /admin/action/edit-city (POST) (NEED token)

- Request body fields: 
    - cityId (String) - Required
    - cityLabel (String)
    - latitude (Double)
    - longitude (Double)

#### 1.4.3 /admin/action/delete-city (POST) (NEED token)

- Request body fields: 
    - id (String) - Required

#### 1.4.4 admin/action/list-city (GET) (NEED token)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - actual (Boolean)

#### 1.4.5 admin/action/detail-city (GET) (NEED token)

- Request parameters: 
    - id (String) - Required

### 1.5 Category managing

#### 1.5.1 /admin/action/save-category (POST) (NEED token)

- Request body fields: 
    - categoryLabel (String) - Required
    - img (String)
    - color (String)

#### 1.5.2 /admin/action/edit-category (POST) (NEED token)

- Request body fields: 
    - categoryId (String) - Required
    - categoryLabel (String)
    - img (String)
    - color (String)

#### 1.5.3 /admin/action/delete-category (POST) (NEED token)

- Request body fields: 
    - id (String) - Required

#### 1.5.4 admin/action/list-category (GET) (NEED token)

- Request parameters:
   - limit (Integer)
   - offset (Integer)
   - actual (Boolean)

#### 1.5.5 admin/action/detail-category (GET) (NEED token)

- Request parameters: 
    - id (String) - Required

### 1.6 Account managing

#### 1.6.1 admin/action/get-account (GET) (NEED token)

- No request param

#### 1.6.2 admin/action/edit-account (POST) (NEED token)

- Request body fields:
    - displayName (String)
    - phone (String)

### 1.7 Complain managing (жалобы)

#### 1.7.1 admin/action/list-complain (GET) (NEED token)

- No request param

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
   
### 2.2 Event managing

#### 2.2.1 client/action/save-event (POST) (NEED token)

- Request body fields: 
    - label (String) - Required
    - description (String)
    - startedAt (Date) - Required
    - endedAt (Date) - Required
    - latitude (Double) - Required
    - longitude (Double) - Required
    - categoryId (String)
    - address (String)
   
#### 2.2.2 client/action/edit-event (POST) (NEED token)
   
- Request body fields: 
    - eventId (String) - Required
    - label (String)
    - description (String)
    - startedAt (Date)
    - endedAt (Date)
    - latitude (Double)
    - longitude (Double)
    - categoryId (String)
    - address (String)

#### 2.2.3 client/action/list-event (GET) (NEED token)

- Request parameters:
   - categoryId (String)
   - labelSearch (String)
   - clientId (String)
   - actual (Boolean)
   - blocked (Boolean)

#### 2.2.4 client/action/detail-event (GET) (NEED token)

- Request parameters: 
    - id (String) - Required
   
#### 2.2.5 client/action/join-event (POST) (NEED token)

- Request parameters: 
    - id (String) - Required

#### 2.2.6 client/action/qr-event (POST) (NEED token)

- Request parameters: 
    - id (String) - Required

#### 2.2.7 client/action/client-events (GET) (NEED token)

- No request param

#### 2.2.8 client/action/client-participated-events (GET) (NEED token)

- No request param

#### 2.2.9 client/action/event/{id}/save-content (POST) (NEED token)

- Request parameters: 
    - file (MultipartFile)

- PathVariable: 
    - id (String)
   
### 2.3 Client managing

#### 2.3.1 client/action/client-detail (GET) (NEED token)

- No request param

#### 2.3.2 client/action/edit-client (POST) (NEED token)

- Request body:
    - displayName (String)
    - email (String)
    - name (String)
    - surname (String)
    - birthDate (Date)

#### 2.3.4 client/action/save-image (POST) (NEED token)

- Request parameters: 
    - file (MultipartFile)

### 2.4 Category managing

#### 2.4.1 client/action/category-list (GET) (NEED token)

- No request param

### 2.5 File (Content) managing

#### 2.5.1 client/action/get-content/{id} (GET) (NEED token)

- PathVariable:
    - id (String)

### 2.6 Complain managing (жалобы)

#### 2.6.1 client/action/complain-event (POST) (NEED token)

- Request body:
    - eventId (String) - Required
    - complainText (String) - Required

###### Apis will be updated as it will changed
