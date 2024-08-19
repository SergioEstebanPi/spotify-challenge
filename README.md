# Spring Boot Spotify Challenge

## Overview

This Spring Boot application is designed to handle track metadata and cover image retrieval for a Spotify challenge. It uses an H2 in-memory database for storage and includes OAuth2 authentication with Spotify. The application provides endpoints for retrieving track metadata, creating tracks, and fetching cover images.

## Features

- OAuth2 authentication with Spotify.
- H2 in-memory database for track metadata storage.
- Endpoints to manage track metadata and cover images.

## Technology Stack

- **Backend:** Spring Boot
- **Database:** H2 (in-memory)
- **OAuth2 Provider:** Spotify
- **Image Handling:** JPEG

## Prerequisites

Before running the application, ensure you have the following installed:

- JDK 11 or higher
- Maven

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/spotify-challenge.git
cd spotify-challenge
```
## Configure Spotify OAuth2

Create a file named application.properties in src/main/resources/ and add your Spotify credentials:

properties

spring.security.oauth2.client.registration.spotify.client-id=YOUR_SPOTIFY_CLIENT_ID
spring.security.oauth2.client.registration.spotify.client-secret=YOUR_SPOTIFY_CLIENT_SECRET
spring.security.oauth2.client.registration.spotify.redirect-uri=http://localhost:8080/oauth2/callback

Replace YOUR_SPOTIFY_CLIENT_ID and YOUR_SPOTIFY_CLIENT_SECRET with your actual Spotify API credentials.

## Run the Application

bash

```bash
./mvnw spring-boot:run
```

The backend application will be available at http://localhost:8080.
API Endpoints

The following endpoints are available:
## Retrieve Track Metadata

Endpoint: GET /codechallenge/getTrackMetadata
Description: Fetches metadata for a track based on its ISRC code.
Parameters:
    isrc (required): The ISRC code of the track.
Response:
    200 OK: Returns track metadata.
    404 Not Found: If the track is not found.

Example Request:

http
```bash
GET http://localhost:8080/codechallenge/getTrackMetadata?isrc=USUM72000812
```

## Create a Track

Endpoint: POST /codechallenge/createTrack
Description: Creates a new track entry based on its ISRC code.
Parameters:
    isrc (required): The ISRC code of the track.
Response:
    201 Created: Successfully created the track.
    404 Not Found: If the track cannot be found.

Example Request:

http
```bash
POST http://localhost:8080/codechallenge/createTrack?isrc=USUM72000812
```

## Get Cover Image

    Endpoint: GET /codechallenge/getCover
    Description: Retrieves the cover image of a track based on its ISRC code.
    Parameters:
        isrc (required): The ISRC code of the track.
    Response:
        200 OK: Returns the cover image in JPEG format.
        500 Internal Server Error: If there is an error retrieving the image.

Example Request:

http
```bash
GET http://localhost:8080/codechallenge/getCover?isrc=USUM72000812
```

## Authentication

    Login with Spotify:
        The application uses OAuth2 for authentication with Spotify.
        After successful authentication, you will receive an access token which can be used for subsequent API requests.

    Configure OAuth2 Callback:
        Ensure that your Spotify application has the correct redirect URI configured to handle OAuth2 callback.

## Database

    The application uses an H2 in-memory database, which is suitable for development and testing purposes.
    Data stored in the H2 database is ephemeral and will be lost when the application restarts.

## Contributing

Feel free to submit issues or pull requests. Contributions are welcome to improve functionality or fix bugs.