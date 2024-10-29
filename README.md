# Traveler’s Guide and Assistant

Traveler’s guide and assistant by the Name indicated it is a cloud based android mobile application which is help travelers all over the world with much valuable functionalities which will help their journey as a guider and as a assistant. So it will help to improve tourism all over the world. 

This application will help traveller to overcome different problems they face while they are travelling. It offers map service, Weather service with forecasting weather details, travel log system, scheduler system, News corner and social media platform for travelers called TravelMate as main functionalities. As extra functionalities it includes language translator, language identifier and Photo to text Converter (OCR). 

Through this mobile application travelers can continue their own account with authentication system and they can also transfer their account to another person.

<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/91b86955-6015-4927-8227-2658a9c3cbf5" width="600">
</div>

## System Architecture

<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/1a308235-c442-4ae7-98f2-020791e9b831" width="600">
</div>

## Used Technologies
- Languages
<ul>
  <li>JAVA - For android mobile app development</li>
  <li>XML - For Mobile app interface designs</li>
</ul>
- Tools
<ul>
<li>Android Studio Bumblebee — 2021.1.1 Patch 2 used for android app development</li>
<li>GitHub - version control System</li>
<li>Google Play Services</li>
</ul>
- Backend
<ul>
  <li>Authentication - Firebase Authentication</li>
  <li>Database - Firestore Database</li>
  <li>Storage - Firebase Storage</li>
  <li>Realtime Database - Firebase Realtime Database</li>
</ul>
- APIs and Packages
<ul>
  <li>Google Developers</li>
  <ul>
    <li>ML Kit Package</li>
    <ul>
      <li>Translate Library - For language translator </li>
      <li>Language ID Library - For language identifier </li>
    </ul>
    <li>Mobile Vision API - For OCR feature </li>
  </ul>
  <li>Google Cloud Platform</li>
  <ul>
      <li>Google Maps API - For map feature</li>
  </ul>
  <li>OpenWeather API -For weather feature</li>
  <li>News API - For news feature</li>
</ul>
- Libraries
<ul>
<li>AndroidX – Jetpack Libraries </li>
  <ul>
  <li>appcompact – Allows access to new APIs on older API versions of the platform</li>
  <li>constraintlayout – Position and size widgets in a flexible way with relative positioning</li>
  <li>recyclerview – Display large sets of data in your UI while minimizing memorynusage</li>
  <li>swiperefreshlayout – Implement the swipe-to-refresh UI pattern</li>
  </ul>
<li>Google Material Design Library</li>
<li>Other External Libraries Used</li>
  <ul>
  <li>Android Image Cropper – crop inserted images</li>
  <li>CircleImageView – Add Circular image views</li>
  <li>Sweet Alert Dialog – show alert dialog</li>
  <li>Dexter – get run time permission</li>
  <li>ssp / sdp - a scalable size unit</li>
  <li>Retrofit - A type-safe HTTP client for Android and Java</li>
  <li>Picasso - A powerful image downloading and caching library</li>
  <li>Prettytime - Social Style Date and Time Formatting for Java</li>
  </ul>
</ul>

## Objectives

1. To provide a real-time map for the users which can get users current location and search locations with 3 different map views.
2. To provide weather system, where user can get current location weather and other places weather by searching.
3. To provide weather forecasting system.
4. To provide a travel scheduler, where traveler can plan their day with time.
5. To provide news system which is updated with latest news and user and search news.
6. To provide a travel log system with storage where users can store photos and text notes with log name and date.
7. Search old Travel logs Using log number, log name and date. After selecting a log user can see that log.
8. To provide a social media platform for travelers where they can continue their own profile and post new photos.
9. To provide search facility for travel social media to filter post according to user preference.
10. To provide language translation facility.
11. To provide photo text translator with text recognition capability.
12. To provide Language identifier facility.
13. To provide authentication method with security features.
14. To provide user profile which they can edit, change password and transfer it to another person.
15. To develop the proposed system into a marketable product in the tourism sector.
    
## Features

### 1. Authentication System and Settings
User Authentication includes user registration and user login features. Users can register for an account by themselves. Settings feature includes various account settings.

#### Main Menu Interfaces
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/fdf8edf6-bcad-4421-893e-235b3b8698e8" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/9acc1b57-8623-4fc8-a075-7c002f753930" width="150">
</div>

#### User Login Interfaces
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/c1fd6026-d57d-4b45-92f9-039bedf52fca" width="100">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/579c6a4f-9721-4255-b33c-827a77bfefeb" width="100">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/32dff59f-c0ee-4811-b7f1-5ee1a3ab0f0d" width="100">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/3d5dd437-a463-4d3f-b583-39c010e1027d" width="100">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/ca7e8a77-f87f-421f-ae9b-6f3544b79f7b" width="100">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/95428ab2-a8c2-4fbe-ae4c-1119a3304e81" width="100">
 </div>

#### Settings Interfaces
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/38b7fe90-87bf-43e0-a3e0-5cf6b217ccd5" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/00a0b8da-7b61-429a-a27f-6dca074faaf5" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/5de9d640-c351-4675-85ee-1662f9dd2902" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/517d7a56-74b6-438c-9839-b96091b1df72" width="150">
</div>

### 2. Map Service
This feature allows users to view three map views with the current location and search location feature.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/a56f2800-38eb-4c2c-be94-6164f93d3772" width="150">
</div>

### 3. Scheduler Service
Users can plan their day using this feature.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/02e6586a-5677-48fa-8549-8dbc60f36340" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/67ddcca5-32c9-4f43-a50e-8a560fe64705" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/45e110a5-4895-4f7e-9e76-110a8b3ddac1" width="150">
</div>

### 4. News Service
Users can get the latest news from different news services.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/fadf0856-f122-4cda-8b39-3b47d30e9c50" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/f023f800-0e41-42b3-8a20-4a68022faf02" width="150">
</div>

### 5. Weather Service
Users can get details about the current location weather and forecasting weather details for the next 5 days with a 3-hour time step. They can also search for weather details for a specific location.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/3e5f08c4-bf04-4528-a81c-396e51f31b84" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/f7689f16-57e8-45ac-a22e-3fd26764d0b6" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/434ed56b-c8c3-4718-8073-b7a357d5097c" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/03f8c46c-0260-474b-8d38-e67bea5e70be" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/e921acdb-56fc-4294-a058-e5d6506b09d8" width="150">
</div>

### 6. Travel Log Service
Users can insert travel logs with details such as log name, number, date, description, and three photos. They can also filter and view the added logs later.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/9399ffb9-6dc6-4d4f-bd37-f53315513667" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/c58ecdbb-e5a3-465d-891e-deecea4cf6a5" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/ea38e0a1-35e5-4d81-ac6e-6ef3be3a64e3" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/9d45addb-e83e-492d-9888-94fc1850a5a0" width="150">
</div>

### 7. TravelMate
This is a social media platform for travelers.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/a9d66e0e-6714-46e0-8dc6-c46e0b954ad1" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/18df7be3-90a1-4ccf-9556-65d82937511f" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/af8d4215-6655-4208-a323-f880f89559ef" width="150">
</div>

### 8. Extra Features
Users can use these three extra features during their journeys.
<div align='center'>
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/94913818-ea49-45a8-b1c9-df47ab3af3ed" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/dbe5f609-28a7-41d6-b56a-727f360e290e" width="150">
  <img src="https://github.com/ManujaDewmina/Travellers-Guide-and-Assistant/assets/92631934/09da1421-eacf-4314-9f37-3e54f0d98d7d" width="150">
</div>
