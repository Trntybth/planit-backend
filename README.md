PlanIt - Event Management App (Android + Spring Boot)

This project is a full-stack event management application that allows members to sign up for events and organisations to view events they’ve created. The backend is built using Java Spring Boot and connects to a MongoDB database. The frontend is a native Android application built in Java.

The application supports two user types: "Member" and "Organisation". When a user logs in with Google Sign-In, the backend checks the email and redirects them to the appropriate home screen based on their type. Members can view a list of all events and add events to their "My Events" list. They can also remove events they've signed up for, and optionally add them to their Google Calendar. Organisations can view a list of events they’ve created.

The backend provides REST API endpoints for sign-up, un-sign-up, and fetching events. The Android app uses Retrofit to communicate with the backend and uses a simple session manager to keep track of the logged-in user's email.

This project meets the specification by:
- Supporting Google Sign-In for authentication
- Providing two distinct user roles (Member and Organisation)
- Enabling members to:
  - View all available events
  - Add events to their personal list
  - Remove themselves from events
  - Add events to Google Calendar
- Displaying organisation-created events in their dashboard
- Using MongoDB to store event and signup data
- Ensuring that only signed-up events are shown to each member
- Implementing full communication between frontend and backend using Retrofit and REST APIs
